package com.thangnd.fastfoodstore.repository;

import com.thangnd.fastfoodstore.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
}
