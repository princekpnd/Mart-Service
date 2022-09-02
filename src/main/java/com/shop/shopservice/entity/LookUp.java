package com.shop.shopservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "Lookup")
@NamedQueries({
    @NamedQuery(name="LookUp.findAllByLookUpType",
                query="SELECT lt FROM LookUp lt where lt.lookUpTypeId = :lookUpTypeId and lt.isActive is TRUE and lt.isDeleted is FALSE"),
    @NamedQuery(name="LookUp.findAllByLookUpTypeForAdmin",
    			query="SELECT lt FROM LookUp lt where lt.lookUpTypeId = :lookUpTypeId"),
    @NamedQuery(name="LookUp.findLookUpIdByName",
				query="SELECT lt FROM LookUp lt where lt.lookUpName = :lookUpName and lt.shopId = :shopId"),
}) 
public class LookUp {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int lookUpId;
	
	@Field(store = Store.NO)
	@NotNull
	@Column(name = "LOOKUP_NAME", nullable = false)
	private String lookUpName;
	
	@Field(store = Store.NO)
	@NotNull
	@Column(name = "LOOKUP_LABEL", nullable = false)
	private String lookUpLabel;
	
	@Column(name = "LOOKUP_TYPE_ID", nullable = false)
	private int lookUpTypeId;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	/**
	 * @return the lookUpId
	 */
	public int getLookUpId() {
		return lookUpId;
	}

	/**
	 * @param lookUpId the lookUpId to set
	 */
	public void setLookUpId(int lookUpId) {
		this.lookUpId = lookUpId;
	}

	/**
	 * @return the lookUpName
	 */
	public String getLookUpName() {
		return lookUpName;
	}

	/**
	 * @param lookUpName the lookUpName to set
	 */
	public void setLookUpName(String lookUpName) {
		this.lookUpName = lookUpName;
	}

	/**
	 * @return the lookUpLabel
	 */
	public String getLookUpLabel() {
		return lookUpLabel;
	}

	/**
	 * @param lookUpLabel the lookUpLabel to set
	 */
	public void setLookUpLabel(String lookUpLabel) {
		this.lookUpLabel = lookUpLabel;
	}

	/**
	 * @return the lookUpTypeId
	 */
	public int getLookUpTypeId() {
		return lookUpTypeId;
	}

	/**
	 * @param lookUpTypeId the lookUpTypeId to set
	 */
	public void setLookUpTypeId(int lookUpTypeId) {
		this.lookUpTypeId = lookUpTypeId;
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
}
