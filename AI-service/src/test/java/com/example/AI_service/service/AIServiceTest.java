package com.example.AI_service.service;

import com.example.AI_service.dto.AIResponseDTO;
import com.example.AI_service.model.Intent;
import com.example.AI_service.model.Products;
import com.example.AI_service.repository.ChatHistoryRepository;
import com.example.AI_service.repository.ProductRepository;
import com.example.AI_service.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AIServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ChatHistoryRepository chatRepository;

    @Mock
    private EmbeddingService embeddingService;

    @Mock
    private IntentService intentService;

    @InjectMocks
    private AIService aiService;

    @Test
    void handleQuery_SearchIntent_Success() {
        Products mockProduct = new Products();
        mockProduct.setId("prod-1");
        mockProduct.setName("Laptop");
        mockProduct.setCategory("Electronics");

        when(intentService.classify(anyString())).thenReturn(Intent.SEARCH);
        when(productRepository.findAll()).thenReturn(List.of(mockProduct));

        AIResponseDTO response = aiService.handleQuery(1L, "find a laptop");

        assertNotNull(response);
        assertEquals("SEARCH", response.getIntent());
        assertFalse(response.getProducts().isEmpty());
        assertEquals("Laptop", response.getProducts().get(0).getName());
    }
}
