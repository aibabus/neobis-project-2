package com.shop.ShopApplication.controller;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.service.UserService;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SmsService smsService;
    private final UserRepository userRepository;

    @GetMapping("/findUsers")
    public List<User> findAllUsers() {
        return userService.getUser();
    }

    @GetMapping("/findUser/{id}")
    public User findUser(@PathVariable int id) {
        return userService.getSingleUser(id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
    }


    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable int userId,
            @RequestBody User updatedUser
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userService.getUserById(userId);

        if (authenticatedUser == null) {
            System.out.println("user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User updatedUserData = userService.updateUser(userId, updatedUser);

        if (updatedUserData == null) {
            System.out.println("There is no data");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(updatedUserData);
    }


    @GetMapping("/favorite-products/{userId}")
    public ResponseEntity<Set<Product>> getFavoriteProducts(@PathVariable int userId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Set<Product> favoriteProducts = user.getFavoriteProducts();

        return ResponseEntity.ok(favoriteProducts);
    }

    @PutMapping("/{userId}/favorite-products/{productId}")
    public ResponseEntity<User> addOrRemoveFavoriteProduct(
            @PathVariable int userId,
            @PathVariable int productId
    ) {
        User updatedUser = userService.addOrRemoveFavoriteProduct(userId, productId);

        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/update-phone-number/{userId}")
    public ResponseEntity<String> updatePhoneNumber(
            @PathVariable int userId,
            @RequestParam String newPhoneNumber
    ) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (userService.findByPhoneNumberAndVerified(newPhoneNumber)) {
                return ResponseEntity.badRequest().body("Phone number is already in use by another verified user.");
            }


            user.setPhoneNumber(newPhoneNumber);
            user.setVerified(false);
            userRepository.save(user);

            return ResponseEntity.ok("Phone number was updated.");
        }

        return ResponseEntity.notFound().build();
    }




    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String phoneNumber) {
        smsService.sendVerificationCode(phoneNumber);
        return ResponseEntity.ok("Verification code sent successfully.");
    }


    @PostMapping("/verify-phone-number")
    public ResponseEntity<String> verifyPhoneNumber(
            @RequestParam String phoneNumber,
            @RequestParam String code
    ) {
        boolean verificationResult = userService.verifyPhoneNumber(phoneNumber, code);

        if (verificationResult) {
            return ResponseEntity.ok("Phone number verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Phone number verification failed.");
        }
    }

}
