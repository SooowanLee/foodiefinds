package com.example.foodpick.user.controller;

import com.example.foodpick.user.dto.UserRequestDto;
import com.example.foodpick.user.dto.UserResponseDto;
import com.example.foodpick.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    // 사용자 등록
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto user = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // 사용자 목록 조회(페이징)
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<UserResponseDto> users = userService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    // 사용자 조회(ID)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") Long id) {
        UserResponseDto responseDto = userService.findUserById(id);
        return ResponseEntity.ok(responseDto);
    }

    // 사용자 조회(Username)
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUsername(@PathVariable("username") String username) {
        UserResponseDto user = userService.findUserByUserName(username);
        return ResponseEntity.ok(user);
    }

    // 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") Long id, @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.updateUser(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}