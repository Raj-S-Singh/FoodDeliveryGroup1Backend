package com.pomato.mainPackage.model;

public class PlaceOrder {
    private String address;
    private float amount;
    private String paymentMethod;
    private String listOfItems;
    private int restaurantId;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(String listOfItems) {
        this.listOfItems = listOfItems;
    }
}
