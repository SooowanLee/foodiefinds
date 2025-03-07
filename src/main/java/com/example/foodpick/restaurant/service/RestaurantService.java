package com.example.foodpick.restaurant.service;

import com.example.foodpick.bookmark.dto.BookmarkResponseDto;
import com.example.foodpick.bookmark.entity.Bookmark;
import com.example.foodpick.bookmark.repository.BookmarkRepository;
import com.example.foodpick.restaurant.dto.RestaurantRequestDto;
import com.example.foodpick.restaurant.dto.RestaurantResponseDto;
import com.example.foodpick.restaurant.entity.Restaurant;
import com.example.foodpick.restaurant.repository.RestaurantRepository;
import com.example.foodpick.user.entity.User;
import com.example.foodpick.user.repository.UserRepository;
import com.example.foodpick.web.naver.NaverClient;
import com.example.foodpick.web.naver.dto.SearchImageReq;
import com.example.foodpick.web.naver.dto.SearchImageRes;
import com.example.foodpick.web.naver.dto.SearchLocalReq;
import com.example.foodpick.web.naver.dto.SearchLocalRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final NaverClient naverClient;
    private final RestaurantRepository restaurantRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    // 네이버 검색 후 DTO 반환
    public RestaurantResponseDto search(String query) {
        SearchLocalReq searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        SearchLocalRes searchLocalRes = naverClient.localSearch(searchLocalReq);

        if (searchLocalRes.getTotal() > 0) {
            SearchLocalRes.SearchLocalItem searchLocalItem = searchLocalRes.getItems().stream().findFirst().get();
            String imageQuery = searchLocalItem.getTitle().replaceAll("<[^>]*", "");

            SearchImageReq searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);

            SearchImageRes searchImageRes = naverClient.searchImage(searchImageReq);

            RestaurantResponseDto dto = new RestaurantResponseDto();
            if (searchImageRes.getTotal() > 0) {
                SearchImageRes.SearchImageItem searchImageItem = searchImageRes.getItems().stream().findFirst().get();
                dto.setImageLink(searchImageItem.getLink());
            }

            dto.setTitle(searchLocalItem.getTitle());
            dto.setCategory(searchLocalItem.getCategory());
            dto.setAddress(searchLocalItem.getAddress());
            dto.setRoadAddress(searchLocalItem.getRoadAddress());
            dto.setHomePageLink(searchLocalItem.getLink());

            return dto;
        }
        return new RestaurantResponseDto();
    }

    // 북마크 추가
    public BookmarkResponseDto addBookmark(Long userId, RestaurantRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);

        // 음식점 생성
        Restaurant restaurant = Restaurant.builder()
                .title(requestDto.getTitle())
                .category(requestDto.getCategory())
                .address(requestDto.getAddress())
                .roadAddress(requestDto.getRoadAddress())
                .homePageLink(requestDto.getHomePageLink())
                .imageLink(requestDto.getImageLink())
                .build();

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .restaurant(savedRestaurant)
                .build();

        Bookmark savedBookmark = bookmarkRepository.save(bookmark);

        return new BookmarkResponseDto(savedBookmark);
    }

    // 모든 북마크 조회
    public List<BookmarkResponseDto> findAllBookmarks(Long userId) {
        return bookmarkRepository.findByUserId(userId)
                .stream()
                .map(BookmarkResponseDto::new)
                .collect(Collectors.toList());
    }

    // 북마크 삭제
    public void deleteBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }

    // 방문 기록 추가
    public void addVisit(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(IllegalArgumentException::new);

        bookmark.setVisit(true);
        bookmark.setVisitCount(bookmark.getVisitCount() + 1);
        bookmark.setLastVisitDate(LocalDateTime.now());
        bookmarkRepository.save(bookmark);
    }

    // 음식점 목록 조회
    public Page<RestaurantResponseDto> getRestaurants(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("title"));
        Page<Restaurant> restaurants = keyword != null && !keyword.isEmpty()
                ? restaurantRepository.findByTitleContaining(keyword, pageable)
                : restaurantRepository.findAll(pageable);

        return restaurants.map(RestaurantResponseDto::new);
    }
}
