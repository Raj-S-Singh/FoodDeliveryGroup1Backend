package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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

    @PutMapping(value = "/logout/{userId}", produces = "application/json")
    public ResponseEntity<LogoutResponse> logoutUser(@PathVariable int userId,
                                                     @RequestHeader(name="jwtToken") String jwtToken){

        LogoutResponse logoutResponse = adminService.logoutAuth(userId, jwtToken);
        if(logoutResponse.isStatus()){
            return new ResponseEntity<>(logoutResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(logoutResponse, HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping(value = "restaurant/delete/{restaurantId}",produces = "application/json")
    public ResponseEntity<RestaurantDeleteResponse> deleteRestaurant(@RequestHeader(name = "jwtToken") String jwtToken,@PathVariable("restaurantId") int restaurantId){
        RestaurantDeleteResponse restaurantDeleteResponse=new RestaurantDeleteResponse();
        restaurantDeleteResponse=adminService.deleteRestaurant(restaurantId, jwtToken);
        if (restaurantDeleteResponse.isStatus()){
            return new ResponseEntity<>(restaurantDeleteResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(restaurantDeleteResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/viewallorders", produces = "application/json")
    public ResponseEntity<ViewAllOrdersAdminResponse> viewAllOrders(@RequestHeader(name = "jwtToken")
                                                                        String jwtToken){

        ViewAllOrdersAdminResponse viewAllOrdersAdminResponse = adminService.getAllOrder(jwtToken);
        if(viewAllOrdersAdminResponse.isStatus()){
            return new ResponseEntity<>(viewAllOrdersAdminResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(viewAllOrdersAdminResponse, HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping(value = "/viewAllPayments",produces = "application/json")
    public ResponseEntity<PaymentAllResponse> paymentAll(@RequestHeader(name = "jwtToken") String jwtToken){
        PaymentAllResponse paymentAllResponse=adminService.getAllPayments(jwtToken);
        if (paymentAllResponse.isStatus())
            return new ResponseEntity<>(paymentAllResponse,HttpStatus.OK);
        else
            return new ResponseEntity<>(paymentAllResponse,HttpStatus.BAD_REQUEST);
    }
}
