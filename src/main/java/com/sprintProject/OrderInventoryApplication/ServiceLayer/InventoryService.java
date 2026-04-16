package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.CustomException.InventoryNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomException.ProductNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomException.StoreNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.InventoryRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ProductsRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.StoresRepository;

@Service
public class InventoryService implements InventoryServiceInterface {

	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private StoresRepository storesRepository;
	@Autowired
	private ProductsRepository productsRepository;

	@Override
	public List<Inventory> getAllInventory() {
		return inventoryRepository.findAll();
	}

	@Override
	public Inventory getInventoryById(int inventoryId) {
		return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + inventoryId));
	}

	@Override
	public Inventory createInventory(Inventory inventory) {
		Stores stores = storesRepository.findById(inventory.getStores().getStoreId())
		        .orElseThrow(() -> new StoreNotFoundException("Store not found with id: " + inventory.getStores().getStoreId()));

		Products products = productsRepository.findById(inventory.getProducts().getProductId())
		        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + inventory.getProducts().getProductId()));

		inventory.setStores(stores);
		inventory.setProducts(products);

	        return inventoryRepository.save(inventory);
	}

	@Override
	public Inventory updateInventory(int inventoryId, Inventory inventory) {
		Inventory existing = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + inventoryId));

        existing.setStores(inventory.getStores());
        existing.setProducts(inventory.getProducts());
        existing.setProductInventory(inventory.getProductInventory());

        return inventoryRepository.save(existing);
	}

	@Override
	public void deleteInventory(int inventoryId) {
		Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + inventoryId));

        inventoryRepository.delete(inventory);

	}


	

}
