package com.springboot.warehousemanagementsystem.service;

import java.util.List;
import java.util.Optional;
import com.springboot.warehousemanagementsystem.dao.ProductDAO;

import com.springboot.warehousemanagementsystem.dto.ProductChartDTO;
import com.springboot.warehousemanagementsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;


	@Override
	public Product addProduct(Product product) {
		product.setDeleted(false);
		return productDAO.save(product);
	}

	@Override
	public Product findById(long id) throws Exception {
		Optional<Product> product1= productDAO.findById(id);
		if(product1.isPresent()) {
			return product1.get();
		}

		throw new Exception("Product not found");
	}

	@Override
	public List<ProductChartDTO> getProductStats() {
		return productDAO.getProductStats();
	}

	@Override
	public List<Product> getAllProducts() {
		return productDAO.findAll();
	}

	@Override
	public Product updateProduct(Product product) throws Exception {
		Optional<Product> product1= productDAO.findById(product.getId());
		if(product1.isPresent()) {
			return productDAO.save(product);
		}

		throw new Exception("Product not found");
	}

	@Override
	public void deleteProduct(long id) throws Exception {
		Optional<Product> product1= productDAO.findById(id);
		if(product1.isPresent()) {
			 product1.get().setDeleted(true);
			 productDAO.save(product1.get());
			 return;
		}
		throw new Exception("Product not found");
	}
}