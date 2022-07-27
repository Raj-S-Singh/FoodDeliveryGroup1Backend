package com.pomato.mainPackage.repository;

import com.pomato.mainPackage.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    Restaurant findByRestaurantId();
}
