package com.sprintProject.orderinventoryapplication.repository;

import com.sprintProject.orderinventoryapplication.entity.Stores;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

public interface StoresRepository extends JpaRepository<Stores, Integer> {

        // Check if a store exists by name
        boolean existsByStoreName(String storeName);

        // Custom query to fetch a store based on its name
        @Query("SELECT s FROM Stores s WHERE s.storeName = ?1")
        Stores findStoreByName(String storeName);
}

