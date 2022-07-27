package com.pomato.mainPackage.repository;

import com.pomato.mainPackage.model.FoodOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrders,Integer> {
    FoodOrders findByOrderId(int orderId);
}
