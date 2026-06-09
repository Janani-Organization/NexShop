package com.example.cart_service.service;

import com.example.cart_service.client.ProductClient;
import com.example.cart_service.dto.CartResponseDTO;
import com.example.cart_service.dto.ProductResponseDTO;
import com.example.cart_service.model.Cart;
import com.example.cart_service.model.CartItem;
import com.example.cart_service.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductClient productServiceClient;

    @InjectMocks
    private CartService cartService;

    private Cart mockCart;
    private ProductResponseDTO mockProduct;

    @BeforeEach
    void setUp() {
        mockCart = Cart.builder()
                .userId(1L)
                .items(new ArrayList<>())
                .totalPrice(0.0)
                .build();

        mockProduct = ProductResponseDTO.builder()
                .id("prod-1")
                .name("Laptop")
                .price(1000.0)
                .stock(10)
                .build();
    }

    @Test
    void addToCart_Success() {
        when(productServiceClient.getProductById(anyString())).thenReturn(mockProduct);
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(mockCart));
        when(cartRepository.save(any(Cart.class))).thenReturn(mockCart);

        CartResponseDTO response = cartService.addToCart(1L, "prod-1", 1);

        assertNotNull(response);
        assertEquals(1, response.getItems().size());
        assertEquals(1000.0, response.getTotalPrice());
    }

    @Test
    void removeItem_Success() {
        CartItem item = CartItem.builder().productId("prod-1").name("Laptop").price(1000.0).quantity(1).build();
        mockCart.getItems().add(item);
        mockCart.setTotalPrice(1000.0);

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(mockCart));
        when(cartRepository.save(any(Cart.class))).thenReturn(mockCart);

        CartResponseDTO response = cartService.removeItem(1L, "prod-1");

        assertNotNull(response);
        assertEquals(0, response.getItems().size());
        assertEquals(0.0, response.getTotalPrice());
    }
}
