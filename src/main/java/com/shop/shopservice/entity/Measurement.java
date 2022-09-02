package com.shop.shopservice.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name ="Measurement")
@NamedQueries({
	        @NamedQuery(name= "Measurement.findAll",
	        		query ="SELECT me FROM Measurement me"),
	        @NamedQuery(name= "Measurement.findByShopId",
	                 query = "SELECT me FROM Measurement me WHERE me.shopId = :shopId and isActive is TRUE and isDeleted is FALSE"),
	        @NamedQuery(name ="Measurement.existMeasurement",
	                  query ="SELECT me FROM Measurement me WHERE me.title = :title and me.shopId = :shopId"),
	        @NamedQuery(name="Measurement.findByTitle",
	        		query ="SELECT me FROM Measurement me WHERE me.title = :title and me.shopId = :shopId"),
	        @NamedQuery(name ="Measurement.deActiveForExcel",
	        		query = "SELECT me FROM Measurement me WHERE me.id = :id")
	
})
public class Measurement implements Serializable{
	
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name= "ID", nullable = false)
	private int id;
	
	@Column(name= "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name= "TITLE", nullable = false)
	private String title;
	
	@Column(name= "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name="IS_DELETED", nullable = false )
	private boolean isDeleted;
	
	@Column(name="CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name="NAME", nullable = false)
	private String name;
	
	
	
	public Measurement(String shopId, String name) {
		this.shopId = shopId;
		this.name = name;
	}
	public Measurement() {
		super();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the shopId
	 */
	public String getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(String shopId) {
		this.shopId = shopId;
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

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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


	
	

}
