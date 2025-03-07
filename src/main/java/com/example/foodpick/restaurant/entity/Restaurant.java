package com.example.foodpick.restaurant.entity;

import com.example.foodpick.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;           // 음식명, 장소명
    private String category;        // 카테고리

    @Column(nullable = false)
    private String address;         // 주소
    private String roadAddress;     //도로명
    private String homePageLink;    //홈페이지 주소
    private String imageLink;       // 음식, 가게 이미지 주소
}
