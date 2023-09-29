package com.shop.ShopApplication.service;
import com.shop.ShopApplication.repo.ProductRepository;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import com.shop.ShopApplication.user.VerificationCode;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getSingleUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        }
        throw new RuntimeException("User is not find " + id);
    }

    @Override
    public void deleteUser(int id) {

        userRepository.deleteById(id);
    }

    @Override
    public boolean updatePhoneNumber(int userId, String newPhoneNumber) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPhoneNumber(newPhoneNumber);
            user.setVerified(false);
            userRepository.save(user);
            return true;
        }

        return false;
    }
//    @Override
//    public User updateUser(User user,
//                           MultipartFile avatar,
//                           String firstName,
//                           String lastName,
//                           String email,
//                           String phoneNumber,
//                           LocalDate birthDate) {
//
//        User existingUser = userRepository.findById(user.getUser_id()).orElse(null);
//
//        if (existingUser == null) {
//            return null;
//        }
//
//        if (!existingUser.getEmail().equals(email)) {
//            return null;
//        }
//
//        // Update user information if provided
//        if (avatar != null) {
//            String fileName = StringUtils.cleanPath(avatar.getOriginalFilename());
//
//            if (fileName.contains("..")) {
//                System.out.println("Not a valid file");
//            }
//
//            try {
//                existingUser.setAvatar(Base64.getEncoder().encodeToString(avatar.getBytes()));
//            } catch (IOException | java.io.IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (firstName != null) {
//            existingUser.setFirstName(firstName);
//        }
//        if (lastName != null) {
//            existingUser.setLastName(lastName);
//        }
//        if (email != null) {
//            existingUser.setEmail(email);
//        }
//        if (phoneNumber != null) {
//            existingUser.setPhoneNumber(phoneNumber);
//        }
//        if (birthDate != null) {
//            existingUser.setBirthDate(birthDate);
//        }
//
//        return userRepository.save(existingUser);
//    }

    @Override
    public User updateUser(int userId, User updatedUser) {
        System.out.println("Entering updateUser method");

        User existingUser = userRepository.findById(userId).orElse(null);


        if (existingUser == null) {
            return null;

        }

        BeanUtils.copyProperties(updatedUser, existingUser, "user_id", "email","role","login","password","verified","enabled","phone_number");

        return userRepository.save(existingUser);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User addOrRemoveFavoriteProduct(int userId, int productId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {

            return null;
        }

        Product product = productService.getProductById(productId);

        if (product != null) {
            if (user.getFavoriteProducts().contains(product)) {
                user.getFavoriteProducts().remove(product);
            } else {
                user.getFavoriteProducts().add(product);
            }

            return userRepository.save(user);
        } else {

            return null;
        }
    }

    @Override
    public boolean verifyPhoneNumber(String phoneNumber, String code) {
        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (user == null) {
            return false;
        }

        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumberAndUser(phoneNumber, user);

        if (verificationCode == null || !verificationCode.getCode().equals(code)) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(verificationCode.getExpirationTime())) {
            return false;
        }

        verificationCode.setPhoneConfirmedAt(now);

        user.setVerified(true);

        userRepository.save(user);

        verificationCodeRepository.delete(verificationCode);

        return true;
    }

    @Override
    public boolean doesUserExistByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    @Override
    public boolean doesUserExistByLogin(String login) {
        Optional<User> userOptional = userRepository.findByLogin(login);
        return userOptional.isPresent();
    }

    @Override
    public boolean findByPhoneNumberAndVerified(String newPhoneNumber) {
        User user = userRepository.findByPhoneNumber(newPhoneNumber);
        if(user.getVerified() == true){
            return true;
        }
        return false;
    }

}
