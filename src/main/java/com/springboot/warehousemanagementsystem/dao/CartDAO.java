package com.springboot.warehousemanagementsystem.dao;

import com.springboot.warehousemanagementsystem.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDAO extends JpaRepository<Cart,Long > {

}
