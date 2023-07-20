package com.springboot.warehousemanagementsystem.restcontroller;

import com.springboot.warehousemanagementsystem.model.Carrier;
import com.springboot.warehousemanagementsystem.service.CarrierService;
import com.springboot.warehousemanagementsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/v1", produces = {APPLICATION_JSON_VALUE})
public class CarrierController {

    @Autowired
    CarrierService carrierService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/carrier/{id}")
    public ResponseEntity<?> carrierById(@PathVariable("id") Long id) throws Exception {
        try{
            return ResponseEntity.ok(carrierService.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/carrier/{id}")
    public ResponseEntity<?> updateCarrier(@PathVariable("id") long id,@RequestBody Carrier carrier) throws Exception {
        try{
            carrier.setId(id);
            return ResponseEntity.ok(carrierService.updateCarrier(carrier));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/carrier")
    public ResponseEntity<?> addCarrier(@RequestBody  Carrier carrier) throws Exception {
        return ResponseEntity.ok(carrierService.addCarrier(carrier));
    }

    @GetMapping("/carrier")
    public ResponseEntity<?> getCarriers() throws Exception {
        return ResponseEntity.ok(carrierService.getAllCarriers());
    }

    @DeleteMapping("/carrier/{id}")
    public ResponseEntity<?> deleteCarrier(@PathVariable("id") Long carrierId) throws Exception {
        try{
            carrierService.deleteCarrier(carrierId);
            return ResponseEntity.ok("Deleted Successfully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}