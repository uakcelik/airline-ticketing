package com.uakcelik.airlineticketing.db.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "route")
public class Route {

	private int id;
	private Date createDate;
	private Date updateDate;
	private boolean enabled;
	private Airport airportFrom;
	private Airport airportTo;
	private Set<Flight> flights = new HashSet<Flight>(0);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 29)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 29)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "airport_id_from", nullable = false)
	public Airport getAirportFrom() {
		return airportFrom;
	}

	public void setAirportFrom(Airport airportFrom) {
		this.airportFrom = airportFrom;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "airport_id_to", nullable = false)
	public Airport getAirportTo() {
		return airportTo;
	}

	public void setAirportTo(Airport airportTo) {
		this.airportTo = airportTo;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "route")
	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}
	
}
