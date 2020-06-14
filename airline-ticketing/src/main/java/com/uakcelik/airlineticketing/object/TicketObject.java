package com.uakcelik.airlineticketing.object;

import java.math.BigDecimal;

public class TicketObject {

	private Integer id;
	private Integer ticketNumber;
	private BigDecimal salePrice;
	private String creditCard;
	private String saleDate;
	private Integer flightId;
	private Boolean enabled;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public Integer getFlightId() {
		return flightId;
	}
	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
