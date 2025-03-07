package com.example.foodpick.restaurant.repository;

import com.example.foodpick.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findByTitleContaining(String keyword, PageRequest pageable);
}
