package com.shop.shopservice.entity;

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
@Table(name = "HASHTAG")
@NamedQueries({
    @NamedQuery(name="Hashtag.findAll",
                query="SELECT h FROM Hashtag h"),
    @NamedQuery(name="Hashtag.findByType",
    			query="SELECT h FROM Hashtag h WHERE h.catagoryId = :catagoryId")
}) 
public class Hashtag {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "CATAGORY_ID", nullable = false)
	private int catagoryId;
	
	@Column(name = "USER_ID", nullable = false)
	private int userId;
	
	@Column(name = "TAG_TEXT", nullable = false)
	private String tagText;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the catagoryId
	 */
	public int getCatagoryId() {
		return catagoryId;
	}

	/**
	 * @param catagoryId the catagoryId to set
	 */
	public void setCatagoryId(int catagoryId) {
		this.catagoryId = catagoryId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the tagText
	 */
	public String getTagText() {
		return tagText;
	}

	/**
	 * @param tagText the tagText to set
	 */
	public void setTagText(String tagText) {
		this.tagText = tagText;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
}
