package com.example.foodpick.bookmark.entity;

import com.example.foodpick.common.BaseTime;
import com.example.foodpick.restaurant.entity.Restaurant;
import com.example.foodpick.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    private boolean visit = false;  // 방문 여부
    private int visitCount = 0;    // 방문 횟수
    private LocalDateTime lastVisitDate; // 마지막 방문 날짜

}
