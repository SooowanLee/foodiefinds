package com.example.foodpick.user.entity;

import com.example.foodpick.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    public void setUserName(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}