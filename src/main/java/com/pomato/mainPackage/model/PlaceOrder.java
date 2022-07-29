package com.pomato.mainPackage.model;

import java.util.List;

public class PlaceOrder {
    private String address;
    private float amount;
    private String paymentMethod;

    private List<ItemListReturned> listOfItems;
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
    public List<ItemListReturned> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(List<ItemListReturned> listOfItems) {
        this.listOfItems = listOfItems;
    }
}
