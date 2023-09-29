package com.shop.ShopApplication.service.smsServices.smsSender;

import com.shop.ShopApplication.service.smsServices.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {
    private final TwilioConfig twilioConfig;

    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    @Autowired
    public TwilioSmsSender(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String message = smsRequest.getMessage();

            Message.creator(to, from, message).create(); // Send the SMS

            LOGGER.info("Sent SMS: {}", smsRequest);
        } else {
            throw new IllegalArgumentException("Phone number is not valid");
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validation logic here
        return true;
    }
}
