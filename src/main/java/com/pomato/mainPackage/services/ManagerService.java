package com.pomato.mainPackage.services;

import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.repository.FoodOrderRepository;
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

    @Autowired
    FoodOrderRepository foodOrderRepository;


    public ManagerSignupResponse registerManager(ManagerSignupRequest managerSignupRequest) {

        User newUser = new User();
        Restaurant restaurant = new Restaurant();
        User user = userRepository.findByEmail(managerSignupRequest.getEmail());
        ManagerSignupResponse managerSignupResponse = new ManagerSignupResponse();

        if (user != null) {
            managerSignupResponse.setStatus(false);
            managerSignupResponse.setMessage("email already exist");

            return managerSignupResponse;
        } else {
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(managerSignupRequest.getPassword() + pepper, salt);
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
            if (createdUser == null) {
                managerSignupResponse.setStatus(false);
                managerSignupResponse.setMessage("User object cannot be created");
                return managerSignupResponse;
            }
            restaurant.setUserId(createdUser.getUserId());
            Restaurant createdRestaurant = restaurantRepository.save(restaurant);

            if (createdRestaurant == null) {
                managerSignupResponse.setStatus(false);
                managerSignupResponse.setMessage("Restaurant Object cannot be created");
                return managerSignupResponse;
            }

            managerSignupResponse.setStatus(true);
            managerSignupResponse.setMessage("Manager signup successful");
            managerSignupResponse.setUserId(createdUser.getUserId());
            managerSignupResponse.setName(createdUser.getName());
            managerSignupResponse.setRole(createdUser.getRole());
            managerSignupResponse.setContactNumber(createdUser.getContactNumber());
            managerSignupResponse.setEmail(createdUser.getEmail());
            managerSignupResponse.setJwtToken(createdUser.getJwtToken());
            managerSignupResponse.setRestaurantId(createdRestaurant.getRestaurantId());
            managerSignupResponse.setRestaurantName(createdRestaurant.getRestaurantName());
            managerSignupResponse.setRestaurantAddress(createdRestaurant.getRestaurantAddress());
            managerSignupResponse.setRestaurantImage(createdRestaurant.getRestaurantImage());
            return managerSignupResponse;
        }
    }

    public AddItemResponse addItemToRestaurant(AddItemRequest request, int restaurantId, String jwtToken) {
        Menu currentItem = menuRepository.findByName(request.getName());
        Menu managerItem = new Menu();
        AddItemResponse response = new AddItemResponse();
        if (userRepository.findByJwtToken(jwtToken)==null) {
            response.setStatus(false);
            response.setMessage("jwtToken Invalid.");
        } else if (currentItem != null && currentItem.getRestaurantId()==restaurantId) {
            response.setStatus(false);
            response.setMessage("Item already exists in the restaurant.");
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


    public DeleteItemResponse deleteItem(int itemId, String jwtToken) {
        Menu newItem = menuRepository.findByItemId(itemId);
        DeleteItemResponse deleteItemResponse = new DeleteItemResponse();
        User newUser = userRepository.findByJwtToken(jwtToken);

        if(newUser == null){
            deleteItemResponse.setStatus(false);
            deleteItemResponse.setMessage("jwtToken invalid.");

            return deleteItemResponse;
        }

        if (newItem == null) {
            deleteItemResponse.setStatus(false);
            deleteItemResponse.setMessage("Item doesn't exist.");
        } else {
            menuRepository.deleteById(itemId);
            deleteItemResponse.setStatus(true);
            deleteItemResponse.setMessage("Item deleted from menu");
        }

        return deleteItemResponse;
    }

    public UpdateItemResponse update(UpdateItemRequest item, String jwtToken) {
        UpdateItemResponse updateItemResponse = new UpdateItemResponse();

        if (userRepository.findByJwtToken(jwtToken)==null) {
            updateItemResponse.setStatus(false);
            updateItemResponse.setMessage("jwtToken invalid");

            return updateItemResponse;
        }

        Menu curItem = menuRepository.findByItemId(item.getItemId());
        if (curItem == null) {
            updateItemResponse.setStatus(false);
            updateItemResponse.setMessage("item with given id not found");
            return updateItemResponse;
        } else {
            Menu updatedItem = new Menu();
            updatedItem.setItemId(item.getItemId());
            updatedItem.setItemImage(item.getItemImage());
            updatedItem.setName(item.getName());
            updatedItem.setDescription(item.getDescription());
            updatedItem.setPrice(item.getPrice());
            updatedItem.setCuisineType(item.getCuisineType());
            updatedItem.setRestaurantId(item.getRestaurantId());
            menuRepository.save(updatedItem);

            updateItemResponse.setStatus(true);
            updateItemResponse.setMessage("Item updated");
            updateItemResponse.setMenu(updatedItem);
            return updateItemResponse;
        }
    }

    public ViewOrderManagerResponse showOrders(int restaurantId, int userId, String jwtToken){

        ViewOrderManagerResponse viewOrderManagerResponse = new ViewOrderManagerResponse();

        if (!jwtToken.equals((userRepository.findByUserId(userId)).getJwtToken())) {
            viewOrderManagerResponse.setStatus(false);
            viewOrderManagerResponse.setMessage("jwtToken invalid");

            return viewOrderManagerResponse;
        }

        Restaurant restaurant = restaurantRepository.findByRestaurantId(restaurantId);
        if(restaurant == null){
            viewOrderManagerResponse.setMessage("Invalid Restaurant id");
            viewOrderManagerResponse.setStatus(false);

            return viewOrderManagerResponse;
        }
        else{
            viewOrderManagerResponse.setMessage("Fetch order");
            viewOrderManagerResponse.setStatus(true);
            viewOrderManagerResponse.setList(foodOrderRepository.findAllByRestaurantId(restaurantId));

            return viewOrderManagerResponse;
        }
    }
}
