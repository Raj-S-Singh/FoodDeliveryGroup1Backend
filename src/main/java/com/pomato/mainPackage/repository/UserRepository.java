package com.pomato.mainPackage.repository;

import com.pomato.mainPackage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserId(int userId);
    User findByEmail(String email);

    User findByJwtToken(String jwtToken);
}
