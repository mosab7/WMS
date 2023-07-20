package com.springboot.warehousemanagementsystem.restcontroller;

import com.springboot.warehousemanagementsystem.dto.OrderListDto;
import com.springboot.warehousemanagementsystem.model.OrderType;
import com.springboot.warehousemanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/v1", produces = {APPLICATION_JSON_VALUE})
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/order/{id}")
    public ResponseEntity<?> ordertById(@PathVariable("id") Long id) throws Exception {
        try{
            return ResponseEntity.ok(orderService.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/order")
    public ResponseEntity<?> addOrder(@RequestBody OrderListDto orderListDto,@RequestParam("type") OrderType orderType) throws Exception {
        switch(orderType) {
            case INBOUND:
                    return ResponseEntity.ok(orderService.addInboundOrder(orderListDto));
            case OUTBOUND:
                    return  ResponseEntity.ok(orderService.addOrder(orderListDto));
            default: return ResponseEntity.ok(orderService.addInboundOrder(orderListDto));
        }
    }


    @PutMapping("/order/{id}/{carrierId}")
    public ResponseEntity<?> assignOrderCarrier(@PathVariable("id") Long id,@PathVariable("carrierId") Long carrierId) throws Exception {
        try{
            orderService.assignCarrier(id, carrierId);
            return ResponseEntity.ok("Assigned Successfully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrders() throws Exception {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/order/stats")
    public ResponseEntity<?> getOrdersStats() throws Exception {
        return ResponseEntity.ok(orderService.getOrderStats());
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long productId) throws Exception {
        try{
            orderService.deleteOrder(productId);
            return ResponseEntity.ok("Deleted Successfully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}