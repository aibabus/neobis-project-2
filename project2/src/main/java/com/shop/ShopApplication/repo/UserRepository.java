package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);

    User findByPhoneNumber(String phoneNumber);

}
