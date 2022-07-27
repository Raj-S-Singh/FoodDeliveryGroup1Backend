package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.model.ManagerSignupRequest;
import com.pomato.mainPackage.model.ManagerSignupResponse;
import com.pomato.mainPackage.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
