package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.repo.ProductRepository;
import com.shop.ShopApplication.service.ProductService;
import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping("/saveProduct")
    @Transactional
    public ResponseEntity<String> saveProduct(@RequestBody MultipartFile image, String productName, String shortDescription, String fullDescription, int price){
        productService.saveProduct(image,productName,shortDescription,fullDescription,price);
        return ResponseEntity.ok("Product saved successfully.");
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable int product_id,
            @RequestBody Product updatedProduct
    ) {
        Product product = productService.getProductById(product_id);

        if (product == null) {
            System.out.println("product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Product updatedProductData = productService.updateProduct(product_id, updatedProduct);

        if (updatedProductData == null) {
            System.out.println("There is no data");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(updatedProductData);
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
