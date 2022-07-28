package com.pomato.mainPackage.model;

import java.util.List;

public class ViewAllOrdersAdminResponse {

    private boolean status;
    private String message;
    private List<FoodOrders> list;

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

    public List<FoodOrders> getList() {
        return list;
    }

    public void setList(List<FoodOrders> list) {
        this.list = list;
    }
}
