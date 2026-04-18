package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StoresRepository extends JpaRepository<Stores, Integer> {
        boolean existsByStoreName(String storeName);
}

