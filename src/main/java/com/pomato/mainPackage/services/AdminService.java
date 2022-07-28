package com.pomato.mainPackage.services;

import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.repository.RestaurantRepository;
import com.pomato.mainPackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class AdminService {
    @Value("${pepper}")
    String pepper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    public AdminLoginResponse loginAuth(LoginRequest loginRequest) {
        User user=userRepository.findByEmail(loginRequest.getEmail());
        AdminLoginResponse adminLoginResponse=new AdminLoginResponse();
        if(user==null){
            adminLoginResponse.setMessage("Login failed, no user exists");
            adminLoginResponse.setStatus(false);
        }
        else if(loginRequest.getEmail().equals(user.getEmail()) &&
        user.getPassword().equals(BCrypt.hashpw(loginRequest.getPassword()+pepper,user.getSalt()))
        ){
            adminLoginResponse.setMessage("Login successful");
            adminLoginResponse.setStatus(true);
            adminLoginResponse.setUserId(user.getUserId());
            adminLoginResponse.setEmail(user.getEmail());
            adminLoginResponse.setRole(user.getRole());
            String jwtTokenGen= UUID.randomUUID().toString();
            user.setJwtToken(jwtTokenGen);
            userRepository.save(user);
            adminLoginResponse.setJwtToken(user.getJwtToken());
            adminLoginResponse.setName(user.getName());
        }
        else {
            adminLoginResponse.setMessage("Login failed, wrong password");
            adminLoginResponse.setStatus(false);
        }
        return adminLoginResponse;
    }

    public LogoutResponse logoutAuth(int userId, String jwtToken){

        LogoutResponse logoutResponse = new LogoutResponse();
        if (jwtToken.equals((userRepository.findByUserId(userId)).getJwtToken()) == false) {
            logoutResponse.setStatus(false);
            logoutResponse.setMessage("jwtToken invalid");

            return logoutResponse;
        }
        else{
            User user = userRepository.findByUserId(userId);
            user.setJwtToken(null);
            userRepository.save(user);

            logoutResponse.setStatus(true);
            logoutResponse.setMessage("Logged out successfully");

            return logoutResponse;
        }

    }
}
