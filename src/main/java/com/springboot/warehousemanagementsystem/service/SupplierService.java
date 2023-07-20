package com.springboot.warehousemanagementsystem.service;

import com.springboot.warehousemanagementsystem.model.Carrier;
import com.springboot.warehousemanagementsystem.model.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {
    public Supplier addSupplier(Supplier supplier);

    public Supplier findById(long id) throws Exception;

    public List<Supplier> getAllSuppliers();

    public Supplier updateSupplier(Supplier supplier) throws Exception;

    public void deleteSupplier(long id) throws Exception;
}
