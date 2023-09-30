package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query("SELECT p FROM Product p WHERE p.user.user_id = :userId")
//    List<Product> findAllByUserId(int userId);
//@Query("SELECT p FROM Product p WHERE p.user = :user")@Param("user")
List<Product> findAllByUser( User user);
}
