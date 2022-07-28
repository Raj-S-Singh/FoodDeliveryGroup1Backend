package com.pomato.mainPackage.model;

import java.util.List;

public class ViewOrderCustomerResponse {
    private String message;
    private boolean status;
    private List<FoodOrders> foodOrders;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<FoodOrders> getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(List<FoodOrders> foodOrders) {
        this.foodOrders = foodOrders;
    }
}
