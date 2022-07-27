package com.pomato.mainPackage.repository;

import com.pomato.mainPackage.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
    Menu findByItemId(int itemId);
    Menu findByName(String name);

    @Query(value = "SELECT * FROM Menu WHERE restaurantId = restaurant_Id",nativeQuery=true)
    Collection<Menu> findRestaurantMenu(int restaurant_Id);
}
