package com.uakcelik.airlineticketing.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uakcelik.airlineticketing.db.entity.Flight;
import com.uakcelik.airlineticketing.db.entity.Ticket;
import com.uakcelik.airlineticketing.object.ResponseItem;
import com.uakcelik.airlineticketing.object.TicketObject;
import com.uakcelik.airlineticketing.service.FlightService;
import com.uakcelik.airlineticketing.service.TicketService;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<TicketObject> getFlights(Integer ticketNumber, Integer flightId, int page, int size) 
	{
		List<TicketObject> ticketObjectList = new ArrayList<>();
		if (page < 1) {
			return ticketObjectList;
		}
		
		ticketObjectList = ticketService.searchFlights(ticketNumber, flightId, page, size);
		return ticketObjectList;
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public int countTickets(Integer ticketNumber, Integer flightId) 
	{
		int count = ticketService.countTickets(ticketNumber, flightId);
		return count;
	}
	
	@RequestMapping(value = "/sale", method = RequestMethod.POST)
	public ResponseItem saveTicket(int flightId, String creditCard, BigDecimal salePrice) 
	{
		ResponseItem responseItem = new ResponseItem();
		
		Flight flight = flightService.getFlightByIdAndEnabled(flightId, true);
		if (flight == null) {
			responseItem.setMessage("Uçuş bilgisi bulunamadı!");
			responseItem.setResult(false);
			return responseItem;
		}
		
		responseItem = ticketService.saleTicket(flight, creditCard, salePrice);
		return responseItem;
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseItem cancelTicket(int id){
		
		ResponseItem responseItem = new ResponseItem();
		
//		ResponseItem responseItem = ticketService.cancelTicket(id);
		
		try {
			Ticket ticket = ticketService.getTicketById(id);
			ticket.setEnabled(false);
			ticketService.saveTicket(ticket);
			
			responseItem.setMessage("Bilet iptal edildi!");
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
