package com.pomato.mainPackage.repository;

import com.pomato.mainPackage.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    Restaurant findByRestaurantId(int restaurantId);
    @Query(value = "SELECT r FROM Restaurant r")
    List<Restaurant> getAllRestaurants();
}
