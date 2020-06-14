package com.uakcelik.airlineticketing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uakcelik.airlineticketing.db.entity.Airport;
import com.uakcelik.airlineticketing.db.repository.AirportRepository;
import com.uakcelik.airlineticketing.db.repository.RouteRepository;
import com.uakcelik.airlineticketing.helper.DateUtil;
import com.uakcelik.airlineticketing.object.AirportObject;

@Service
public class AirportService {
	
	@Autowired
	private AirportRepository airportRepository;
	
	@Autowired
	private RouteRepository routeRepository;

	public List<AirportObject> searchAirports(String name, String country, String city, int page, int size) {
		
		List<AirportObject> listAirportObject = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		List<Airport> listAirport = airportRepository.findAirportByNameAndCityAndCountryAndEnabledOrderById(name, country, city, true, pageable);
		
		for (Airport airport : listAirport) {
			AirportObject airportObject = new AirportObject();
			airportObject.setId(airport.getId());
			airportObject.setName(airport.getName());
			airportObject.setCountry(airport.getCountry());
			airportObject.setCity(airport.getCity());
			airportObject.setCreateDate(DateUtil.format(airport.getCreateDate(), DateUtil.DATETIME));
			airportObject.setUpdateDate(DateUtil.format(airport.getUpdateDate(), DateUtil.DATETIME));
			airportObject.setEnabled(airport.isEnabled());
			
			listAirportObject.add(airportObject);
		}
		
		return listAirportObject;
	}
	
	public int countAirports(String name, String country, String city) {
		
		int count = airportRepository.countAirportByNameAndCityAndCountryAndEnabled(name, country, city, true);
		return count;
	}
	
	public Airport getAirportByNameAndCountryAndCityAndEnabled(String name, String country, String city, boolean enabled) {
		Airport airport = airportRepository.findByNameIgnoreCaseAndCountryIgnoreCaseAndCityIgnoreCaseAndEnabled(name, country, city, enabled);
		return airport;
	}
	
	public Airport getAirportById(int id) {
		Airport airport = airportRepository.findById(id);
		return airport;
	}
	
	public void saveAirport(Airport airport) {
		airportRepository.save(airport);
	}
	
	public Airport getAirportByIdAndEnabled(int id, boolean enabled) {
		Airport airport = airportRepository.findByIdAndEnabled(id, enabled);
		return airport;
	}

}
