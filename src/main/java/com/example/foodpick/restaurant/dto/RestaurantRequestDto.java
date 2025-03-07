package com.example.foodpick.restaurant.dto;

import lombok.Data;

@Data
public class RestaurantRequestDto {
    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String homePageLink;
    private String imageLink;
}
