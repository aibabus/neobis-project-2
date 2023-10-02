package com.shop.ShopApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullDto {
    private int user_id;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String avatar;
    private String phoneNumber;
    private LocalDate birthDate;
}
