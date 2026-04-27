package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.InvalidDataException;
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

	// Maps an Inventory entity to an InventoryResponseDto
	private InventoryResponseDto mapToResponseDto(Inventory inventory) {

		InventoryResponseDto dto = new InventoryResponseDto();
		dto.setInventoryId(inventory.getInventoryId());
		dto.setProductInventory(inventory.getProductInventory());
		if (inventory.getStores() != null) {
			dto.setStoreId(inventory.getStores().getStoreId());
		}
		if (inventory.getProducts() != null) {
			dto.setProductId(inventory.getProducts().getProductId());
		}

		return dto;
	}

	// Retrieves all inventory records
	@Override
	public List<InventoryResponseDto> getAllInventory() {

		return inventoryRepository.findAll().stream().map(this::mapToResponseDto).toList();
	}

	// Retrieves an inventory record by its ID
	@Override
	public InventoryResponseDto getInventoryById(int inventoryId) {

		Inventory inventory = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id : " + inventoryId));

		return mapToResponseDto(inventory);
	}

	// Creates a new inventory record for a specific store and product
	@Override
	public InventoryResponseDto createInventory(int storeId, int productId, InventoryRequestDto inventoryRequestDto) {

		// Validate stock is not negative
		if (inventoryRequestDto.getProductInventory() < 0) {
			throw new InvalidDataException("Stock quantity cannot be negative");
		}

		Stores store = storesRepository.findById(storeId)
				.orElseThrow(() -> new StoreNotFoundException("Store not found with id : " + storeId));

		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + productId));

		// Enforce unique (store_id, product_id) constraint with friendly error
		boolean duplicate = inventoryRepository.findByStoresStoreId(storeId).stream()
				.anyMatch(inv -> inv.getProducts().getProductId() == productId);
		if (duplicate) {
			throw new InvalidDataException(
					"Inventory already exists for storeId=" + storeId + " and productId=" + productId);
		}

		Inventory inventory = new Inventory();
		inventory.setStores(store);
		inventory.setProducts(product);
		inventory.setProductInventory(inventoryRequestDto.getProductInventory());

		return mapToResponseDto(inventoryRepository.save(inventory));
	}

	// Updates an existing inventory record
	@Override
	public InventoryResponseDto updateInventory(int inventoryId, int storeId, int productId,
			InventoryRequestDto inventoryRequestDto) {

		// Validate stock is not negative
		if (inventoryRequestDto.getProductInventory() < 0) {
			throw new InvalidDataException("Stock quantity cannot be negative");
		}

		Inventory inventory = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id : " + inventoryId));

		Stores store = storesRepository.findById(storeId)
				.orElseThrow(() -> new StoreNotFoundException("Store not found with id : " + storeId));

		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + productId));

		inventory.setStores(store);
		inventory.setProducts(product);
		inventory.setProductInventory(inventoryRequestDto.getProductInventory());

		return mapToResponseDto(inventoryRepository.save(inventory));
	}

	// Deletes an inventory record by its ID
	@Override
	public String deleteInventory(int inventoryId) {

		Inventory inventory = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id : " + inventoryId));

		inventoryRepository.delete(inventory);

		return "Inventory deleted successfully with id : " + inventoryId;
	}



	public List<InventoryResponseDto> getInventoryByStore(int storeId) {
		storesRepository.findById(storeId)
				.orElseThrow(() -> new StoreNotFoundException("Store not found with id : " + storeId));

		return inventoryRepository.findByStoresStoreId(storeId).stream().map(this::mapToResponseDto).toList();
	}

	public List<InventoryResponseDto> getInventoryByProduct(int productId) {
		productsRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + productId));

		return inventoryRepository.findByProductsProductId(productId).stream().map(this::mapToResponseDto).toList();
	}
}
