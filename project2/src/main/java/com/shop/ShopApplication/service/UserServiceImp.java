package com.shop.ShopApplication.service;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.user.User;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

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
    public User updateUser(User user,
                           MultipartFile avatar,
                           String firstName,
                           String lastName,
                           String email,
                           String phoneNumber,
                           LocalDate birthDate) {

        User existingUser = userRepository.findById(user.getUser_id()).orElse(null);

        if (existingUser == null) {
            return null;
        }

        if (!existingUser.getEmail().equals(email)) {
            return null;
        }

        // Update user information if provided
        if (avatar != null) {
            String fileName = StringUtils.cleanPath(avatar.getOriginalFilename());

            if (fileName.contains("..")) {
                System.out.println("Not a valid file");
            }

            try {
                existingUser.setAvatar(Base64.getEncoder().encodeToString(avatar.getBytes()));
            } catch (IOException | java.io.IOException e) {
                e.printStackTrace();
            }
        }
        if (firstName != null) {
            existingUser.setFirstName(firstName);
        }
        if (lastName != null) {
            existingUser.setLastName(lastName);
        }
        if (email != null) {
            existingUser.setEmail(email);
        }
        if (phoneNumber != null) {
            existingUser.setPhoneNumber(phoneNumber);
        }
        if (birthDate != null) {
            existingUser.setBirthDate(birthDate);
        }

        // Save the updated user back to the database
        return userRepository.save(existingUser);
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

}
