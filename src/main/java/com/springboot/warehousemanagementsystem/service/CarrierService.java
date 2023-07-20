package com.springboot.warehousemanagementsystem.service;

import java.util.List;

import com.springboot.warehousemanagementsystem.model.Carrier;
import org.springframework.stereotype.Service;

@Service
public interface CarrierService {
	public Carrier addCarrier(Carrier carrier);

	public Carrier findById(long id) throws Exception;

	public List<Carrier> getAllCarriers();

	public Carrier updateCarrier(Carrier carrier) throws Exception;

	public void deleteCarrier(long id) throws Exception;
}