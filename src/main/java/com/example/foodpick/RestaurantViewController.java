package com.example.foodpick;

import com.example.foodpick.bookmark.dto.BookmarkResponseDto;
import com.example.foodpick.restaurant.dto.PageResponseDto;
import com.example.foodpick.restaurant.dto.RestaurantRequestDto;
import com.example.foodpick.restaurant.dto.RestaurantResponseDto;
import com.example.foodpick.restaurant.service.RestaurantService;
import com.example.foodpick.user.dto.UserResponseDto;
import com.example.foodpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RestaurantViewController {
    private final RestaurantService restaurantService;
    private final UserRepository userRepository;

    @GetMapping("/restaurants")
    public String showRestaurants(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
        if (query != null && !query.isEmpty()) {
            RestaurantResponseDto searchResult = restaurantService.search(query);
            model.addAttribute("searchResult", searchResult);
        }

        String url = "http://localhost:8080/api/restaurants?page=" + page + "&size=" + size + (query != null ? "&keyword=" + query : "");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PageResponseDto<RestaurantResponseDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<PageResponseDto<RestaurantResponseDto>>() {});
        PageResponseDto<RestaurantResponseDto> pageResponse = response.getBody();
        List<RestaurantResponseDto> restaurants = pageResponse.getContent();
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("page", pageResponse);

        model.addAttribute("currentUser", new UserResponseDto(userRepository.findById(1L).get()));
        return "restaurants";
    }

    @PostMapping("/restaurants/bookmark")
    public String addBookmark(@ModelAttribute RestaurantRequestDto requestDto) {
        Long userId = 1L; // 실제로는 인증된 사용자 ID 사용
        restaurantService.addBookmark(userId, requestDto);
        return "redirect:/restaurants";
    }

    @GetMapping("/bookmarks")
    public String showBookmarks(Model model) {
        Long userId = 1L; // 실제로는 인증된 사용자 ID 사용
        List<BookmarkResponseDto> bookmarks = restaurantService.findAllBookmarks(userId);
        model.addAttribute("bookmarks", bookmarks);
        model.addAttribute("currentUser", new UserResponseDto(userRepository.findById(userId).get()));
        return "bookmarks";
    }

    @PostMapping("/bookmarks/visit")
    public String addVisit(@RequestParam("id") Long id) {
        restaurantService.addVisit(id);
        return "redirect:/bookmarks";
    }
}