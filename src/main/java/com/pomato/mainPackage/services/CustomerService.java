package com.pomato.mainPackage.services;

import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.repository.FoodOrderRepository;
import com.pomato.mainPackage.repository.PaymentRepository;
import com.pomato.mainPackage.model.AddItemResponse;
import com.pomato.mainPackage.model.CustomerSignupResponse;
import com.pomato.mainPackage.model.Restaurant;
import com.pomato.mainPackage.model.User;
import com.pomato.mainPackage.repository.RestaurantRepository;
import com.pomato.mainPackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

@Service
public class CustomerService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodOrderRepository foodOrderRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Value("${pepper}")
    String pepper;

    public CustomerSignupResponse register(User user){

        User newUser = new User();
        User currentUser = userRepository.findByEmail(user.getEmail());
        CustomerSignupResponse customerSignupResponse = new CustomerSignupResponse();

        if(currentUser != null){
            customerSignupResponse.setMessage("Customer already exists.");
            customerSignupResponse.setStatus(false);
        }
        else{
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(user.getPassword() + pepper, salt );
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            user.setJwtToken(BCrypt.gensalt());
            //user = userRepository.save(user);
            newUser = userRepository.save(user);

            customerSignupResponse.setUserId(newUser.getUserId());
            customerSignupResponse.setName(newUser.getName());
            customerSignupResponse.setEmail(newUser.getEmail());
            customerSignupResponse.setRole(newUser.getRole());
            customerSignupResponse.setContactNumber(newUser.getContactNumber());
            customerSignupResponse.setJwtToken(newUser.getJwtToken());

            customerSignupResponse.setMessage("Signup successful");
            customerSignupResponse.setStatus(true);
        }
        return customerSignupResponse;
    }

    public PlaceOrderResponse placeOrder(String jwtToken, PlaceOrder placeOrder) {
        FoodOrders order=new FoodOrders();
        Payment payment=new Payment();
        User user=userRepository.findByUserId(placeOrder.getUserId());
        PlaceOrderResponse placeOrderResponse = new PlaceOrderResponse();
        if (user.getJwtToken().equals(jwtToken)==false){
            placeOrderResponse.setMessage("jwtToken invalid");
            placeOrderResponse.setStatus(false);
            return placeOrderResponse;
        }
        else {
            order.setUserId(placeOrder.getUserId());
            order.setAddress(placeOrder.getAddress());
            order.setRestaurantId(placeOrder.getRestaurantId());
            order.setListOfItems(placeOrder.getListOfItems());
            order.setOrderStatus("Order Placed");
            String st = String.valueOf(Timestamp.from(Instant.now()));
            order.setTimeStamp(st);
            FoodOrders temp = foodOrderRepository.save(order);
            if (temp == null) {
                placeOrderResponse.setMessage("Order saving failed");
                placeOrderResponse.setStatus(false);
                return placeOrderResponse;
            }
            payment.setOrderId(order.getOrderId());
            payment.setTimeStamp(st);
            payment.setRestaurantId(placeOrder.getRestaurantId());
            payment.setPaymentMethod(placeOrder.getPaymentMethod());
            payment.setAmount(placeOrder.getAmount());
            payment.setPaymentStatus("Success");
            Payment tempp = paymentRepository.save(payment);
            if (tempp == null) {
                placeOrderResponse.setMessage("Order payment saving failed");
                placeOrderResponse.setStatus(false);
                return placeOrderResponse;
            }
            placeOrderResponse.setMessage("Order Placed and Payment Successful");
            placeOrderResponse.setStatus(true);
            return placeOrderResponse;
        }
    }
    
    public ResponseEntity<GetRestaurantResponse> getAllRestaurant(String token){
        GetRestaurantResponse getRestaurantResponse=new GetRestaurantResponse();
        User user=userRepository.findByJwtToken(token);
        if(user!=null && user.getRole().equalsIgnoreCase("Customer")){
            getRestaurantResponse.setAllRestaurant(restaurantRepository.getAllRestaurants());
            getRestaurantResponse.setMessage("Successfully executed");
            return new ResponseEntity<GetRestaurantResponse>(getRestaurantResponse, HttpStatus.OK);
        }
        else{
            getRestaurantResponse.setAllRestaurant(Collections.emptyList());
            getRestaurantResponse.setMessage("JWT Token Invalid");
            return new ResponseEntity<GetRestaurantResponse>(getRestaurantResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
