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
		dto.setStores(inventory.getStores());
		dto.setProducts(inventory.getProducts());
		dto.setProductInventory(inventory.getProductInventory());
		return dto;
	}

	@Override
	public List<InventoryResponseDto> getAllInventory() {
		return inventoryRepository.findAll().stream().map(this::mapToResponseDto).toList();
	}

	@Override
	public InventoryResponseDto getInventoryById(int inventoryId) {
		Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + inventoryId));
		return mapToResponseDto(inventory);
	}

	@Override
	public InventoryResponseDto createInventory(InventoryRequestDto inventory) {
		Stores stores = storesRepository.findById(inventory.getStores().getStoreId())
		        .orElseThrow(() -> new StoreNotFoundException("Store not found with id: " + inventory.getStores().getStoreId()));

		Products products = productsRepository.findById(inventory.getProducts().getProductId())
		        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + inventory.getProducts().getProductId()));

		Inventory newInventory = new Inventory();
		newInventory.setStores(stores);
		newInventory.setProducts(products);
		newInventory.setProductInventory(inventory.getProductInventory());

	    Inventory saved = inventoryRepository.save(newInventory);
		return mapToResponseDto(saved);
	}

	@Override
	public InventoryResponseDto updateInventory(int inventoryId, InventoryRequestDto inventory) {
		Inventory existing = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + inventoryId));

        existing.setStores(inventory.getStores());
        existing.setProducts(inventory.getProducts());
        existing.setProductInventory(inventory.getProductInventory());

        Inventory saved = inventoryRepository.save(existing);
		return mapToResponseDto(saved);
	}

	@Override
	public String deleteInventory(int inventoryId) {
		Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + inventoryId));

        inventoryRepository.delete(inventory);

		return "Inventory deleted successfully with id: " + inventoryId;
	}


	

}
