package com.springboot.warehousemanagementsystem.restcontroller;


import com.springboot.warehousemanagementsystem.model.Product;
import com.springboot.warehousemanagementsystem.service.CustomerService;
import com.springboot.warehousemanagementsystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "api/v1", produces = {APPLICATION_JSON_VALUE})
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/product/{id}")
    public ResponseEntity<?> productById(@PathVariable("id") Long id) throws Exception {
        try{
            return ResponseEntity.ok(productService.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id,@RequestBody Product product) throws Exception {
        try{
            product.setId(id);
            return ResponseEntity.ok(productService.updateProduct(product));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/product/stats")
    public ResponseEntity<?> getProductStats() throws Exception {
        return ResponseEntity.ok(productService.getProductStats());
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody  Product product) throws Exception {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @GetMapping("/product")
    public ResponseEntity<?> getProducts() throws Exception {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId) throws Exception {
        try{
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Deleted Successfully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}