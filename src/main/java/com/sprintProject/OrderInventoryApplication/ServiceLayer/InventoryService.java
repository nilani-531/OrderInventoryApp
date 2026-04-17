package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.InventoryNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.ProductNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.StoreNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.InventoryRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ProductsRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.StoresRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.InventoryResponseDto;

@Service
public class InventoryService implements InventoryServiceInterface {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private StoresRepository storesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    private InventoryResponseDto mapToResponseDto(Inventory inventory) {

        InventoryResponseDto dto = new InventoryResponseDto();
        dto.setInventoryId(inventory.getInventoryId());
        dto.setProductInventory(inventory.getProductInventory());

        return dto;
    }

    @Override
    public List<InventoryResponseDto> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public InventoryResponseDto getInventoryById(int inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new InventoryNotFoundException(
                                "Inventory not found with id : " + inventoryId));

        return mapToResponseDto(inventory);
    }

    @Override
    public InventoryResponseDto createInventory(int storeId, int productId,
            InventoryRequestDto inventoryRequestDto) {

        Stores store = storesRepository.findById(storeId)
                .orElseThrow(() ->
                        new StoreNotFoundException(
                                "Store not found with id : " + storeId));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id : " + productId));

        Inventory inventory = new Inventory();
        inventory.setStores(store);
        inventory.setProducts(product);
        inventory.setProductInventory(
                inventoryRequestDto.getProductInventory());

        Inventory savedInventory = inventoryRepository.save(inventory);

        return mapToResponseDto(savedInventory);
    }

    @Override
    public InventoryResponseDto updateInventory(int inventoryId, int storeId,
            int productId, InventoryRequestDto inventoryRequestDto) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new InventoryNotFoundException(
                                "Inventory not found with id : " + inventoryId));

        Stores store = storesRepository.findById(storeId)
                .orElseThrow(() ->
                        new StoreNotFoundException(
                                "Store not found with id : " + storeId));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id : " + productId));

        inventory.setStores(store);
        inventory.setProducts(product);
        inventory.setProductInventory(
                inventoryRequestDto.getProductInventory());

        Inventory updatedInventory = inventoryRepository.save(inventory);

        return mapToResponseDto(updatedInventory);
    }

    @Override
    public String deleteInventory(int inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new InventoryNotFoundException(
                                "Inventory not found with id : " + inventoryId));

        inventoryRepository.delete(inventory);

        return "Inventory deleted successfully with id : " + inventoryId;
    }
}