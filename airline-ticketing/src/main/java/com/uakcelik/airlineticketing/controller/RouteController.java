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
import com.uakcelik.airlineticketing.db.entity.Route;
import com.uakcelik.airlineticketing.object.ResponseItem;
import com.uakcelik.airlineticketing.object.RouteObject;
import com.uakcelik.airlineticketing.service.AirportService;
import com.uakcelik.airlineticketing.service.FlightService;
import com.uakcelik.airlineticketing.service.RouteService;

@RestController
@RequestMapping(value = "/route")
public class RouteController {

	@Autowired
	private RouteService routeService;
	
	@Autowired
	private AirportService airportService;
	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<RouteObject> getRoutes(Integer airportIdFrom, Integer airportIdTo, int page, int size) 
	{
		List<RouteObject> routeObjectList = new ArrayList<>();
		if (page < 1) {
			return routeObjectList;
		}
		
		routeObjectList = routeService.searchRoutes(airportIdFrom, airportIdTo, page, size);
		return routeObjectList;
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public int countRoutes(Integer airportIdFrom, Integer airportIdTo) 
	{
		int count = routeService.countRoutes(airportIdFrom, airportIdTo);
		return count;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseItem saveRoute(RouteObject routeObject) 
	{
		ResponseItem responseItem = new ResponseItem();
		
		// new route, control airportFrom and airportTo
		if (routeObject.getId() == null) {
			Route checkedRoute = routeService.getRouteByAirportIdFromAndAirportIdToAndEnabled(routeObject.getAirportIdFrom(), routeObject.getAirportIdTo(), true);
			if (checkedRoute != null) {
				responseItem.setMessage("Bu rota zaten mevcut!");
				responseItem.setResult(false);
				return responseItem;
			}
		}
		
		Airport airportFrom = airportService.getAirportByIdAndEnabled(routeObject.getAirportIdFrom(), true);
		Airport airportTo = airportService.getAirportByIdAndEnabled(routeObject.getAirportIdTo(), true);
		
		if (airportFrom == null) {
			responseItem.setMessage("Kalkış havaalanı bulunamadı!");
			responseItem.setResult(false);
			return responseItem;
		}
		
		if (airportTo == null) {
			responseItem.setMessage("Varış havaalanı bulunamadı!");
			responseItem.setResult(false);
			return responseItem;
		}
		
		Route route = new Route();
		
		if (routeObject.getId() != null) {
			// update
			route = routeService.getRouteById(routeObject.getId());
		}
		else {
			route.setCreateDate(new Date());
		}
		
		route.setAirportFrom(airportFrom);
		route.setAirportTo(airportTo);
		route.setUpdateDate(new Date());
		route.setEnabled(true);
		
		try {
			routeService.saveRoute(route);
			
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
	public ResponseItem deleteRoute(int id){
		
		ResponseItem responseItem = new ResponseItem();
		
		try {
			int countFlightsByRouteId = flightService.countFlightsByRouteId(id);
			if (countFlightsByRouteId > 0) {
				responseItem.setMessage("Bu rotaya tanımlı uçuş bulunmaktadır, rota silinemez!");
				responseItem.setResult(false);
				return responseItem;
			}
			
			Route route = routeService.getRouteById(id);
			route.setEnabled(false);
			route.setUpdateDate(new Date());
			routeService.saveRoute(route);
			
			responseItem.setMessage("Rota silindi!");
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
