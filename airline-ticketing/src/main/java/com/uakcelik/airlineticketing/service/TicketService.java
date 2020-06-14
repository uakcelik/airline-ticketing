package com.uakcelik.airlineticketing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uakcelik.airlineticketing.db.entity.Flight;
import com.uakcelik.airlineticketing.db.entity.Ticket;
import com.uakcelik.airlineticketing.db.repository.FlightRepository;
import com.uakcelik.airlineticketing.db.repository.TicketRepository;
import com.uakcelik.airlineticketing.helper.DateUtil;
import com.uakcelik.airlineticketing.object.ResponseItem;
import com.uakcelik.airlineticketing.object.TicketObject;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private FlightRepository flightRepository;
	
	public List<TicketObject> searchFlights(Integer ticketNumber, Integer flightId, int page, int size) {
		
		List<TicketObject> listTicketObject = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		List<Ticket> listTicket = ticketRepository.findTicketByTicketNumberAndFlightIdAndEnabledOrderById(ticketNumber, flightId, true, pageable);
		
		for (Ticket ticket : listTicket) {
			TicketObject ticketObject = new TicketObject();
			ticketObject.setId(ticket.getId());
			ticketObject.setTicketNumber(ticket.getTicketNumber());
			ticketObject.setSalePrice(ticket.getSalePrice());
			ticketObject.setCreditCard(ticket.getCreditCard());
			ticketObject.setSaleDate(DateUtil.format(ticket.getSaleDate(), DateUtil.DATETIME));
			ticketObject.setFlightId(ticket.getFlight().getId());
			ticketObject.setEnabled(ticket.isEnabled());
			
			listTicketObject.add(ticketObject);
		}
		
		return listTicketObject;
	}
	
	public int countTickets(Integer ticketNumber, Integer flightId) {
		
		int count = ticketRepository.countTicketByTicketNumberAndFlightIdAndEnabled(ticketNumber, flightId, true);
		return count;
	}
	
	public Ticket getTicketById(int id) {
		Ticket ticket = ticketRepository.findById(id);
		return ticket;
	}
	
	public void saveTicket(Ticket ticket) {
		ticketRepository.save(ticket);
	}
	
	@Transactional
	synchronized public ResponseItem saleTicket(Flight flight, String creditCard, BigDecimal salePrice) {
		ResponseItem responseItem = new ResponseItem();
		
		if (salePrice.compareTo(flight.getPrice()) != 0) {
			responseItem.setMessage("Fiyat bilgisi yanlış, işlem tamamlanamadı!");
			responseItem.setResult(false);
			return responseItem;
		}
		
		try {
			Ticket ticket = new Ticket();
			ticket.setTicketNumber(createTicketNumber());
			ticket.setSalePrice(salePrice);
			
			creditCard = maskCreditCard(creditCard);
			if (creditCard.length() != 16) {
				responseItem.setMessage("Kredi kartı bilgisi hatalı!");
				responseItem.setResult(false);
				return responseItem;
			}
			ticket.setCreditCard(creditCard);
			
			ticket.setSaleDate(new Date());
			ticket.setFlight(flight);
			ticket.setEnabled(true);
			
			ticketRepository.save(ticket);
			
			// bilet sayısını kontrol et, uçuş kapasitesinin her %10 artışında bilet fiyatı da %10 artmalı
			int capacity = flight.getCapacity();
			int counter = capacity / 10; // Yüzde 10'luk artış sayısı
			
			int countTickets = ticketRepository.countByFlightId(flight.getId());
			if (countTickets % counter == 0) {
				// %10 luk artış, bilet fiyatını da %10 artır.
				BigDecimal price = flight.getPrice();
				BigDecimal newPrice = price.multiply(new BigDecimal(1.1));
				newPrice = newPrice.setScale(2, RoundingMode.HALF_EVEN);
				
				flight.setPrice(newPrice);
				flight.setUpdateDate(new Date());
				flightRepository.save(flight);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			responseItem.setMessage(e.getMessage());
			responseItem.setResult(false);
			return responseItem;
		}
		
		responseItem.setMessage("Satış tamamlandı!");
		responseItem.setResult(true);
		
		return responseItem;
	}
	
	public String maskCreditCard(String creditCard) {
		creditCard = creditCard.replaceAll("[^\\d.]", "");
		creditCard = creditCard.substring(0, 6) + "******" + creditCard.substring(12);
		return creditCard;
	}
	
	public int createTicketNumber() {
		Ticket ticket = ticketRepository.findFirstByOrderByTicketNumberDesc();
		
		int maxTicketNumber = 10000;
		if (ticket != null) {
			maxTicketNumber = ticket.getTicketNumber();
		}
		
		return (maxTicketNumber + 1);
	}

}
