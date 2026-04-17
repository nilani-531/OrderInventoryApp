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

import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.InventoryResponseDto;

@Service
public class InventoryService implements InventoryServiceInterface {

    @Autowired
    private InventoryRepository inventoryRepository;

<<<<<<< HEAD
    @Autowired
    private StoresRepository storesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    private InventoryResponseDto mapToResponseDto(Inventory inventory) {
=======
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
>>>>>>> master

        InventoryResponseDto dto = new InventoryResponseDto();
        dto.setInventoryId(inventory.getInventoryId());
        dto.setProductInventory(inventory.getProductInventory());

<<<<<<< HEAD
        return dto;
    }

    @Override
    public List<InventoryResponseDto> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
=======
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
>>>>>>> master

    @Override
    public InventoryResponseDto getInventoryById(int inventoryId) {

<<<<<<< HEAD
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
=======
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
>>>>>>> master
