package com.shop.ShopApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFullDto {
    private int product_id;
    private String image;
    private String productName;
    private int price;
    private String shortDescription;
    private String fullDescription;
    private int numberOfLikes;
}
