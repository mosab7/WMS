package com.springboot.warehousemanagementsystem.dao;

import com.springboot.warehousemanagementsystem.model.Carrier;
import com.springboot.warehousemanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CarrierDAO extends JpaRepository<Carrier,Long> {

    @Query(value = "select * from carrier where id= ? and is_deleted = false",nativeQuery = true)
    Optional<Carrier> findById(long id);

    @Query(value = "select * from carrier where is_deleted = false",nativeQuery = true)
    List<Carrier> findAll();
}
