package com.pomato.mainPackage.repository;

import com.pomato.mainPackage.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
    Menu findByItemId(int itemId);
}
