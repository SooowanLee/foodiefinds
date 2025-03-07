package com.example.foodpick.user.service;

import com.example.foodpick.user.dto.UserRequestDto;
import com.example.foodpick.user.dto.UserResponseDto;
import com.example.foodpick.user.entity.User;
import com.example.foodpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 사용자 생성
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = new User();
        user.setUserName(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);
    }

    // 사용자 조회 (ID)
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return new UserResponseDto(user);
    }

    // 사용자 조회 (Username)
    public UserResponseDto findUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(IllegalAccessError::new);

        return new UserResponseDto(user);
    }

    // 사용자 업데이트
    public UserResponseDto updateUser(Long id, UserRequestDto updateUser) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (updateUser.getEmail() != null && !updateUser.getEmail().isEmpty()) {
            user.setEmail(updateUser.getEmail());
        }

        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser);
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 모든 사용자 조회(페이징)
    public Page<UserResponseDto> getAllUsers(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return userRepository.findAll(pageable).map(UserResponseDto::new);
    }
}