package com.pomato.mainPackage.model;

public class PlaceOrderResponse {
    private String message;
    private boolean status;
    private FoodOrders foodOrders;

    public FoodOrders getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(FoodOrders foodOrders) {
        this.foodOrders = foodOrders;
    }

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
}
