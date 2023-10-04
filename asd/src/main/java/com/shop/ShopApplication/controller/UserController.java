package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.register.AuthService;
import com.shop.ShopApplication.service.UserService;
import com.shop.ShopApplication.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

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
    


}
