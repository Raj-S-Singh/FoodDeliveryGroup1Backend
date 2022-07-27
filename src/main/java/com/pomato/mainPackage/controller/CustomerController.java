package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.model.CustomerSignupResponse;
import com.pomato.mainPackage.model.Restaurant;
import com.pomato.mainPackage.model.User;
import com.pomato.mainPackage.repository.RestaurantRepository;
import com.pomato.mainPackage.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    RestaurantRepository restaurantRepository;

    @PostMapping(value = "/customersignup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerSignupResponse> customerSignup(@RequestBody User user){

        CustomerSignupResponse customerSignupResponse = customerService.register(user);

        if(customerSignupResponse.isStatus()){
            return new ResponseEntity<CustomerSignupResponse>(customerSignupResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<CustomerSignupResponse>(customerSignupResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping (value="/restaurants",produces = "application/json")
    public ResponseEntity<Collection<Restaurant>> getRestaurants(){
        return new ResponseEntity<Collection<Restaurant>>(restaurantRepository.getAllRestaurants(),HttpStatus.OK);
    }
}
