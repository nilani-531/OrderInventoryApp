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

import com.sprintProject.OrderInventoryApplication.dto.ResponseStructure;
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
	public ResponseStructure<List<InventoryResponseDto>> getAllInventory() {
		List<InventoryResponseDto> list = inventoryRepository.findAll().stream().map(this::mapToResponseDto).toList();
		ResponseStructure<List<InventoryResponseDto>> response = new ResponseStructure<>();
		response.setStatus(200);
		response.setMsg("Inventory fetched successfully");
		response.setData(list);
		return response;
	}

	@Override
	public ResponseStructure<InventoryResponseDto> getInventoryById(int inventoryId) {
		Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + inventoryId));
		ResponseStructure<InventoryResponseDto> response = new ResponseStructure<>();
		response.setStatus(200);
		response.setMsg("Inventory fetched successfully");
		response.setData(mapToResponseDto(inventory));
		return response;
	}

	@Override
	public ResponseStructure<InventoryResponseDto> createInventory(InventoryRequestDto inventory) {
		Stores stores = storesRepository.findById(inventory.getStores().getStoreId())
		        .orElseThrow(() -> new StoreNotFoundException("Store not found with id: " + inventory.getStores().getStoreId()));

		Products products = productsRepository.findById(inventory.getProducts().getProductId())
		        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + inventory.getProducts().getProductId()));

		Inventory newInventory = new Inventory();
		newInventory.setStores(stores);
		newInventory.setProducts(products);
		newInventory.setProductInventory(inventory.getProductInventory());

	    Inventory saved = inventoryRepository.save(newInventory);

		ResponseStructure<InventoryResponseDto> response = new ResponseStructure<>();
		response.setStatus(201);
		response.setMsg("Inventory created successfully");
		response.setData(mapToResponseDto(saved));
		return response;
	}

	@Override
	public ResponseStructure<InventoryResponseDto> updateInventory(int inventoryId, InventoryRequestDto inventory) {
		Inventory existing = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + inventoryId));

        existing.setStores(inventory.getStores());
        existing.setProducts(inventory.getProducts());
        existing.setProductInventory(inventory.getProductInventory());

        Inventory saved = inventoryRepository.save(existing);
		ResponseStructure<InventoryResponseDto> response = new ResponseStructure<>();
		response.setStatus(200);
		response.setMsg("Inventory updated successfully");
		response.setData(mapToResponseDto(saved));
		return response;
	}

	@Override
	public ResponseStructure<String> deleteInventory(int inventoryId) {
		Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + inventoryId));

        inventoryRepository.delete(inventory);

		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatus(200);
		response.setMsg("Inventory deleted successfully");
		response.setData("Inventory deleted successfully with id: " + inventoryId);
		return response;
	}


	

}
