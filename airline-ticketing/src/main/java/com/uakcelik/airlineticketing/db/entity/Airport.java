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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "airport")
public class Airport {

	private int id;
	private String name;
	private String country;
	private String city;
	private Date createDate;
	private Date updateDate;
	private boolean enabled;
	private Set<Route> routesFrom = new HashSet<Route>(0);
	private Set<Route> routesTo = new HashSet<Route>(0);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = false, length = 128)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "country", nullable = false, length = 64)
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Column(name = "city", nullable = false, length = 64)
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "airportFrom")
	public Set<Route> getRoutesFrom() {
		return routesFrom;
	}

	public void setRoutesFrom(Set<Route> routesFrom) {
		this.routesFrom = routesFrom;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "airportTo")
	public Set<Route> getRoutesTo() {
		return routesTo;
	}

	public void setRoutesTo(Set<Route> routesTo) {
		this.routesTo = routesTo;
	}

}
