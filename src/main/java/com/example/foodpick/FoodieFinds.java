package com.example.foodpick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FoodieFinds {

    public static void main(String[] args) {
        SpringApplication.run(FoodieFinds.class, args);
    }
}
