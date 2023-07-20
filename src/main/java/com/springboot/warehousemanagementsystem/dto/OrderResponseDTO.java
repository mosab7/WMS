package com.springboot.warehousemanagementsystem.dto;

import com.springboot.warehousemanagementsystem.model.Order;
import com.springboot.warehousemanagementsystem.model.OrderStatus;
import com.springboot.warehousemanagementsystem.model.OrderType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderResponseDTO {
    long id;

    List<OrderDTO> orderDTOList;
    Long customerId;

    String customerName;
    Double total;

    String carrier;

    String supplier;
    String status;

    LocalDateTime dateCreated;

    LocalDateTime dateUpdated;

    OrderType orderType;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderDTO> getOrderDTOList() {
        return orderDTOList;
    }

    public void setOrderDTOList(List<OrderDTO> orderDTOList) {
        this.orderDTOList = orderDTOList;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public static OrderResponseDTO fromOrderDTO(Order order){
        OrderResponseDTO orderResponseDTO= new OrderResponseDTO();
        if(order.getCustomer() != null) {
            orderResponseDTO.setCustomerId(order.getCustomer().getId());
        }
        orderResponseDTO.setDateCreated(order.getOrderDate());
        orderResponseDTO.setTotal(order.getTotal());
        orderResponseDTO.setDateUpdated(order.getOrderUpdated());
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setStatus(order.getOrderStatus().getValue());
        if(order.getCarrier() != null) {
            orderResponseDTO.setSupplier("Not Applicable");
            orderResponseDTO.setCarrier(order.getCarrier().getName());
        }
        if(order.getCarrier() == null && order.getOrderType().equals(OrderType.OUTBOUND)) {
            orderResponseDTO.setCarrier("Pending Assignment");
            orderResponseDTO.setSupplier("Not Applicable");
        }

        if (order.getOrderType().equals(OrderType.INBOUND)) {
            if(order.getSupplier() != null){
                orderResponseDTO.setSupplier(order.getSupplier().getName());
            }

            orderResponseDTO.setCarrier("Not Applicable");
            orderResponseDTO.setCustomerName("Not Applicable");
        }
        if (order.getOrderType().equals(OrderType.OUTBOUND)) {
            orderResponseDTO.setCustomerName(order.getCustomer().getName());
        }
        List<OrderDTO> orderDTOS= new ArrayList<>();
        for(int i=0;i<order.getCart().size();i++) {
            OrderDTO orderDTO= new OrderDTO();
            orderDTO.setProductName(order.getCart().get(i).getProduct().getName());
            orderDTO.setQty(order.getCart().get(i).getQty());
            orderDTO.setProductId(order.getCart().get(i).getProduct().getId());
            orderDTOS.add(orderDTO);
        }
        orderResponseDTO.setOrderType(order.getOrderType());
        orderResponseDTO.setOrderDTOList(orderDTOS);

        return orderResponseDTO;

    }
}
