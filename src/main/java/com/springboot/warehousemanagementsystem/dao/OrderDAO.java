package com.springboot.warehousemanagementsystem.dao;

import com.springboot.warehousemanagementsystem.dto.OrderChartDTO;
import com.springboot.warehousemanagementsystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order,Long>{

    @Query(value = "select DATE(o.order_date) as orderDate ,count(o.id) as orderCount from orders o group by DATE(o.order_date)",nativeQuery = true)
    List<OrderChartDTO> getOrderStats();

}

