package com.uakcelik.airlineticketing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uakcelik.airlineticketing.db.entity.Airline;
import com.uakcelik.airlineticketing.object.AirlineObject;
import com.uakcelik.airlineticketing.object.ResponseItem;
import com.uakcelik.airlineticketing.service.AirlineService;
import com.uakcelik.airlineticketing.service.FlightService;

@RestController
@RequestMapping(value = "/airline")
public class AirlineController {

	@Autowired
	private AirlineService airlineService;
	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<AirlineObject> getAirlines(String name, int page, int size) 
	{
		List<AirlineObject> airlineObjectList = new ArrayList<>();
		if (page < 1) {
			return airlineObjectList;
		}
		
		airlineObjectList = airlineService.searchAirlines(name, page, size);
		return airlineObjectList;
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public int countAirlines(String name) 
	{
		int count = airlineService.countAirlines(name);
		return count;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseItem saveAirline(AirlineObject airlineObject) 
	{
		ResponseItem responseItem = new ResponseItem();
		
		// new airline, control name
		if (airlineObject.getId() == null) {
			Airline checkedAirline = airlineService.getAirlineByNameAndEnabled(airlineObject.getName(), true);
			if (checkedAirline != null) {
				responseItem.setMessage("Firma adı zaten mevcut!");
				responseItem.setResult(false);
				return responseItem;
			}
		}
		
		Airline airline = new Airline();
		
		if (airlineObject.getId() != null) {
			// update
			airline = airlineService.getAirlineById(airlineObject.getId());
		}
		else {
			airline.setCreateDate(new Date());
		}
		
		airline.setName(airlineObject.getName());
		airline.setUpdateDate(new Date());
		airline.setEnabled(true);
		
		try {
			airlineService.saveAirline(airline);
			
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
	public ResponseItem deleteAirline(int id){
		
		ResponseItem responseItem = new ResponseItem();
		
		try {
			int countFlightsByAirlineId = flightService.countFlightsByAirlineId(id);
			if (countFlightsByAirlineId > 0) {
				responseItem.setMessage("Bu havayolu firmasına ait uçuş bulunmaktadır, firma silinemez!");
				responseItem.setResult(false);
				return responseItem;
			}
			
			Airline airline = airlineService.getAirlineById(id);
			airline.setEnabled(false);
			airline.setUpdateDate(new Date());
			airlineService.saveAirline(airline);
			
			responseItem.setMessage("Havayolu firması silindi!");
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
