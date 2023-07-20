package com.springboot.warehousemanagementsystem.service;

import com.springboot.warehousemanagementsystem.dao.SupplierDAO;
import com.springboot.warehousemanagementsystem.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {


    @Autowired
    private SupplierDAO supplierDAO;


    @Override
    public Supplier addSupplier(Supplier supplier) {
        supplier.setDeleted(false);
        return supplierDAO.save(supplier);
    }

    @Override
    public Supplier findById(long id) throws Exception {
        Optional<Supplier> supplier1= supplierDAO.findById(id);
        if(supplier1.isPresent()) {
            return supplier1.get();
        }

        throw new Exception("Supplier not found");
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierDAO.findAll();
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) throws Exception {
        Optional<Supplier> supplier1= supplierDAO.findById(supplier.getId());
        if(supplier1.isPresent()) {
            return supplierDAO.save(supplier);
        }

        throw new Exception("Supplier not found");
    }

    @Override
    public void deleteSupplier(long id) throws Exception {
        Optional<Supplier> supplier= supplierDAO.findById(id);
        if(supplier.isPresent()) {
            supplier.get().setDeleted(true);
            supplierDAO.save(supplier.get());
            return;
        }
        throw new Exception("Supplier not found");
    }
}
