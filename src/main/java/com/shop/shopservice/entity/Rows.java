package com.shop.shopservice.entity;
import java.util.List;

import javax.persistence.Transient;

public class Rows {
 
	@Transient
	private List<Elements> elements;

	/**
	 * @return the element
	 */
	public List<Elements> getElements() {
		return elements;
	}

	/**
	 * @param element the element to set
	 */
	public void setElements(List<Elements> element) {
		this.elements = element;
	}
	
	

}