package com.springboot.warehousemanagementsystem.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long  id;

    @Column(name="orderStatus",length = 100)
    @Enumerated()
    private OrderStatus orderStatus;


    @Column(name="orderType",length = 100)
    @Enumerated()
    private OrderType orderType;


    @Column(name = "orderDate")
    private LocalDateTime orderDate;

    @Column(name =  "orderUpdated")
    private LocalDateTime orderUpdated;


    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Cart> cart;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST,optional = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "order_carrier",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "carrier_id"))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Carrier carrier;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "order_supplier",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Supplier supplier;

    @Column(name = "total")
    private Double total;


    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getOrderUpdated() {
        return orderUpdated;
    }

    public void setOrderUpdated(LocalDateTime orderUpdated) {
        this.orderUpdated = orderUpdated;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
