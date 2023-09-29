package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.user.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
