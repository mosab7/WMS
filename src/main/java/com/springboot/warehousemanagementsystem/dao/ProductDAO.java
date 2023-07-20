package com.springboot.warehousemanagementsystem.dao;

import com.springboot.warehousemanagementsystem.dto.OrderChartDTO;
import com.springboot.warehousemanagementsystem.dto.ProductChartDTO;
import com.springboot.warehousemanagementsystem.model.Carrier;
import com.springboot.warehousemanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDAO extends JpaRepository<Product,Long > {
    Optional<Product> findByName(String name);

    @Query(value = "select * from product where id= ? and is_deleted = false",nativeQuery = true)
    Optional<Product> findById(long id);

    @Query(value = "select * from product where is_deleted = false",nativeQuery = true)
    List<Product> findAll();

    @Query(value = "select  p.qty as productCount ,p.name as name from product p where p.is_deleted = false",nativeQuery = true)
    List<ProductChartDTO> getProductStats();
}
