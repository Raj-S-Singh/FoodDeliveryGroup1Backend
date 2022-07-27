package com.pomato.mainPackage.services;

import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.repository.MenuRepository;
import com.pomato.mainPackage.repository.RestaurantRepository;
import com.pomato.mainPackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    @Value("${pepper}")
    String pepper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    MenuRepository menuRepository;

    public ManagerSignupResponse registerManager(ManagerSignupRequest managerSignupRequest){

        User newUser = new User();
        Restaurant restaurant = new Restaurant();
        User user = userRepository.findByEmail(managerSignupRequest.getEmail());
        ManagerSignupResponse managerSignupResponse = new ManagerSignupResponse();

        if(user != null){
            managerSignupResponse.setStatus(false);
            managerSignupResponse.setMessage("email already exist");

            return managerSignupResponse;
        }
        else{
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(managerSignupRequest.getPassword()+pepper,salt);
            newUser.setPassword(hashedPassword);
            newUser.setSalt(salt);
            newUser.setName(managerSignupRequest.getName());
            newUser.setContactNumber(managerSignupRequest.getContactNumber());
            newUser.setEmail(managerSignupRequest.getEmail());
            newUser.setRole(managerSignupRequest.getRole());
            String jwtToken = BCrypt.gensalt();
            newUser.setJwtToken(jwtToken);

            restaurant.setRestaurantAddress(managerSignupRequest.getRestaurantAddress());
            restaurant.setRestaurantImage(managerSignupRequest.getRestaurantImage());
            restaurant.setRestaurantName(managerSignupRequest.getRestaurantName());

            User createdUser = userRepository.save(newUser);
            if(createdUser == null){
                managerSignupResponse.setStatus(false);
                managerSignupResponse.setMessage("User object cannot be created");
                return managerSignupResponse;
            }
            restaurant.setUserId(createdUser.getUserId());
            Restaurant createdRestaurant = restaurantRepository.save(restaurant);

            if(createdRestaurant == null){
                managerSignupResponse.setStatus(false);
                managerSignupResponse.setMessage("Restaurant Object cannot be created");
                return managerSignupResponse;
            }

            return managerSignupResponse;
        }
    }

    public AddItemResponse addItemToRestaurant(AddItemRequest request,int restaurantId,String jwtToken){
        Menu currentItem = menuRepository.findByName(request.getName());
        Menu managerItem = new Menu();
        AddItemResponse response= new AddItemResponse();
        if(jwtToken.equals(userRepository.findByUserId(request.getUserId()).getJwtToken())==false){
            response.setStatus(false);
            response.setMessage("jwtToken Invalid.");
        }
        else if(currentItem!=null){
            response.setStatus(false);
            response.setMessage("Item already exists in the database");
        } else {
            managerItem.setRestaurantId(restaurantId);
            managerItem.setItemImage(request.getItemImage());
            managerItem.setDescription(request.getDescription());
            managerItem.setItemImage(request.getItemImage());
            managerItem.setCuisineType(request.getCuisineType());
            managerItem.setPrice(request.getPrice());
            managerItem.setName(request.getName());
            managerItem = menuRepository.save(managerItem);

            response.setStatus(true);
            response.setMessage("Item added successfully.");
            response.setItemId(managerItem.getItemId());
            response.setRestaurantId(managerItem.getRestaurantId());
            response.setCuisineType(managerItem.getCuisineType());
            response.setName(managerItem.getName());
            response.setDescription(managerItem.getDescription());
            response.setPrice(managerItem.getPrice());
            response.setItemImage(managerItem.getItemImage());
        }
        return response;
    }
}
