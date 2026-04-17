package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;

import java.util.List;

public interface StoresServiceInterface {

    List<Stores> getAllStores();

    Stores getStoreById(int id);

    Stores createStore(Stores store);

    Stores updateStore(int id, Stores store);

    void deleteStore(int id);
}