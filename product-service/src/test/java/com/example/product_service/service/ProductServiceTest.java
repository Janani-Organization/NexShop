package com.example.product_service.service;

import com.example.product_service.dto.ProductSearchResponseDTO;
import com.example.product_service.models.Products;
import com.example.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private MongoOperations mongoTemplate;

    @Mock
    private EmbeddingService embeddingService;

    @InjectMocks
    private ProductService productService;

    private Products mockProduct;

    @BeforeEach
    void setUp() {
        mockProduct = Products.builder()
                .id("1")
                .name("Laptop")
                .description("Gaming Laptop")
                .price(1000.0)
                .stock(10)
                .build();
    }

    @Test
    void getAllProducts_Success() {
        when(repository.findAll()).thenReturn(List.of(mockProduct));

        List<Products> results = productService.getAllProducts();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Laptop", results.get(0).getName());
    }

    @Test
    void searchProducts_Success() {
        when(mongoTemplate.count(any(Query.class), eq(Products.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Products.class))).thenReturn(List.of(mockProduct));

        ProductSearchResponseDTO response = productService.searchProducts(
                "Laptop", null, null, null, null, null, 0, 10, "price", "asc"
        );

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getProducts().size());
        assertEquals("Laptop", response.getProducts().get(0).getName());
    }
}
