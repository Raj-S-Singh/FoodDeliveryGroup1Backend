package com.pomato.mainPackage.model;

import java.util.Collection;
import java.util.List;

public class GetRestaurantResponse {
    private boolean status;
    String message;
    List<Restaurant> allRestaurant;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Restaurant> getAllRestaurant() {
        return allRestaurant;
    }

    public void setAllRestaurant(List<Restaurant> allRestaurant) {
        this.allRestaurant = allRestaurant;
    }
}
