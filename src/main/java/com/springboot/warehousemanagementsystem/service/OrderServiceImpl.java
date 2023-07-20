package com.springboot.warehousemanagementsystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.springboot.warehousemanagementsystem.dao.CartDAO;
import com.springboot.warehousemanagementsystem.dao.OrderDAO;
import com.springboot.warehousemanagementsystem.dao.ProductDAO;
import com.springboot.warehousemanagementsystem.dto.OrderChartDTO;
import com.springboot.warehousemanagementsystem.dto.OrderListDto;
import com.springboot.warehousemanagementsystem.dto.OrderResponseDTO;
import com.springboot.warehousemanagementsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	CustomerService customerService;

	@Autowired
	CarrierService carrierService;

	@Autowired
	ProductService productService;

	@Autowired
	SupplierService supplierService;

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private CartDAO cartDAO;

	@Autowired
	private ProductDAO productDAO;


	@Override
	public List<OrderResponseDTO> getAllOrders() {
		List<OrderResponseDTO> orders = orderDAO.findAll().stream().map(s->OrderResponseDTO.fromOrderDTO(s)).collect(Collectors.toList());
		return orders;
	}

	@Override
	public void deleteOrder(long id) throws Exception {
		Optional<Order> orderOptional= orderDAO.findById(id);

		if(orderOptional.isPresent()) {
			for(int i=0;i<orderOptional.get().getCart().size();i++) {
				Product product= productService.findById(orderOptional.get().getCart().get(i).getProduct().getId());
				product.setQty(orderOptional.get().getCart().get(i).getQty() + product.getQty());
				productService.updateProduct(product);
			}
			orderDAO.deleteById(id);
			return;
		}

		throw new Exception("Order not found");

	}

	@Override
	public void assignCarrier(long id, long carrierId) throws Exception {
		Carrier carrier= carrierService.findById(carrierId);
		Optional<Order> order= orderDAO.findById(id);
		if(!order.isPresent()){
			throw new Exception("Order not found");
		}

		if(order.get().getOrderStatus().getValue().equals(OrderStatus.SHIPPED.getValue())) {
			throw new Exception("Order already shipped");
		}

		order.get().setCarrier(carrier);
		order.get().setOrderStatus(OrderStatus.SHIPPED);
		orderDAO.save(order.get());
	}

	@Override
	public OrderResponseDTO addOrder(OrderListDto orderDTO) throws Exception {
		Order order = new Order();
		Customer customer= customerService.findById(orderDTO.getCustomerId());

		order.setCustomer(customer);
		List<Cart> carts= new ArrayList<>();
		List<Product> products= new ArrayList<>();
		Double total= 0.0;
		order.setOrderStatus(OrderStatus.CONFIRMED);
		for(int i=0;i<orderDTO.getOrderDTOList().size();i++) {
			Product product= productService.findById(orderDTO.getOrderDTOList().get(i).getProductId());
			if(product.getQty() - orderDTO.getOrderDTOList().get(i).getQty() < 0) {
				order.setOrderStatus(OrderStatus.NOT_ENOUGH_PRODUCT);
			}
			product.setQty(product.getQty() - orderDTO.getOrderDTOList().get(i).getQty());
			Cart cart= new Cart();
			cart.setQty(orderDTO.getOrderDTOList().get(i).getQty());
			cart.setProduct(product);
			carts.add(cart);
			total+= (product.getPrice() * orderDTO.getOrderDTOList().get(i).getQty());
			products.add(product);
		}
		 order.setCart(carts);
		 order.setTotal(total);
		 order.setOrderDate(LocalDateTime.now());
		 order.setOrderUpdated(LocalDateTime.now());

		 order.setOrderType(OrderType.OUTBOUND);
		 Order savedOrder = orderDAO.save(order);
		 productDAO.saveAll(products);
		 for(int i=0;i<carts.size();i++) {
			 carts.get(i).setOrder(savedOrder);
		 }
		 cartDAO.saveAll(carts);
		 return  OrderResponseDTO.fromOrderDTO(savedOrder);
	}

	@Override
	public List<OrderChartDTO> getOrderStats() {
		return orderDAO.getOrderStats();
	}

	@Override
	public OrderResponseDTO addInboundOrder(OrderListDto orderDTO) throws Exception {
		Order order = new Order();
		order.setCustomer(null);
		List<Cart> carts= new ArrayList<>();
		List<Product> products= new ArrayList<>();
		Double total= 0.0;
		for(int i=0;i<orderDTO.getOrderDTOList().size();i++) {
			Product product= productService.findById(orderDTO.getOrderDTOList().get(i).getProductId());
			product.setQty(product.getQty() + orderDTO.getOrderDTOList().get(i).getQty());
			Cart cart= new Cart();
			cart.setQty(orderDTO.getOrderDTOList().get(i).getQty());
			cart.setProduct(product);
			carts.add(cart);
			total+= (product.getPrice() * orderDTO.getOrderDTOList().get(i).getQty());
			products.add(product);
		}
		Supplier supplier= supplierService.findById(orderDTO.getSupplierId());
		order.setSupplier(supplier);
		order.setCart(carts);
		order.setTotal(total);
		order.setOrderDate(LocalDateTime.now());
		order.setOrderUpdated(LocalDateTime.now());
		order.setOrderStatus(OrderStatus.NOT_APPLICABLE);
		order.setOrderType(OrderType.INBOUND);
		Order savedOrder = orderDAO.save(order);
		productDAO.saveAll(products);
		for(int i=0;i<carts.size();i++) {
			carts.get(i).setOrder(savedOrder);
		}
		cartDAO.saveAll(carts);
		try{
			updateOrdersStatus();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

		return  OrderResponseDTO.fromOrderDTO(savedOrder);
	}

	@Override
	public void updateOrdersStatus() throws Exception {
			List<Order> orders = orderDAO.findAll().stream().filter(o-> o.getOrderStatus().equals(OrderStatus.NOT_ENOUGH_PRODUCT)).collect(Collectors.toList());

			for(int i=0;i<orders.size();i++) {
				for(int j=0;j<orders.get(i).getCart().size();j++) {
					Product product = productService.findById(orders.get(i).getCart().get(j).getProduct().getId());
					if(product.getQty() + orders.get(i).getCart().get(j).getQty() >= 0){
						orders.get(i).setOrderStatus(OrderStatus.CONFIRMED);
					}
					else {
						orders.get(i).setOrderStatus(OrderStatus.NOT_ENOUGH_PRODUCT);
					}
				}
			}
			orderDAO.saveAll(orders);
	}

	@Override
	public OrderResponseDTO findById(long id) throws Exception {
		Optional<Order> orderOptional= orderDAO.findById(id);

		if(orderOptional.isPresent()) {
			return OrderResponseDTO.fromOrderDTO(orderOptional.get());
		}

		throw new Exception("Order not found");
	}
}