package com.pomato.mainPackage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/customersignup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerSignupResponse> customerSignup(@RequestBody User user){

        CustomerSignupResponse customerSignupResponse = customerService.register(user);

        if(customerSignupResponse.isStatus()){
            return new ResponseEntity<>(customerSignupResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(customerSignupResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/placeOrder", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlaceOrderResponse> placeOrderCustomer(@RequestHeader(name = "jwtToken")String jwtToken
            , @RequestBody PlaceOrder placeOrder) throws JsonProcessingException {
        PlaceOrderResponse placeOrderResponse=customerService.placeOrder(jwtToken,placeOrder);
        if (placeOrderResponse.isStatus()){
            return new ResponseEntity<>(placeOrderResponse,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(placeOrderResponse,HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping(value="/restaurants",produces = "application/json")
    public ResponseEntity<GetRestaurantResponse> getRestaurants(@RequestHeader(name = "jwtToken") String jwtToken){
        GetRestaurantResponse getRestaurantResponse=customerService.getAllRestaurant(jwtToken);
        if (getRestaurantResponse.isStatus())
            return new ResponseEntity<>(getRestaurantResponse,HttpStatus.OK);
        else
            return new ResponseEntity<>(getRestaurantResponse,HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/restaurants/getitems/{restaurantId}",produces="application/json")
    public ResponseEntity<ViewMenuResponse> viewMenu(@RequestHeader(name="jwtToken") String jwtToken,
                                                     @PathVariable("restaurantId") int restaurantId){
        ViewMenuResponse viewMenuResponse=customerService.viewRestaurantMenu(jwtToken,restaurantId);
        if (viewMenuResponse.isStatus()){
            return new ResponseEntity<ViewMenuResponse>(viewMenuResponse,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<ViewMenuResponse>(viewMenuResponse,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/vieworderscustomer/{userId}",produces = "application/json")
    public ResponseEntity<ViewOrderCustomerResponse> getOrders(@RequestHeader(name = "jwtToken") String jwtToken,
                                                               @PathVariable int userId){
        ViewOrderCustomerResponse viewOrderCustomerResponse=customerService.viewOrders(jwtToken,userId);
        if (viewOrderCustomerResponse.isStatus()){
            return new ResponseEntity<>(viewOrderCustomerResponse,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(viewOrderCustomerResponse,HttpStatus.BAD_REQUEST);
    }
    @PostMapping(value = "/checkout/{userId}")
    public boolean checkoutFunction(@RequestHeader(name="jwtToken") String jwtToken,@PathVariable int userId){
        return customerService.checkout(jwtToken,userId);
    }
}
