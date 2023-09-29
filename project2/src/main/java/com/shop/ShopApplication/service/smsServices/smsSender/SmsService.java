package com.shop.ShopApplication.service.smsServices.smsSender;

import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.user.User;
import com.shop.ShopApplication.user.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class SmsService {
    private final SmsSender smsSender;
    private final VerificationCodeRepository verificationCodeRepository;

    private final UserRepository userRepository;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender, VerificationCodeRepository verificationCodeRepository,UserRepository userRepository) {
        this.smsSender = smsSender;
        this.verificationCodeRepository = verificationCodeRepository;
        this.userRepository = userRepository;
    }

    public void sendVerificationCode(String phoneNumber) {
        String verificationCode = generateVerificationCode();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(5);


        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user != null) {
            VerificationCode Verificationcode = new VerificationCode();
            Verificationcode.setCode(verificationCode);
            Verificationcode.setPhoneNumber(phoneNumber);
            Verificationcode.setUser(user);
            Verificationcode.setExpirationTime(expirationTime);
            verificationCodeRepository.save(Verificationcode);
        }


        String message = "Your verification code is: " + verificationCode;
        SmsRequest smsRequest = new SmsRequest(phoneNumber, message);
        smsSender.sendSms(smsRequest);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // Generates a random 4-digit code
        return String.valueOf(code);
    }
}
