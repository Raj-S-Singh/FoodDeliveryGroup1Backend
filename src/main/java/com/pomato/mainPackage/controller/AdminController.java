package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.model.AdminLoginResponse;
import com.pomato.mainPackage.model.LoginRequest;
import com.pomato.mainPackage.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
