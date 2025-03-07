package com.example.foodpick.bookmark.dto;

import com.example.foodpick.bookmark.entity.Bookmark;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookmarkResponseDto {

    private Long id;
    private Long userId;
    private Long restaurantId;
    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String homePageLink;
    private String imageLink;
    private boolean visit;
    private int visitCount;
    private LocalDateTime lastVisitDate;

    public BookmarkResponseDto(Bookmark bookmark) {
        this.id = bookmark.getId();
        this.userId = bookmark.getUser().getId();
        this.restaurantId = bookmark.getRestaurant().getId();
        this.title = bookmark.getRestaurant().getTitle();
        this.category = bookmark.getRestaurant().getCategory();
        this.address = bookmark.getRestaurant().getAddress();
        this.roadAddress = bookmark.getRestaurant().getRoadAddress();
        this.homePageLink = bookmark.getRestaurant().getHomePageLink();
        this.imageLink = bookmark.getRestaurant().getImageLink();
        this.visit = bookmark.isVisit();
        this.visitCount = bookmark.getVisitCount();
        this.lastVisitDate = bookmark.getLastVisitDate();
    }
}
