package com.shop.ShopApplication.service.smsServices.smsSender;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class SmsRequest {
    @NotBlank
    private final String phoneNumber;
    private final String message;



    public SmsRequest(@JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("messsage")String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
