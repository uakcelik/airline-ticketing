package com.uakcelik.airlineticketing.object;

public class RouteObject {

	private Integer id;
	private String createDate;
	private String updateDate;
	private Integer airportIdFrom;
	private Integer airportIdTo;
	private Boolean enabled;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getAirportIdFrom() {
		return airportIdFrom;
	}
	public void setAirportIdFrom(Integer airportIdFrom) {
		this.airportIdFrom = airportIdFrom;
	}
	public Integer getAirportIdTo() {
		return airportIdTo;
	}
	public void setAirportIdTo(Integer airportIdTo) {
		this.airportIdTo = airportIdTo;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
