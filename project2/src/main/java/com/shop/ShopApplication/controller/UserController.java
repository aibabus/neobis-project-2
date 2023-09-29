package com.shop.ShopApplication.controller;
import com.shop.ShopApplication.service.UserService;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsRequest;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import com.shop.ShopApplication.user.Product;
import com.shop.ShopApplication.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    private final SmsService smsService;

    @GetMapping("/findUsers")
    public List<User> findAllUsers(){
        return userService.getUser();
    }

    @GetMapping("/findUser/{id}")
    public User findUser(@PathVariable int id){
        return userService.getSingleUser(id);
    }
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@RequestParam int id){
        userService.deleteUser(id);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(
            @RequestParam(required = false) MultipartFile avatar,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) LocalDate birthDate
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();


        User updatedUser = userService.updateUser(
                authenticatedUser,
                avatar,
                firstName,
                lastName,
                email,
                phoneNumber,
                birthDate
        );

        if (updatedUser == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(updatedUser);
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

}
