package com.shop.shopservice.entity;

import javax.persistence.Column;

public class ExcelStringEntity {

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "TITLE", nullable = false)
	private String title;	

	public ExcelStringEntity() {
		super();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
