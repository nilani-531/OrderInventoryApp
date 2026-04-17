package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.DuplicateResourceException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.InvalidDataException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.ResourceNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.StoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StoresService implements StoresServiceInterface {

    @Autowired
    private StoresRepository storeRepository;

    @Override
    public List<Stores> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Stores getStoreById(int id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + id));
    }

    @Override
    public Stores createStore(Stores store) {

        //  Unique store name
        if (storeRepository.existsByStoreName(store.getStoreName())) {
            throw new DuplicateResourceException("Store name already exists");
        }

        //  Either web OR physical address must exist
        if ((store.getWebAddress() == null || store.getWebAddress().isEmpty()) &&
                (store.getPhysicalAddress() == null || store.getPhysicalAddress().isEmpty())) {
            throw new InvalidDataException("Either web address or physical address must be provided");
        }

        //  Set logo last updated automatically
        store.setLogoLastUpdated(LocalDate.now());

        return storeRepository.save(store);
    }

    @Override
    public Stores updateStore(int id, Stores store) {

        Stores existing = getStoreById(id);

        //  Prevent duplicate name (except same record)
        if (!existing.getStoreName().equals(store.getStoreName()) &&
                storeRepository.existsByStoreName(store.getStoreName())) {
            throw new DuplicateResourceException("Store name already exists");
        }

        // Address validation
        if ((store.getWebAddress() == null || store.getWebAddress().isEmpty()) &&
                (store.getPhysicalAddress() == null || store.getPhysicalAddress().isEmpty())) {
            throw new InvalidDataException("Either web address or physical address must be provided");
        }

        existing.setStoreName(store.getStoreName());
        existing.setWebAddress(store.getWebAddress());
        existing.setPhysicalAddress(store.getPhysicalAddress());
        existing.setLatitude(store.getLatitude());
        existing.setLongitude(store.getLongitude());

        // Update logo fields if present
        if (store.getLogo() != null) {
            existing.setLogo(store.getLogo());
            existing.setLogoMimeType(store.getLogoMimeType());
            existing.setLogoFilename(store.getLogoFilename());
            existing.setLogoCharset(store.getLogoCharset());
            existing.setLogoLastUpdated(LocalDate.now());
        }

        return storeRepository.save(existing);
    }

    @Override
    public void deleteStore(int id) {
        Stores store = getStoreById(id);
        storeRepository.delete(store);
    }
}