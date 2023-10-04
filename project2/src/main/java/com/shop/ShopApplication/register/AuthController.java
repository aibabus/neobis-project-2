package com.shop.ShopApplication.register;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsRequest;

import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/log")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/credentialsCheck")
    public ResponseEntity<CheckResponse> check(@RequestBody CheckRequest request){
        return ResponseEntity.ok(authService.checkAvailability(request));
    }

}
