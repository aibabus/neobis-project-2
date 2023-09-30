package com.shop.ShopApplication.service;



import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<User> getUser();
    User saveUser (User user);
    User getSingleUser (int id);
    void deleteUser (int id);
    public boolean updatePhoneNumber(int userId, String newPhoneNumber);
//    User updateUser (User user,
//                     MultipartFile avatar,
//                     String firstName,
//                     String lastName,
//                     String email,
//                     String phoneNumber,
//                     LocalDate birthDate);
    public User updateUser(int userId, User updatedUser);
    public User getUserById(int userId);
    public boolean verifyPhoneNumber(String phoneNumber, String code);
    public User addOrRemoveFavoriteProduct(int userId, int productId);
    public boolean doesUserExistByEmail(String email);
    public boolean doesUserExistByLogin(String login);

    List<Product> findAllUserProducts(User user);

    public boolean findByPhoneNumberAndVerified(String newPhoneNumber);
}
