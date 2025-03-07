package com.example.foodpick.restaurant.controller;

import com.example.foodpick.bookmark.dto.BookmarkResponseDto;
import com.example.foodpick.restaurant.dto.RestaurantRequestDto;
import com.example.foodpick.restaurant.dto.RestaurantResponseDto;
import com.example.foodpick.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 네이버 검색
    @GetMapping("/search")
    public ResponseEntity<RestaurantResponseDto> searchNaverRestaurants(@RequestParam("query") String query) {
        RestaurantResponseDto responseDto = restaurantService.search(query);
        return ResponseEntity.ok(responseDto);
    }

    // 음식점 목록 조회
    @GetMapping
    public ResponseEntity<Page<RestaurantResponseDto>> getRestaurants(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(required = false) String keyword) {
        Page<RestaurantResponseDto> restaurants = restaurantService.getRestaurants(keyword, page, size);

        return ResponseEntity.ok(restaurants);
    }

    // 북마크 추가
    @PostMapping("/bookmarks")
    public ResponseEntity<BookmarkResponseDto> addBookmark(@RequestParam("userId") Long userId, @RequestBody RestaurantRequestDto requestDto) {
        BookmarkResponseDto responseDto = restaurantService.addBookmark(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 북마크 목록 조회 (User별)
    @GetMapping("/bookmarks")
    public ResponseEntity<List<BookmarkResponseDto>> getBookmarks(@RequestParam("userId") Long userId) {
        List<BookmarkResponseDto> bookmarks = restaurantService.findAllBookmarks(userId);
        return ResponseEntity.ok(bookmarks);
    }

    // 북마크 삭제
    @DeleteMapping("/bookmarks/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable("id") Long id) {
        restaurantService.deleteBookmark(id);
        return ResponseEntity.noContent().build();
    }

    // 방문 기록 추가
    @PostMapping("/bookmarks/{id}/visit")
    public ResponseEntity<Void> addVisit(@PathVariable("id") Long id) {
        restaurantService.addVisit(id);
        return ResponseEntity.ok().build();
    }
}
