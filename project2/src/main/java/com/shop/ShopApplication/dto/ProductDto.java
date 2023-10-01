package com.shop.ShopApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int product_id;
    private String productName;
    private int price;
    private String shortDescription;
    private String fullDescription;
}
