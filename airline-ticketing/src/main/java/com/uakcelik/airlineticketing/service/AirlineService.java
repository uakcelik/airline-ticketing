package com.uakcelik.airlineticketing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uakcelik.airlineticketing.db.entity.Airline;
import com.uakcelik.airlineticketing.db.repository.AirlineRepository;
import com.uakcelik.airlineticketing.helper.DateUtil;
import com.uakcelik.airlineticketing.object.AirlineObject;

@Service
public class AirlineService {

	@Autowired
	private AirlineRepository airlineRepository;
	
	public List<AirlineObject> searchAirlines(String name, int page, int size) {
		
		List<AirlineObject> listAirlineObject = new ArrayList<>();
		List<Airline> listAirline = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		if (name == null || name.isEmpty() || name == "") {
			listAirline = airlineRepository.findByEnabledOrderById(true, pageable);
		}
		else {
			listAirline = airlineRepository.findByEnabledAndNameIgnoreCaseContainingOrderById(true, name, pageable);
		}
		
		for (Airline airline : listAirline) {
			AirlineObject airlineObject = new AirlineObject();
			airlineObject.setId(airline.getId());
			airlineObject.setName(airline.getName());
			airlineObject.setCreateDate(DateUtil.format(airline.getCreateDate(), DateUtil.DATETIME));
			airlineObject.setUpdateDate(DateUtil.format(airline.getUpdateDate(), DateUtil.DATETIME));
			airlineObject.setEnabled(airline.isEnabled());
			
			listAirlineObject.add(airlineObject);
		}
		
		return listAirlineObject;
	}
	
	public int countAirlines(String search) {
		
		int count = 0;
		
		if (search == null || search.isEmpty() || search == "") {
			count = airlineRepository.countByEnabled(true);
		}
		else {
			count = airlineRepository.countByEnabledAndNameIgnoreCaseContaining(true, search);
		}
		
		return count;
	}
	
	public Airline getAirlineByNameAndEnabled(String name, boolean enabled) {
		Airline airline = airlineRepository.findByNameIgnoreCaseAndEnabled(name, enabled);
		return airline;
	}
	
	public Airline getAirlineById(int id) {
		Airline airline = airlineRepository.findById(id);
		return airline;
	}	
	
	public void saveAirline(Airline airline) {
		airlineRepository.save(airline);
	}

	public Airline getAirlineByIdAndEnabled(int id, boolean enabled) {
		Airline airline = airlineRepository.findByIdAndEnabled(id, enabled);
		return airline;
	}

}
