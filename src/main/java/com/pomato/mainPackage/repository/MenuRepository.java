package com.pomato.mainPackage.repository;

import com.pomato.mainPackage.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
    Menu findByItemId(int itemId);
    Menu findByName(String name);

    @Query(value = "SELECT m FROM Menu m WHERE m.restaurantId = ?1")
    List<Menu> findRestaurantMenu(int restaurant_Id);
}
