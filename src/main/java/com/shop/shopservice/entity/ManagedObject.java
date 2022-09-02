package com.shop.shopservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Avinash
 *
 */

@Entity
@Table(name = "managedobject")
public class ManagedObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	protected int ID;
	
	@Column(name = "GUID", nullable = false)
	protected String GUID;
	
	@Column(name = "createdDate", nullable = false)
	protected Date createdDate;
	
	@Column(name = "modifiedDate", nullable = true)
	protected Date modifiedDate;
	
	@Column(name = "createdBy", nullable = false)
	protected int createdBy;
	
	@Column(name = "modifiedBy", nullable = true)
	protected int modifiedBy;
	
	@Column(name = "objectType", nullable = false)
	protected int objectType;

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the objectType
	 */
	public int getObjectType() {
		return objectType;
	}

	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public String getGUID() {
		return GUID;
	}

	public void setGUID(String gUID) {
		GUID = gUID;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ManagedObject() {
	}

	public ManagedObject(String gUID, Date createdDate, int createdBy, int objectType) {
		this.GUID = gUID;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.objectType = objectType;
	}

}
