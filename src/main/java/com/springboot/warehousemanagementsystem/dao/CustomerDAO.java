package com.springboot.warehousemanagementsystem.dao;

import com.springboot.warehousemanagementsystem.model.Carrier;
import com.springboot.warehousemanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.warehousemanagementsystem.model.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerDAO extends JpaRepository<Customer,Long> {
    @Query(value = "select * from customer where id= ? and is_deleted = false",nativeQuery = true)
    Optional<Customer> findById(long id);

    @Query(value = "select * from customer where is_deleted = false",nativeQuery = true)
    List<Customer> findAll();
}

