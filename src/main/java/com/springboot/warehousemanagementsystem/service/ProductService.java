package com.springboot.warehousemanagementsystem.service;

import java.util.List;

import com.springboot.warehousemanagementsystem.dto.OrderChartDTO;
import com.springboot.warehousemanagementsystem.dto.ProductChartDTO;
import com.springboot.warehousemanagementsystem.model.Product;

public interface ProductService {
	
	public Product addProduct(Product product);

	public Product findById(long id) throws Exception;


	List<ProductChartDTO> getProductStats();
	public List<Product> getAllProducts();

	public Product updateProduct(Product product) throws Exception;

	public void deleteProduct(long id) throws Exception;
}