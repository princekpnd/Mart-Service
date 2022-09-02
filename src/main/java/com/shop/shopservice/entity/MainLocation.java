package com.shop.shopservice.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Transient;

public class MainLocation {
  ArrayList<Object> destination_addresses = new ArrayList<Object>();
  ArrayList<Object> origin_addresses = new ArrayList<Object>();
  
  
  @Transient
	private List<Rows> rows;
  
  private String status;

/**
 * @return the destination_addresses
 */
public ArrayList<Object> getDestination_addresses() {
	return destination_addresses;
}

/**
 * @param destination_addresses the destination_addresses to set
 */
public void setDestination_addresses(ArrayList<Object> destination_addresses) {
	this.destination_addresses = destination_addresses;
}

/**
 * @return the origin_addresses
 */
public ArrayList<Object> getOrigin_addresses() {
	return origin_addresses;
}

/**
 * @param origin_addresses the origin_addresses to set
 */
public void setOrigin_addresses(ArrayList<Object> origin_addresses) {
	this.origin_addresses = origin_addresses;
}

/**
 * @return the rows
 */
public List<Rows> getRows() {
	return rows;
}

/**
 * @param rows the rows to set
 */
public void setRows(List<Rows> rows) {
	this.rows = rows;
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