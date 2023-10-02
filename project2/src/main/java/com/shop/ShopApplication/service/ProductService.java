package com.shop.ShopApplication.service;

import com.shop.ShopApplication.dto.ProductFullDto;
import com.shop.ShopApplication.dto.ProductListDto;
import com.shop.ShopApplication.dto.ProductSaveRequestDto;
import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public Product saveProduct(ProductSaveRequestDto requestDto);
    public Product updateProduct(int productId, Product updatedProduct);


    public List<ProductListDto> findAllProducts();
//    public List<ProductDto> findAllUserProducts(int userId);
    public ProductFullDto findSingleProduct(int id);

    public Product getProductById(int id);
}
