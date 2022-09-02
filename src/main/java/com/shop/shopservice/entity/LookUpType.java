package com.shop.shopservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "lookup_type")
@NamedQueries({
    @NamedQuery(name="LookUpType.findAll",
                query="SELECT lt FROM LookUpType lt")
}) 
public class LookUpType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int lookUpTypeId;
	
	@Column(name = "LOOKUP_TYPE_NAME", nullable = false)
	private String lookUpTypeName;
	@Column(name = "LOOKUP_TYPE_LABEL", nullable = false)
	private String lookUpTypeLabel;

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
	 * @return the lookUpTypeName
	 */
	public String getLookUpTypeName() {
		return lookUpTypeName;
	}

	/**
	 * @param lookUpTypeName the lookUpTypeName to set
	 */
	public void setLookUpTypeName(String lookUpTypeName) {
		this.lookUpTypeName = lookUpTypeName;
	}

	/**
	 * @return the lookUpTypeLabel
	 */
	public String getLookUpTypeLabel() {
		return lookUpTypeLabel;
	}

	/**
	 * @param lookUpTypeLabel the lookUpTypeLabel to set
	 */
	public void setLookUpTypeLabel(String lookUpTypeLabel) {
		this.lookUpTypeLabel = lookUpTypeLabel;
	}
}
