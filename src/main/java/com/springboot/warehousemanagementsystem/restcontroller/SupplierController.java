package com.springboot.warehousemanagementsystem.restcontroller;

import com.springboot.warehousemanagementsystem.model.Supplier;
import com.springboot.warehousemanagementsystem.service.CustomerService;
import com.springboot.warehousemanagementsystem.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/v1", produces = {APPLICATION_JSON_VALUE})
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/supplier/{id}")
    public ResponseEntity<?> supplierById(@PathVariable("id") Long id) throws Exception {
        try{
            return ResponseEntity.ok(supplierService.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/supplier/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable("id") long id,@RequestBody Supplier supplier) throws Exception {
        try{
            supplier.setId(id);
            return ResponseEntity.ok(supplierService.updateSupplier(supplier));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/supplier")
    public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier) throws Exception {
        return ResponseEntity.ok(supplierService.addSupplier(supplier));
    }

    @GetMapping("/supplier")
    public ResponseEntity<?> getSuppliers() throws Exception {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @DeleteMapping("/supplier/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable("id") Long carrierId) throws Exception {
        try{
            supplierService.deleteSupplier(carrierId);
            return ResponseEntity.ok("Deleted Successfully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}