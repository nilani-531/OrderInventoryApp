package com.sprintProject.orderinventoryapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.orderinventoryapplication.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer>{

}


