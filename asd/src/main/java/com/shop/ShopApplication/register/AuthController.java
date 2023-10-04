package com.shop.ShopApplication.register;

import com.shop.ShopApplication.register.token.ConfirmationResponse;
import com.shop.ShopApplication.register.token.ResendRequest;
import com.shop.ShopApplication.register.token.ResendResponse;
import com.shop.ShopApplication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));

    }

    @PostMapping("/log")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request ){
        return ResponseEntity.ok(authService.login(request));

    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestParam("conToken") String conToken) {
        String response = authService.confirmToken(conToken);
        return ResponseEntity.ok(response);
    }
//    public String confirm(@RequestParam("conToken") String conToken) {
//        return authService.confirmToken(conToken);
//    }

    @PostMapping("/resend-confirmation")
    public ResponseEntity<ResendResponse> resendConfirmation(@RequestBody ResendRequest request){
        return ResponseEntity.ok(authService.resendConfirmation(request));
    }

    @GetMapping("/enabled")
    public int checkUserEnabled(@RequestParam String username) {
        // Call a method in your AuthService to check if the user is enabled
        boolean isEnabled = authService.isUserEnabled(username);

        return isEnabled ? 1 : 0;
    }

}
