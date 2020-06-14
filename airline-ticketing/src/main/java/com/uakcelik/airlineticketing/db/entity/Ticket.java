package com.uakcelik.airlineticketing.db.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ticket")
public class Ticket {

	private int id;
	private int ticketNumber;
	private BigDecimal salePrice;
	private String creditCard;
	private Date saleDate;
	private boolean enabled;
	private Flight flight;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "ticket_number", unique = true, nullable = false)
	public int getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	@Column(name = "sale_price", nullable = false)
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	
	@Column(name = "credit_card", nullable = false, length = 16)
	public String getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sale_date", nullable = false, length = 29)
	public Date getSaleDate() {
		return saleDate;
	}
	
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	
	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flight_id", nullable = false)
	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
}
