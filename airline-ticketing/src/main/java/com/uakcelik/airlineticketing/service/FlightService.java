package com.uakcelik.airlineticketing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uakcelik.airlineticketing.db.entity.Flight;
import com.uakcelik.airlineticketing.db.repository.FlightRepository;
import com.uakcelik.airlineticketing.helper.DateUtil;
import com.uakcelik.airlineticketing.object.FlightObject;

@Service
public class FlightService {

	@Autowired
	private FlightRepository flightRepository;
	
	public List<FlightObject> searchFlights(BigDecimal minPrice, BigDecimal maxPrice, Integer airlineId, Integer routeId, int page, int size) {
		
		List<FlightObject> listFlightObject = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		List<Flight> listFlight = flightRepository.findFlightByPriceAndAirlineAndRouteAndEnabledOrderById(minPrice, maxPrice, airlineId, routeId, true, pageable);
		
		for (Flight flight : listFlight) {
			FlightObject flightObject = new FlightObject();
			flightObject.setId(flight.getId());
			flightObject.setCapacity(flight.getCapacity());
			flightObject.setPrice(flight.getPrice());
			flightObject.setCreateDate(DateUtil.format(flight.getCreateDate(), DateUtil.DATETIME));
			flightObject.setUpdateDate(DateUtil.format(flight.getUpdateDate(), DateUtil.DATETIME));
			flightObject.setAirlineId(flight.getAirline().getId());
			flightObject.setRouteId(flight.getRoute().getId());
			flightObject.setEnabled(flight.isEnabled());
			
			listFlightObject.add(flightObject);
		}
		
		return listFlightObject;
	}
	
	public int countFlights(BigDecimal minPrice, BigDecimal maxPrice, Integer airlineId, Integer routeId) {
		
		int count = flightRepository.countFlightByPriceAndAirlineAndRouteAndEnabled(minPrice, maxPrice, airlineId, routeId, true);
		return count;
	}
	
	public Flight getFlightById(int id) {
		Flight flight = flightRepository.findById(id);
		return flight;
	}
	
	public void saveFlight(Flight flight) {
		flightRepository.save(flight);
	}

	public int countFlightsByAirlineId(int airlineId) {
		int count = flightRepository.countByAirlineIdAndEnabled(airlineId, true);
		return count;
	}
	
	public int countFlightsByRouteId(int routeId) {
		int count = flightRepository.countByRouteIdAndEnabled(routeId, true);
		return count;
	}

	public Flight getFlightByIdAndEnabled(int id, boolean enabled) {
		Flight flight = flightRepository.findByIdAndEnabled(id, enabled);
		return flight;
	}
	
}
