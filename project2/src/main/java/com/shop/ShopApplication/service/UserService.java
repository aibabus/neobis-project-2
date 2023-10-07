package com.shop.ShopApplication.service;



import com.shop.ShopApplication.dto.ProductListDto;
import com.shop.ShopApplication.dto.UserFullDto;
import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<User> getUser();
    User saveUser (User user);
    public UserFullDto getSingleUser(int id);
    public UserFullDto getSingleUserByLogin(String login);
    void deleteUser (int id);
    public ResponseEntity<String> updatePhoneNumber(int userId, String newPhoneNumber);
    public User updateUser(int userId, User updatedUser);
    public User getUserById(int userId);
    public boolean verifyPhoneNumber(String phoneNumber, String code);
    public User addOrRemoveFavoriteProduct(int userId, int productId);
    public boolean doesUserExistByEmail(String email);
    public boolean doesUserExistByLogin(String login);
    public List<ProductListDto> getUserProductList(int userId);
    public List<ProductListDto> getFavoriteProductList(int userId);

    List<Product> findAllUserProducts(User user);

    public boolean findByPhoneNumberAndVerified(String newPhoneNumber);
}
