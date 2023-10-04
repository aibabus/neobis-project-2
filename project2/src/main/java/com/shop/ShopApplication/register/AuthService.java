package com.shop.ShopApplication.register;

import com.shop.ShopApplication.config.JwtService;

import com.shop.ShopApplication.service.UserService;
import com.shop.ShopApplication.user.Role;
import com.shop.ShopApplication.user.User;
import com.shop.ShopApplication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthResponse.builder()
                    .message("Email is already registered")
                    .build();
        }

        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            return AuthResponse.builder()
                    .message("Login is already registered")
                    .build();
        }


        var user = User.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .verified(false)
                .build();
        if (user == null){
            throw new IllegalStateException("bad credentials");
        }

        userRepository.save(user);

        return AuthResponse.builder()
                .message("now you registered")
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                ));
        var user = userRepository.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public CheckResponse checkAvailability(CheckRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            return CheckResponse.builder()
                    .message("Email is already taken")
                    .userPresent(false)
                    .build();
        }

        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            return CheckResponse.builder()
                    .message("Email is already taken")
                    .userPresent(false)
                    .build();
        }

        return CheckResponse.builder()
                .message("Email and Login is new !")
                .userPresent(true)
                .build();
    }



//    public boolean isUserEnabled(String username) {
//        Optional<User> user = userRepository.findByLogin(username);
//        return user.map(User::isEnabled).orElse(false);
//}

}
