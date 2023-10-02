package com.shop.ShopApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveRequestDto {
    private MultipartFile image;
    private String productName;
    private String shortDescription;
    private String fullDescription;
    private int price;
}
