package com.shop.shopservice.entity;

import javax.persistence.Transient;

public class Elements {
	@Transient
	private Distance distance;
	
	
	@Transient
	private Duration duration;
	
	
	  private String status;


	/**
	 * @return the distance
	 */
	public Distance getDistance() {
		return distance;
	}


	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Distance distance) {
		this.distance = distance;
	}


	/**
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}


	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	

}