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
    User updateUser (User user,
                     MultipartFile avatar,
                     String firstName,
                     String lastName,
                     String email,
                     String phoneNumber,
                     LocalDate birthDate);
    public User getUserById(int userId);
    public User addOrRemoveFavoriteProduct(int userId, int productId);
    public boolean doesUserExistByEmail(String email);
    public boolean doesUserExistByLogin(String login);

}
