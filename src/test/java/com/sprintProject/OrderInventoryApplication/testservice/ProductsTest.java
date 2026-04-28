package com.sprintProject.orderinventoryapplication.testservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.sprintProject.orderinventoryapplication.customexception.ProductNotFoundException;
import com.sprintProject.orderinventoryapplication.entity.Products;
import com.sprintProject.orderinventoryapplication.repository.ProductsRepository;
import com.sprintProject.orderinventoryapplication.service.ProductsService;
import com.sprintProject.orderinventoryapplication.dto.requestDto.ProductsRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ProductsResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductsTest {

    // Mocking repository layer
    @Mock
    private ProductsRepository productsRepository;

    // Injecting mocks into service
    @InjectMocks
    private ProductsService productsService;

    // Test data objects
    private Products product;
    private ProductsRequestDto requestDto;

    // Initialize test data before each test
    @BeforeEach
    void setUp() {
        product = new Products();
        product.setProductId(1);
        product.setProductName("Shirt");
        product.setUnitPrice(999.0);
        product.setColour("Black");
        product.setBrand("Nike");
        product.setSize("L");
        product.setRating(4);

        requestDto = new ProductsRequestDto();
        requestDto.setProductName("T-Shirt");
        requestDto.setUnitPrice(799.0);
        requestDto.setColour("Blue");
        requestDto.setBrand("Puma");
        requestDto.setSize("M");
        requestDto.setRating(5);
    }

    // Test fetching all products when data exists
    @Test
    void testGetAllProducts() {
        when(productsRepository.findAll()).thenReturn(Arrays.asList(product));

        assertEquals(1, productsService.getAllProducts().size());
    }

    // Test fetching all products when no data exists
    @Test
    void testGetAllProductsEmpty() {
        when(productsRepository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(0, productsService.getAllProducts().size());
    }

    // Test fetching product by ID successfully
    @Test
    void testGetProductById() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        ProductsResponseDto dto = productsService.getProductById(1);

        assertEquals("Shirt", dto.getProductName());
        assertEquals(4, dto.getRating());
    }

    // Test fetching product by ID when not found
    @Test
    void testGetProductByIdNotFound() {
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productsService.getProductById(1));
    }

    // Test creating a new product
    @Test
    void testCreateProduct() {
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        ProductsResponseDto dto = productsService.createProduct(requestDto);

        assertNotNull(dto);
        assertEquals("Shirt", dto.getProductName());
    }

    // Verify repository save method is called during creation
    @Test
    void testCreateProductVerifySave() {
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        productsService.createProduct(requestDto);

        verify(productsRepository, times(1)).save(any(Products.class));
    }

    // Test updating a product with all fields
    @Test
    void testUpdateProduct() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        ProductsResponseDto dto = productsService.updateProduct(1, requestDto);

        assertNotNull(dto);
        assertEquals("T-Shirt", dto.getProductName());
        assertEquals(799.0, dto.getUnitPrice());
        assertEquals(5, dto.getRating());
    }

    // Test updating a product that does not exist
    @Test
    void testUpdateProductNotFound() {
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productsService.updateProduct(1, requestDto));
    }

    // Test partial update: only price should change, name should remain same
    @Test
    void testUpdateOnlyPrice() {
        requestDto.setProductName(null);

        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        ProductsResponseDto dto = productsService.updateProduct(1, requestDto);

        assertEquals(799.0, dto.getUnitPrice());
        assertEquals("Shirt", dto.getProductName());
    }

    // Test deleting product successfully
    @Test
    void testDeleteProduct() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        String msg = productsService.deleteProduct(1);

        assertEquals("Product deleted successfully with id: 1", msg);
    }

    // Test deleting product when not found
    @Test
    void testDeleteProductNotFound() {
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productsService.deleteProduct(1));
    }

    // Verify delete method is called
    @Test
    void testDeleteProductVerifyDelete() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        productsService.deleteProduct(1);

        verify(productsRepository, times(1)).delete(product);
    }

    // Test DTO mapping fields
    @Test
    void testResponseDtoMapping() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        ProductsResponseDto dto = productsService.getProductById(1);

        assertEquals("Nike", dto.getBrand());
        assertEquals("Black", dto.getColour());
    }

    // Test partial update where some fields remain unchanged
    @Test
    void testPartialUpdateFieldsRemainUnchanged() {
        requestDto.setProductName(null);
        requestDto.setColour(null);

        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        ProductsResponseDto dto = productsService.updateProduct(1, requestDto);

        assertEquals("Shirt", dto.getProductName());
        assertEquals("Black", dto.getColour());
    }

    // Test rating mapping correctness
    @Test
    void testGetProductRatingMapping() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        ProductsResponseDto dto = productsService.getProductById(1);

        assertEquals(4, dto.getRating());
    }
}