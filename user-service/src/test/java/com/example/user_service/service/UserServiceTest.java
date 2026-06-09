package com.example.user_service.service;

import com.example.user_service.client.SellerClient;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private NotificationService notificationService;

    @Mock
    private SellerClient sellerClient;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@test.com");
        mockUser.setPassword("Valid1@Pass");
        mockUser.setRole("CUSTOMER");
        mockUser.setName("Test User");
    }

    @Test
    void register_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User result = userService.register(mockUser);

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
        verify(userRepository).save(any(User.class));
        verify(notificationService).sendNotification(Mockito.eq(1L), Mockito.eq("test@test.com"), anyString(), anyString());
    }

    @Test
    void login_Success() {
        mockUser.setPassword("encodedPassword");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("Valid1@Pass", "encodedPassword")).thenReturn(true);

        User result = userService.login("test@test.com", "Valid1@Pass");

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
    }
}
