package com.sprintProject.orderinventoryapplication.repository;

import com.sprintProject.orderinventoryapplication.entity.Stores;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoresRepository extends JpaRepository<Stores, Integer> {

        // Check if a store exists by name
        @Query("SELECT COUNT(s) > 0 FROM Stores s WHERE LOWER(REPLACE(s.storeName, ' ', '')) = :storeName")
        boolean existsStoreByName(@Param("storeName") String storeName);

        // Custom query to fetch a store based on its name
        @Query("SELECT s FROM Stores s WHERE LOWER(REPLACE(s.storeName, ' ', '')) = :storeName")
        Stores findStoreByName(@Param("storeName") String storeName);
}