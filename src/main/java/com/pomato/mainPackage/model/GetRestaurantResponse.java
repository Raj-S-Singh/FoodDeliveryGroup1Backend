package com.pomato.mainPackage.model;

import java.util.Collection;

public class GetRestaurantResponse {
    String message;
    Collection<Restaurant> allRestaurant;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<Restaurant> getAllRestaurant() {
        return allRestaurant;
    }

    public void setAllRestaurant(Collection<Restaurant> allRestaurant) {
        this.allRestaurant = allRestaurant;
    }
}
