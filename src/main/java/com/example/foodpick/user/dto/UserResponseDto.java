package com.example.foodpick.user.dto;

import com.example.foodpick.user.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String username;
    private String email;


    // 엔티티 -> DTO 변환 메서드
    public UserResponseDto(User user) {
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
    }
}
