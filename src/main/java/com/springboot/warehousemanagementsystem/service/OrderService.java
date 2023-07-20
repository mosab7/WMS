package com.springboot.warehousemanagementsystem.service;

import com.springboot.warehousemanagementsystem.dto.OrderChartDTO;
import com.springboot.warehousemanagementsystem.dto.OrderListDto;
import com.springboot.warehousemanagementsystem.dto.OrderResponseDTO;

import java.util.List;


public interface OrderService {

	List<OrderResponseDTO> getAllOrders();

	void deleteOrder(long id) throws Exception;

	void assignCarrier(long id , long carrierId) throws Exception;

	OrderResponseDTO addOrder(OrderListDto orderDTO) throws Exception;

	List<OrderChartDTO> getOrderStats();

	OrderResponseDTO addInboundOrder(OrderListDto orderDTO) throws Exception;

	void updateOrdersStatus() throws Exception;

	OrderResponseDTO findById(long id) throws Exception;

}

