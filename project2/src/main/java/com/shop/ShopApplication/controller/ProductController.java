package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.repo.ProductRepository;
import com.shop.ShopApplication.service.ProductService;
import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping("/saveProduct")
    public Product saveProduct(@RequestBody MultipartFile image, String productName, String shortDescription, String fullDescription, int price){
        return productService.saveProduct(image,productName,shortDescription,fullDescription,price);
    }

    @PutMapping("/updateProduct/{productId}")
    public Product updateProduct(
            @PathVariable int product_id,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String shortDescription,
            @RequestParam(required = false) String fullDescription,
            @RequestParam(required = false) Integer price
    ) {
        return productService.updateProduct(product_id, image, productName, shortDescription, fullDescription, price);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@RequestParam int id){
        productRepository.deleteById(id);
    }
    @GetMapping("/findAllProducts")
    public List<Product> allProducts(){
        return productService.findAllProducts();
    }
    @GetMapping("/findSingleProduct")
    public Product findSingleUser(@PathVariable int id){
        return productService.findSingleProduct(id);
    }

}
