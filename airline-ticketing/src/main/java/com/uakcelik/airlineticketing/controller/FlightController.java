package com.uakcelik.airlineticketing.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uakcelik.airlineticketing.db.entity.Airline;
import com.uakcelik.airlineticketing.db.entity.Flight;
import com.uakcelik.airlineticketing.db.entity.Route;
import com.uakcelik.airlineticketing.object.FlightObject;
import com.uakcelik.airlineticketing.object.ResponseItem;
import com.uakcelik.airlineticketing.service.AirlineService;
import com.uakcelik.airlineticketing.service.FlightService;
import com.uakcelik.airlineticketing.service.RouteService;

@RestController
@RequestMapping(value = "/flight")
public class FlightController {

	@Autowired
	private FlightService flightService;
	
	@Autowired
	private AirlineService airlineService;
	
	@Autowired
	private RouteService routeService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<FlightObject> getFlights(BigDecimal minPrice, BigDecimal maxPrice, Integer airlineId, Integer routeId, int page, int size) 
	{
		List<FlightObject> flightObjectList = new ArrayList<>();
		if (page < 1) {
			return flightObjectList;
		}
		
		flightObjectList = flightService.searchFlights(minPrice, maxPrice, airlineId, routeId, page, size);
		return flightObjectList;
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public int countFlights(BigDecimal minPrice, BigDecimal maxPrice, Integer airlineId, Integer routeId) 
	{
		int count = flightService.countFlights(minPrice, maxPrice, airlineId, routeId);
		return count;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseItem saveFlight(FlightObject flightObject) 
	{
		ResponseItem responseItem = new ResponseItem();
		
		Flight flight = new Flight();
		
		if (flightObject.getId() != null) {
			// update
			flight = flightService.getFlightById(flightObject.getId());
		}
		else {
			flight.setCreateDate(new Date());
		}
		
		Airline airline = airlineService.getAirlineByIdAndEnabled(flightObject.getAirlineId(), true);
		Route route = routeService.getRouteByIdAndEnabled(flightObject.getRouteId(), true);
		
		if (airline == null) {
			responseItem.setMessage("Havayolu firması bulunamadı!");
			responseItem.setResult(false);
			return responseItem;
		}
		
		if (route == null) {
			responseItem.setMessage("Rota bulunamadı!");
			responseItem.setResult(false);
			return responseItem;
		}
		
		flight.setCapacity(flightObject.getCapacity());
		flight.setPrice(flightObject.getPrice());
		flight.setAirline(airline);
		flight.setRoute(route);
		flight.setUpdateDate(new Date());
		flight.setEnabled(true);
		
		try {
			flightService.saveFlight(flight);
			
			responseItem.setResult(true);
			responseItem.setMessage("Kaydedildi!");
			
		} catch (Exception e) {
			e.printStackTrace();
			
			responseItem.setResult(false);
			responseItem.setMessage(e.getMessage());
			return responseItem;
		}
		
		return responseItem;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseItem deleteFlight(int id){
		
		ResponseItem responseItem = new ResponseItem();
		
		try {
			// TODO : check sold tickets
			
			Flight flight = flightService.getFlightById(id);
			flight.setEnabled(false);
			flight.setUpdateDate(new Date());
			flightService.saveFlight(flight);
			
			responseItem.setMessage("Uçuş silindi!");
			responseItem.setResult(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			responseItem.setMessage(e.getMessage());
			responseItem.setResult(false);
			return responseItem;
		}
		
	    return responseItem;
	}
	
}
