package com.springboot.warehousemanagementsystem.service;


import java.util.*;

import com.springboot.warehousemanagementsystem.dao.CarrierDAO;
import com.springboot.warehousemanagementsystem.model.Carrier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrierServiceImpl implements CarrierService {


	@Autowired
	private CarrierDAO carrierDAO;


	@Override
	public Carrier addCarrier(Carrier carrier) {
		carrier.setDeleted(false);
		return carrierDAO.save(carrier);
	}

	@Override
	public Carrier findById(long id) throws Exception {
		Optional<Carrier> carrier1= carrierDAO.findById(id);
		if(carrier1.isPresent()) {
			return carrier1.get();
		}

		throw new Exception("Carrier not found");
	}

	@Override
	public List<Carrier> getAllCarriers() {
		return carrierDAO.findAll();
	}

	@Override
	public Carrier updateCarrier(Carrier carrier) throws Exception {
		Optional<Carrier> carrier1= carrierDAO.findById(carrier.getId());
		if(carrier1.isPresent()) {
			return carrierDAO.save(carrier);
		}

		throw new Exception("Carrier not found");
	}

	@Override
	public void deleteCarrier(long id) throws Exception {
		Optional<Carrier> carrier= carrierDAO.findById(id);
		if(carrier.isPresent()) {
			carrier.get().setDeleted(true);
			carrierDAO.save(carrier.get());
			return;
		}
		throw new Exception("Carrier not found");
	}
}
