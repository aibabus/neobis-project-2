package com.shop.ShopApplication.service.smsServices.smsSender;

import com.shop.ShopApplication.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class SmsService {
    private final SmsSender smsSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender ;
    }

    public  void sendSms(SmsRequest smsRequest){
        smsSender.sendSms(smsRequest);
    }


    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // Generates a random 4-digit code
        return String.valueOf(code);
    }
}
