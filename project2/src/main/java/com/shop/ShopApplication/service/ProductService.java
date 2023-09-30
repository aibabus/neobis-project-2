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
    public Product updateProduct(int productId, Product updatedProduct);


    public List<Product> findAllProducts();
//    public List<Product> findAllUserProducts(int userId);
    public Product findSingleProduct(int id);

    public Product getProductById(int id);
}
