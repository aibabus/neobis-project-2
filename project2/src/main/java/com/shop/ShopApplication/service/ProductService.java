package com.shop.ShopApplication.service;

import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product saveProduct(MultipartFile image,
                        String productName,
                        String shortDescription,
                        String fullDescription,
                        int price);
    public Product updateProduct(
            int product_id,
            MultipartFile image,
            String productName,
            String shortDescription,
            String fullDescription,
            Integer price
    );

    public List<Product> findAllProducts();
    public Product findSingleProduct(int id);

}
