package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @PostMapping(value = "/managersignup", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<ManagerSignupResponse> managerSignUp(@RequestBody ManagerSignupRequest managerSignupRequest){

        ManagerSignupResponse managerSignupResponse = managerService.registerManager(managerSignupRequest);

        if(managerSignupResponse.isStatus()){
            return new ResponseEntity<>(managerSignupResponse, HttpStatus.OK );
        }
        else{
            return new ResponseEntity<>(managerSignupResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/addItem/{restaurantId}",consumes = "application/json",produces="application/json")
    public ResponseEntity<AddItemResponse> addItemToRestaurant(@PathVariable int restaurantId, @RequestHeader(name="jwtToken") String jwtToken,@RequestBody AddItemRequest addItemRequest){
        AddItemResponse addItemResponse = managerService.addItemToRestaurant(addItemRequest,restaurantId,jwtToken);

        if(addItemResponse.getStatus()){
            return new ResponseEntity<AddItemResponse>(addItemResponse,HttpStatus.OK);
        }
        return new ResponseEntity<AddItemResponse>(addItemResponse,HttpStatus.BAD_REQUEST);
    }
}
