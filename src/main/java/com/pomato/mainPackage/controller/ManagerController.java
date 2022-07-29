package com.pomato.mainPackage.controller;

import com.pomato.mainPackage.model.*;
import com.pomato.mainPackage.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @PostMapping(value = "/managersignup", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<ManagerSignupResponse> managerSignUp(@RequestBody ManagerSignupRequest managerSignupRequest){

        ManagerSignupResponse managerSignupResponse = managerService.registerManager(managerSignupRequest);

        if(managerSignupResponse.isStatus()){
            return new ResponseEntity<ManagerSignupResponse>(managerSignupResponse, HttpStatus.OK );
        }
        else{
            return new ResponseEntity<ManagerSignupResponse>(managerSignupResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping(value = "/deleteitem/{restaurantId}/{itemId}", produces = "application/json")
    public ResponseEntity<DeleteItemResponse> deleteItem(@PathVariable("restaurantId") int restaurantId, @PathVariable("itemId") int itemId, @RequestHeader(name = "jwtToken") String jwtToken) {


        DeleteItemResponse deleteItemResponse = managerService.deleteItem(itemId, jwtToken);
        if (deleteItemResponse.isStatus()) {
            return new ResponseEntity<>(deleteItemResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleteItemResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/additem/{restaurantId}",consumes = "application/json",produces="application/json")
    public ResponseEntity<AddItemResponse> addItemToRestaurant(@PathVariable int restaurantId, @RequestHeader(name="jwtToken") String jwtToken,@RequestBody AddItemRequest addItemRequest) {
        AddItemResponse addItemResponse = managerService.addItemToRestaurant(addItemRequest, restaurantId, jwtToken);

        if (addItemResponse.getStatus()) {
            return new ResponseEntity<AddItemResponse>(addItemResponse, HttpStatus.OK);
        }
        return new ResponseEntity<AddItemResponse>(addItemResponse, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/updateitem/{restaurantId}/{itemId}",consumes = "application/json", produces = "application/json")
    public ResponseEntity<UpdateItemResponse> updateItem(@RequestBody UpdateItemRequest item, @PathVariable int restaurantId,
                                                         @PathVariable int itemId, @RequestHeader("jwtToken") String jwtToken){

        item.setItemId(itemId);
        item.setRestaurantId(restaurantId);
        UpdateItemResponse updateItemResponse = managerService.update(item, jwtToken);

        if(updateItemResponse.isStatus()){
            return new ResponseEntity<UpdateItemResponse>(updateItemResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<UpdateItemResponse>(updateItemResponse, HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping(value = "/viewordersmanager/{userId}/{restaurantId}", produces = "application/json")
    public ResponseEntity<ViewOrderManagerResponse> viewOrdersManager(@PathVariable int restaurantId,
                                  @PathVariable int userId, @RequestHeader(name="jwtToken") String jwtToken){

        ViewOrderManagerResponse viewOrderManagerResponse = managerService.showOrders(restaurantId, userId, jwtToken);

        if(viewOrderManagerResponse.isStatus()){
            return new ResponseEntity<>(viewOrderManagerResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(viewOrderManagerResponse, HttpStatus.BAD_REQUEST);
        }


    }
}
