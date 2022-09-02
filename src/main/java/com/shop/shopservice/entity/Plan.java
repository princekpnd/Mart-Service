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
@Table(name ="PLAN")
@NamedQueries({
	@NamedQuery(name = "Plan.findAll",
               query = "SELECT pl FROM Plan pl"),
	@NamedQuery(name = "Plan.findByPlanType",
	           query ="SELECT pl FROM Plan pl WHERE pl.planType = :planType"),
	@NamedQuery(name ="Plan.planExitByPlanType",
	           query ="SELECT pl FROM Plan pl WHERE pl.planType = :planType"),
	@NamedQuery(name ="Plan.findActivePlan",
	           query ="SELECT pl FROM Plan pl WHERE pl.planType = :planType and  isActive is TRUE and isDeleted is FALSE "),
	@NamedQuery(name ="Plan.findActivePlanById",
	           query ="SELECT pl FROM Plan pl  WHERE pl.id = :id and isActive is TRUE"),
	@NamedQuery(name = "Plan.findAllActive",
			 query ="SELECT pl FROM Plan pl  WHERE isActive is TRUE"),
	
})

public class Plan implements Serializable {
	
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "PLAN_TYPE", nullable = false)
	private int planType;
	
	@Column(name = "VALIDITY", nullable = false)
	private int validity;
	
	@Column(name = "AMOUNT", nullable = false)
	private int amount;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name ="DISCOUNT", nullable = false)
	private int discount;
	
	@Column(name ="IS_DISCOUNT", nullable = false)
	private boolean isDiscount;
	
	@Column(name ="OFFER_ACTIVE_END", nullable = false)
	private boolean offerActiveEnd;
	
	public Plan(int planType) {
		this.planType = planType;
		
	}
	
	public Plan() {
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
	 * @return the planType
	 */
	public int getPlanType() {
		return planType;
	}

	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(int planType) {
		this.planType = planType;
	}

	/**
	 * @return the validity
	 */
	public int getValidity() {
		return validity;
	}

	/**
	 * @param validity the validity to set
	 */
	public void setValidity(int validity) {
		this.validity = validity;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
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
	 * @return the discount
	 */
	public int getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(int discount) {
		this.discount = discount;
	}

	/**
	 * @return the isDiscount
	 */
	public boolean isDiscount() {
		return isDiscount;
	}

	/**
	 * @param isDiscount the isDiscount to set
	 */
	public void setDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}

	/**
	 * @return the offerActiveEnd
	 */
	public boolean isOfferActiveEnd() {
		return offerActiveEnd;
	}

	/**
	 * @param offerActiveEnd the offerActiveEnd to set
	 */
	public void setOfferActiveEnd(boolean offerActiveEnd) {
		this.offerActiveEnd = offerActiveEnd;
	}
	
	
	

}