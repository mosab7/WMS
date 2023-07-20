package com.springboot.warehousemanagementsystem.service;

import com.springboot.warehousemanagementsystem.model.Customer;

import java.util.List;

public interface CustomerService {

    public Customer addCustomer(Customer customer);

    public Customer findById(long id) throws Exception;

    public List<Customer> getAllCustomers();

    public Customer updateCustomer(Customer customer) throws Exception;

    public void deleteCustomer(long id) throws Exception;
}
