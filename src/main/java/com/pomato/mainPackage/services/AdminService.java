package com.pomato.mainPackage.services;

import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.repository.FoodOrderRepository;
import com.pomato.mainPackage.repository.PaymentRepository;
import com.pomato.mainPackage.repository.RestaurantRepository;
import com.pomato.mainPackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class AdminService {
    @Value("${pepper}")
    String pepper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    FoodOrderRepository foodOrderRepository;
    @Autowired
    PaymentRepository paymentRepository;
    public AdminLoginResponse loginAuth(LoginRequest loginRequest) {
        User user=userRepository.findByEmail(loginRequest.getEmail());
        AdminLoginResponse adminLoginResponse=new AdminLoginResponse();
        if(user==null){
            adminLoginResponse.setMessage("Login failed, no user exists");
            adminLoginResponse.setStatus(false);
        }
        else if(loginRequest.getEmail().equals(user.getEmail()) &&
        user.getPassword().equals(BCrypt.hashpw(loginRequest.getPassword()+pepper,user.getSalt()))
        ){
            adminLoginResponse.setMessage("Login successful");
            adminLoginResponse.setStatus(true);
            adminLoginResponse.setUserId(user.getUserId());
            adminLoginResponse.setEmail(user.getEmail());
            adminLoginResponse.setRole(user.getRole());
            String jwtTokenGen= UUID.randomUUID().toString();
            user.setJwtToken(jwtTokenGen);
            userRepository.save(user);
            adminLoginResponse.setJwtToken(user.getJwtToken());
            adminLoginResponse.setName(user.getName());
        }
        else {
            adminLoginResponse.setMessage("Login failed, wrong password");
            adminLoginResponse.setStatus(false);
        }
        return adminLoginResponse;
    }

    public LogoutResponse logoutAuth(int userId, String jwtToken){

        LogoutResponse logoutResponse = new LogoutResponse();
        if (jwtToken.equals((userRepository.findByUserId(userId)).getJwtToken()) == false) {
            logoutResponse.setStatus(false);
            logoutResponse.setMessage("jwtToken invalid");

            return logoutResponse;
        }
        else{
            User user = userRepository.findByUserId(userId);
            user.setJwtToken(null);
            userRepository.save(user);

            logoutResponse.setStatus(true);
            logoutResponse.setMessage("Logged out successfully");

            return logoutResponse;
        }


        }
    public RestaurantDeleteResponse deleteRestaurant(int restaurantId, String jwtToken){
        RestaurantDeleteResponse restaurantDeleteResponse=new RestaurantDeleteResponse();
        User user= userRepository.findByJwtToken(jwtToken);
        if(user==null){
            restaurantDeleteResponse.setMessage("No such user currently logged in");
            restaurantDeleteResponse.setStatus(false);
        }
        else if(user.getRole().equalsIgnoreCase("manager") || user.getRole().equalsIgnoreCase("admin")){
            Restaurant restaurant = restaurantRepository.findByRestaurantId(restaurantId);
            if (user.getRole().equalsIgnoreCase("admin") || (user.getRole().equalsIgnoreCase("manager") && restaurant.getUserId() == user.getUserId())) {
                userRepository.deleteById(restaurant.getUserId());
                restaurantRepository.deleteById(restaurantId);
                restaurantDeleteResponse.setMessage("Successfully deleted");
                restaurantDeleteResponse.setStatus(true);
            }else{
                restaurantDeleteResponse.setMessage("You are not allowed to delete restaurant");
                restaurantDeleteResponse.setStatus(false);
            }
        }
        return restaurantDeleteResponse;
    }
    public ViewAllOrdersAdminResponse getAllOrder(String jwtToken){

        ViewAllOrdersAdminResponse viewAllOrdersAdminResponse = new ViewAllOrdersAdminResponse();
        if(!jwtToken.equals(userRepository.findByUserId(1).getJwtToken())){
            viewAllOrdersAdminResponse.setStatus(false);
            viewAllOrdersAdminResponse.setMessage("jwtToken invalid");

        }
        else{
            viewAllOrdersAdminResponse.setStatus(true);
            viewAllOrdersAdminResponse.setMessage("Orders fetched");
            viewAllOrdersAdminResponse.setList(foodOrderRepository.findAll());
        }
        return viewAllOrdersAdminResponse;
    }
    public PaymentAllResponse getAllPayments(String jwtToken) {
        PaymentAllResponse paymentAllResponse=new PaymentAllResponse();
        User user=userRepository.findByUserId(1);
        if (!user.getJwtToken().equals(jwtToken)) {
            paymentAllResponse.setMessage("jwtToken invalid");
            paymentAllResponse.setStatus(false);
            return paymentAllResponse;
        }
        List<Payment> paymentList=paymentRepository.findAll();
        paymentAllResponse.setMessage("Payments Fetched");
        paymentAllResponse.setStatus(true);
        paymentAllResponse.setPayments(paymentList);
        return paymentAllResponse;
    }
}
