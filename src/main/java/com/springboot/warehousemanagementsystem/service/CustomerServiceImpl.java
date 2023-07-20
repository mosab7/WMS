package com.springboot.warehousemanagementsystem.service;

import com.springboot.warehousemanagementsystem.dao.CustomerDAO;
import com.springboot.warehousemanagementsystem.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;


    @Override
    public Customer addCustomer(Customer customer) {
        customer.setDeleted(false);
        return customerDAO.save(customer);
    }

    @Override
    public Customer findById(long id) throws Exception {
        Optional<Customer> customer1= customerDAO.findById(id);
        if(customer1.isPresent()) {
            return customer1.get();
        }

        throw new Exception("Customer not found");
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer) throws Exception {
        Optional<Customer> customer1= customerDAO.findById(customer.getId());
        if(customer1.isPresent()) {
            return customerDAO.save(customer);
        }

        throw new Exception("Customer not found");
    }

    @Override
    public void deleteCustomer(long id) throws Exception {
        Optional<Customer> customer1= customerDAO.findById(id);
        if(customer1.isPresent()) {
            customer1.get().setDeleted(true);
            customerDAO.save(customer1.get());
            return;
        }
        throw new Exception("Customer not found");
    }
}