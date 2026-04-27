package com.sprintProject.orderinventoryapplication.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    private Products product;
    private ProductsRequestDto requestDto;

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

    // 1 Positive
    @Test
    void testGetAllProducts() {
        when(productsRepository.findAll()).thenReturn(Arrays.asList(product));

        assertEquals(1, productsService.getAllProducts().size());
    }

    // 2 Positive
    @Test
    void testGetAllProductsEmpty() {
        when(productsRepository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(0, productsService.getAllProducts().size());
    }

    // 3 Positive
    @Test
    void testGetProductById() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        ProductsResponseDto dto = productsService.getProductById(1);

        assertEquals("Shirt", dto.getProductName());
    }

    // 4 Negative
    @Test
    void testGetProductByIdNotFound() {
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productsService.getProductById(1));
    }

    // 5 Positive
    @Test
    void testCreateProduct() {
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        ProductsResponseDto dto = productsService.createProduct(requestDto);

        assertNotNull(dto);
    }

    // 6 Positive
    @Test
    void testCreateProductVerifySave() {
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        productsService.createProduct(requestDto);

        verify(productsRepository, times(1)).save(any(Products.class));
    }

    // 7 Positive
    @Test
    void testUpdateProduct() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        ProductsResponseDto dto = productsService.updateProduct(1, requestDto);

        assertNotNull(dto);
    }

    // 8 Negative
    @Test
    void testUpdateProductNotFound() {
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productsService.updateProduct(1, requestDto));
    }

    // 9 Positive
    @Test
    void testUpdateProductName() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        productsService.updateProduct(1, requestDto);

        verify(productsRepository, times(1)).save(any(Products.class));
    }

    // 10 Positive
    @Test
    void testDeleteProduct() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        String msg = productsService.deleteProduct(1);

        assertEquals("Product deleted successfully with id: 1", msg);
    }

    // 11 Negative
    @Test
    void testDeleteProductNotFound() {
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productsService.deleteProduct(1));
    }

    // 12 Verify delete
    @Test
    void testDeleteProductVerifyDelete() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        productsService.deleteProduct(1);

        verify(productsRepository, times(1)).delete(product);
    }

    // 13 Positive
    @Test
    void testResponseDtoMapping() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        ProductsResponseDto dto = productsService.getProductById(1);

        assertEquals("Nike", dto.getBrand());
    }

    // 14 Positive
    @Test
    void testUpdateOnlyPrice() {
        requestDto.setProductName(null);

        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(productsRepository.save(any(Products.class))).thenReturn(product);

        ProductsResponseDto dto = productsService.updateProduct(1, requestDto);

        assertNotNull(dto);
    }

    // 15 Positive
    @Test
    void testGetProductRating() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));

        ProductsResponseDto dto = productsService.getProductById(1);

        assertEquals(4.5, dto.getRating());
    }
}

