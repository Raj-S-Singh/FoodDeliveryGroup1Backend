package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.repository.RestaurantRepository;
import com.pomato.mainPackage.repository.UserRepository;
import com.pomato.mainPackage.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    @PostMapping(value = "/login",consumes = "application/json",produces = "application/json")
    public ResponseEntity<AdminLoginResponse> adminLogin(@RequestBody LoginRequest loginRequest){
        AdminLoginResponse adminLoginResponse=adminService.loginAuth(loginRequest);
        if(adminLoginResponse.isStatus()){
            return new ResponseEntity<>(adminLoginResponse, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(adminLoginResponse,HttpStatus.BAD_REQUEST);
    }
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    UserRepository userRepository;
    @DeleteMapping(value = "/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantDeleteResponse> deleteRestaurant(@PathVariable int id, @RequestHeader(name = "jwtToken") String jwtToken))
    {
        RestaurantDeleteResponse restaurantDeleteResponse = deleteRestaurant( int id, String jwtToken);
        if(restaurantDeleteResponse.isStatus()){
            return new ResponseEntity<>(restaurantDeleteResponse, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(restaurantDeleteResponse,HttpStatus.BAD_REQUEST);
    }
    }


}
