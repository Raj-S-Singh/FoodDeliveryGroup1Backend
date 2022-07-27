package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.model.AdminLoginResponse;
import com.pomato.mainPackage.model.LoginRequest;
import com.pomato.mainPackage.model.Restaurant;
import com.pomato.mainPackage.model.RestaurantDeleteResponse;
import com.pomato.mainPackage.repository.RestaurantRepository;
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

    @DeleteMapping(value = "/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantDeleteResponse> deleteRestaurant(@PathVariable int id){
        RestaurantDeleteResponse restaurantDeleteResponse = new RestaurantDeleteResponse();
        Restaurant restaurant = restaurantRepository.findByRestaurantId(id);
        if( restaurant == null){
            restaurantDeleteResponse.setMessage("Restaurant not found");
            restaurantDeleteResponse.setStatus(false);
            return new ResponseEntity<RestaurantDeleteResponse>(restaurantDeleteResponse,HttpStatus.BAD_REQUEST);
        }
        restaurantRepository.deleteById(id);
        restaurantDeleteResponse.setStatus(true);
        restaurantDeleteResponse.setMessage("Successfully deleted");
        return new ResponseEntity<RestaurantDeleteResponse>(restaurantDeleteResponse,HttpStatus.OK);
    }
}
