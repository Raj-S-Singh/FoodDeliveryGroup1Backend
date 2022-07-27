package com.pomato.mainPackage.services;

import com.pomato.mainPackage.model.AddItemResponse;
import com.pomato.mainPackage.model.CustomerSignupResponse;
import com.pomato.mainPackage.model.User;
import com.pomato.mainPackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    UserRepository userRepository;

    @Value("${pepper}")
    String pepper;

    public CustomerSignupResponse register(User user){

        User newUser = new User();
        User currentUser = userRepository.findByEmail(user.getEmail());
        CustomerSignupResponse customerSignupResponse = new CustomerSignupResponse();

        if(currentUser != null){
            customerSignupResponse.setMessage("Customer already exists.");
            customerSignupResponse.setStatus(false);
        }
        else{
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(user.getPassword() + pepper, salt );
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            user.setJwtToken(BCrypt.gensalt());
            //user = userRepository.save(user);
            newUser = userRepository.save(user);

            customerSignupResponse.setUserId(newUser.getUserId());
            customerSignupResponse.setName(newUser.getName());
            customerSignupResponse.setEmail(newUser.getEmail());
            customerSignupResponse.setRole(newUser.getRole());
            customerSignupResponse.setContactNumber(newUser.getContactNumber());
            customerSignupResponse.setJwtToken(newUser.getJwtToken());

            customerSignupResponse.setMessage("Signup successfull");
            customerSignupResponse.setStatus(true);
        }
        return customerSignupResponse;
    }
}
