package com.shop.ShopApplication.service;

import com.shop.ShopApplication.dto.ProductFullDto;
import com.shop.ShopApplication.dto.ProductListDto;
import com.shop.ShopApplication.dto.ProductSaveRequestDto;
import com.shop.ShopApplication.exceptions.VerificationException;
import com.shop.ShopApplication.repo.ProductRepository;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    @Transactional
    public Product saveProduct(ProductSaveRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (!user.getVerified()) {
            System.out.println("User is not verified ");
            throw new VerificationException("User is not verified. To add a new product, you have to verify your phone number before.");
        }

        Product product = new Product();
        String fileName = StringUtils.cleanPath(requestDto.getImage().getOriginalFilename());

        if (fileName.contains("..")) {
            System.out.println("Not a valid file");
        }

        try {
            product.setImage(Base64.getEncoder().encodeToString(requestDto.getImage().getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        product.setShortDescription(requestDto.getShortDescription());
        product.setFullDescription(requestDto.getFullDescription());
        product.setProductName(requestDto.getProductName());
        product.setPrice(requestDto.getPrice());
        product.setUser(user);

        return productRepository.save(product);
    }

//    @Override
//    public ProductDto updateProduct(
//            int product_id,
//            MultipartFile image,
//            String productName,
//            String shortDescription,
//            String fullDescription,
//            Integer price
//    ) {
//
//        ProductDto existingProduct = productRepository.findById(product_id).orElse(null);
//
//        if (existingProduct == null) {
//
//            return null;
//        }
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User authenticatedUser = (User) authentication.getPrincipal();
//
//        if (authenticatedUser.getUser_id() != existingProduct.getUser().getUser_id()) {
//
//            return null;
//        }
//
//        if (image != null) {
//            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
//
//            if (fileName.contains("..")) {
//                System.out.println("Not a valid file");
//            }
//
//            try {
//                existingProduct.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (productName != null) {
//            existingProduct.setProductName(productName);
//        }
//        if (shortDescription != null) {
//            existingProduct.setShortDescription(shortDescription);
//        }
//        if (fullDescription != null) {
//            existingProduct.setFullDescription(fullDescription);
//        }
//        if (price != null) {
//            existingProduct.setPrice(price);
//        }
//
//        return productRepository.save(existingProduct);
//    }


    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }
    @Override
    public Product updateProduct(int productId, Product updatedProduct) {
        System.out.println("Entering updateProduct method");

        Product existingProduct = productRepository.findById(productId).orElse(null);


        if (existingProduct == null) {
            return null;

        }

        BeanUtils.copyProperties(updatedProduct, existingProduct, "product_id");

        return productRepository.save(existingProduct);
    }
    @Override
    public List<ProductListDto> findAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductListDto> productListDtos = products.stream()
                .map(product -> new ProductListDto(
                        product.getProduct_id(),
                        product.getImage(),
                        product.getProductName(),
                        product.getPrice(),
                        product.getNumberOfLikes() // Assuming you have a getter for the like count
                ))
                .collect(Collectors.toList());

        return productListDtos;
    }

//    @Override
//    public List<ProductDto> findAllUserProducts(int userId) {
//        return productRepository.findAllByUserUserId(userId);
//    }



    @Override
    public ProductFullDto findSingleProduct(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
                Product product = productOptional.get();
                return new ProductFullDto(
                        product.getProduct_id(),
                        product.getImage(),
                        product.getProductName(),
                        product.getPrice(),
                        product.getShortDescription(),
                        product.getFullDescription(),
                        product.getNumberOfLikes()
                );
            }

        throw new RuntimeException("ProductDto is not find " + id);
    }

}
