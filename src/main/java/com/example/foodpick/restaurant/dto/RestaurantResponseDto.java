package com.example.foodpick.restaurant.dto;

import com.example.foodpick.restaurant.entity.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantResponseDto {
    private Long id;
    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String homePageLink;
    private String imageLink;

    public RestaurantResponseDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.title = restaurant.getTitle();
        this.category = restaurant.getCategory();
        this.address = restaurant.getAddress();
        this.roadAddress = restaurant.getRoadAddress();
        this.homePageLink = restaurant.getHomePageLink();
        this.imageLink = restaurant.getImageLink();
    }
}
