package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    @PostMapping(value = "/login",consumes = "application/json",produces = "application/json")
    public void adminLogin(LoginRequest loginRequest){
        adminService.loginAuth(loginRequest);
    }
}
