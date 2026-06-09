package com.example.order_service.service;

import com.example.order_service.Clients.CartClient;
import com.example.order_service.Clients.UserClient;
import com.example.order_service.dto.CartItemDTO;
import com.example.order_service.dto.CartResponseDTO;
import com.example.order_service.dto.OrderResponseDTO;
import com.example.order_service.models.Order;
import com.example.order_service.models.OrderItem;
import com.example.order_service.models.Products;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CartClient cartClient;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CouponService couponService;

    @Mock
    private PdfInvoiceService pdfInvoiceService;

    @Mock
    private UserClient userClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {}

    @Test
    void placeOrder_Success() {
        CartResponseDTO mockCart = CartResponseDTO.builder()
                .userId(1L)
                .totalPrice(1000.0)
                .items(List.of(CartItemDTO.builder().productId("prod-1").quantity(1).price(1000.0).build()))
                .build();

        Products mockProduct = Products.builder().id("prod-1").stock(10).price(1000.0).name("Laptop").build();

        Order mockOrder = Order.builder().orderId(100L).userId(1L).subtotal(1000.0).totalAmount(1000.0).build();

        when(cartClient.getCart(1L)).thenReturn(mockCart);
        when(productRepository.findById("prod-1")).thenReturn(Optional.of(mockProduct));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
        OrderItem mockOrderItem = OrderItem.builder().productId("prod-1").productName("Laptop").quantity(1).price(1000.0).build();
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(mockOrderItem);

        OrderResponseDTO response = orderService.placeOrder(1L, 2L, null);

        assertNotNull(response);
        assertEquals(100L, response.getOrderId());
        assertEquals(1000.0, response.getTotalAmount());
        verify(cartClient).clearCart(1L);
    }
}
