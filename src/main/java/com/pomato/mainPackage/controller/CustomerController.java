package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.model.CustomerSignupResponse;
import com.pomato.mainPackage.model.Restaurant;
import com.pomato.mainPackage.model.User;
import com.pomato.mainPackage.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/customersignup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerSignupResponse> customerSignup(@RequestBody User user){

        CustomerSignupResponse customerSignupResponse = customerService.register(user);

        if(customerSignupResponse.isStatus()){
            return new ResponseEntity<CustomerSignupResponse>(customerSignupResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<CustomerSignupResponse>(customerSignupResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value="/restaurants",produces = "application/json")
    public ResponseEntity<Collection<Restaurant>> getRestaurants(@RequestHeader(name = "jwtToken") String jwtToken){
        Collection<Restaurant> allRestaurant=customerService.getAllRestaurant(jwtToken);
        if(allRestaurant==null){
            return new ResponseEntity<Collection<Restaurant>>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<Collection<Restaurant>>(allRestaurant,HttpStatus.OK);
        }
    }
}
