package com.springboot.warehousemanagementsystem.restcontroller;

import com.springboot.warehousemanagementsystem.model.Customer;
import com.springboot.warehousemanagementsystem.service.CustomerService;
import com.springboot.warehousemanagementsystem.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/v1", produces = {APPLICATION_JSON_VALUE})
public class CustomerController {

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> customerById(@PathVariable("id") Long id) throws Exception {
        try{
            return ResponseEntity.ok(customerService.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable("id") long id,@RequestBody  Customer customerDTO) throws Exception {
        try{
            customerDTO.setId(id);
            return ResponseEntity.ok(customerService.updateCustomer(customerDTO));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<?> addCustomer(@RequestBody  Customer customer) throws Exception {
        return ResponseEntity.ok(customerService.addCustomer(customer));
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getCustomers() throws Exception {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long customerId) throws Exception {
        try{
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok("Deleted Successfully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}