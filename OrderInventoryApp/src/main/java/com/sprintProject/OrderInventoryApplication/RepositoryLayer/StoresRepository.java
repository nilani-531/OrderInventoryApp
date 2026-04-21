package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

public interface StoresRepository extends JpaRepository<Stores, Integer> {

        boolean existsByStoreName(String storeName);

        // Custom query to fetch a store based on its name
        @Query("SELECT s FROM Stores s WHERE s.storeName = ?1")
        Stores findStoreByName(String storeName);
}