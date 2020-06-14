package com.uakcelik.airlineticketing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uakcelik.airlineticketing.db.entity.Route;
import com.uakcelik.airlineticketing.db.repository.RouteRepository;
import com.uakcelik.airlineticketing.helper.DateUtil;
import com.uakcelik.airlineticketing.object.RouteObject;

@Service
public class RouteService {
	
	@Autowired
	private RouteRepository routeRepository;

	public List<RouteObject> searchRoutes(Integer airportIdFrom, Integer airportIdTo, int page, int size) {
		
		List<RouteObject> listRouteObject = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		List<Route> listRoute = routeRepository.findRouteByAirportIdFromAndAirportIdToAndEnabledOrderById(airportIdFrom, airportIdTo, true, pageable);
		
		for (Route route : listRoute) {
			RouteObject routeObject = new RouteObject();
			routeObject.setId(route.getId());
			routeObject.setAirportIdFrom(route.getAirportFrom().getId());
			routeObject.setAirportIdTo(route.getAirportTo().getId());
			routeObject.setCreateDate(DateUtil.format(route.getCreateDate(), DateUtil.DATETIME));
			routeObject.setUpdateDate(DateUtil.format(route.getUpdateDate(), DateUtil.DATETIME));
			routeObject.setEnabled(route.isEnabled());
			
			listRouteObject.add(routeObject);
		}
		
		return listRouteObject;
	}
	
	public int countRoutes(Integer airportIdFrom, Integer airportIdTo) {
		
		int count = routeRepository.countRouteByAirportIdFromAndAirportIdToAndEnabled(airportIdFrom, airportIdTo, true);
		return count;
	}
	
	public Route getRouteByAirportIdFromAndAirportIdToAndEnabled(Integer airportIdFrom, Integer airportIdTo, boolean enabled) {
		Route route = routeRepository.findByAirportFromIdAndAirportToIdAndEnabled(airportIdFrom, airportIdTo, enabled);
		return route;
	}
	
	public Route getRouteById(int id) {
		Route route = routeRepository.findById(id);
		return route;
	}	
	
	public void saveRoute(Route route) {
		routeRepository.save(route);
	}
	
	public int countRouteByAirportId(int airportId) {
		int count = routeRepository.countRouteByAirportId(airportId, true);
		return count;
	}

	public Route getRouteByIdAndEnabled(int id, boolean enabled) {
		Route route = routeRepository.findByIdAndEnabled(id, enabled);
		return route;
	}
	
}
