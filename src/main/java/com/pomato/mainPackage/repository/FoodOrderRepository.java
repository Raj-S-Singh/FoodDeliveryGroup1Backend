package com.pomato.mainPackage.repository;

import com.pomato.mainPackage.model.FoodOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrders,Integer> {
    FoodOrders findByOrderId(int orderId);

    @Query("Select e from FoodOrders e where e.restaurantId=?1")
    List<FoodOrders> findAllByRestaurantId(int restaurantId);

    @Query("Select e from FoodOrders e where e.userId=?1")
    List<FoodOrders> getAllByUserId(int userId);



}
