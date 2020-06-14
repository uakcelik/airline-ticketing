package com.uakcelik.airlineticketing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uakcelik.airlineticketing.db.entity.Airport;
import com.uakcelik.airlineticketing.object.AirportObject;
import com.uakcelik.airlineticketing.object.ResponseItem;
import com.uakcelik.airlineticketing.service.AirportService;
import com.uakcelik.airlineticketing.service.RouteService;

@RestController
@RequestMapping(value = "/airport")
public class AirportController {

	@Autowired
	private AirportService airportService;
	
	@Autowired
	private RouteService routeService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<AirportObject> getAirports(String name, String country, String city, int page, int size) 
	{
		List<AirportObject> airportObjectList = new ArrayList<>();
		if (page < 1) {
			return airportObjectList;
		}
		
		airportObjectList = airportService.searchAirports(name, country, city, page, size);
		return airportObjectList;
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public int countAirports(String name, String country, String city) 
	{
		int count = airportService.countAirports(name, country, city);
		return count;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseItem saveAirport(AirportObject airportObject) 
	{
		ResponseItem responseItem = new ResponseItem();
		
		// new airport, control country, city, name together
		if (airportObject.getId() == null) {
			Airport checkedAirport = airportService.getAirportByNameAndCountryAndCityAndEnabled(airportObject.getName(), airportObject.getCountry(), airportObject.getCity(), true);
			if (checkedAirport != null) {
				responseItem.setMessage("Bu ülke ve şehirde aynı isimde bir havaalanı zaten mevcut!");
				responseItem.setResult(false);
				return responseItem;
			}
		}
		
		Airport airport = new Airport();
		
		if (airportObject.getId() != null) {
			// update
			airport = airportService.getAirportById(airportObject.getId());
		}
		else {
			airport.setCreateDate(new Date());
		}
		
		airport.setName(airportObject.getName());
		airport.setCountry(airportObject.getCountry());
		airport.setCity(airportObject.getCity());
		airport.setUpdateDate(new Date());
		airport.setEnabled(true);
		
		try {
			airportService.saveAirport(airport);
			
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
	public ResponseItem deleteAirport(int id){
		
		ResponseItem responseItem = new ResponseItem();
		
		try {
			int countRouteByAirportId = routeService.countRouteByAirportId(id);
			if (countRouteByAirportId > 0) {
				responseItem.setMessage("Bu havaalanı üzerinde tanımlı rota bulunmaktadır, havaalanı silinemez!");
				responseItem.setResult(false);
				return responseItem;
			}
			
			Airport airport = airportService.getAirportById(id);
			airport.setEnabled(false);
			airport.setUpdateDate(new Date());
			airportService.saveAirport(airport);
			
			responseItem.setMessage("Havaalanı silindi!");
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
