package com.springboot.warehousemanagementsystem.dao;

import com.springboot.warehousemanagementsystem.model.Carrier;
import com.springboot.warehousemanagementsystem.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierDAO extends JpaRepository<Supplier,Long> {

    @Query(value = "select * from supplier where id= ? and is_deleted = false",nativeQuery = true)
    Optional<Supplier> findById(long id);

    @Query(value = "select * from supplier where is_deleted = false",nativeQuery = true)
    List<Supplier> findAll();
}
