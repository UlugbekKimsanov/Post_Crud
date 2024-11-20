package com.example.post_crud.service;

import com.example.post_crud.dto.AuthResponse;
import com.example.post_crud.dto.CreateRequest;
import com.example.post_crud.dto.LoginRequest;
import com.example.post_crud.entity.UserEntity;
import com.example.post_crud.exception.DataAlreadyExistsException;
import com.example.post_crud.exception.DataNotFoundException;
import com.example.post_crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtTokenService;


    public ResponseEntity<String> signUp(CreateRequest userCr) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userCr.getEmail());
        if (optionalUser.isEmpty()) {
            UserEntity user = new UserEntity();
            user.setPassword(passwordEncoder.encode(userCr.getPassword()));
            user.setEmail(userCr.getEmail());
            user.setName(userCr.getName());
            user.setSurname(userCr.getSurname());
            userRepository.save(user);
            return ResponseEntity.ok("Foydalanuvchi muvaffaqiyatli ro'yxatdan o'tdi!");
        }
        throw new DataAlreadyExistsException("Foydalanuuvchi allaqachon ro'yhatdan o'tgan!");
    }

    public ResponseEntity<AuthResponse> signIn(LoginRequest loginRequest) {
        Optional<UserEntity> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            String accessToken = jwtTokenService.generateAccessToken(user.get().getId());
            String refreshToken = jwtTokenService.generateRefreshToken(user.get().getId());
            return ResponseEntity.ok().body(new AuthResponse(accessToken, refreshToken));
        }
        throw new DataNotFoundException("Parol yoki username noto'g'ri!");
    }

    public ResponseEntity<AuthResponse> refreshToken(String refreshToken) {
        String userId = jwtTokenService.validateTokenAndGetSubject(refreshToken);
        UUID userUuid = UUID.fromString(userId);

        String newAccessToken = jwtTokenService.generateAccessToken(userUuid);
        String newRefreshToken = jwtTokenService.generateRefreshToken(userUuid);

        return ResponseEntity.ok().body(new AuthResponse(newAccessToken, newRefreshToken));
    }
}
