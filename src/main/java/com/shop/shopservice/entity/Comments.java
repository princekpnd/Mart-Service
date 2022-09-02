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
@Table(name = "COMMENTS")
@NamedQueries({
    @NamedQuery(name="Comments.findAll",
                query="SELECT c FROM Comments c"),
    @NamedQuery(name="Comments.findByTopics",
    			query="SELECT c FROM Comments c WHERE c.topicsId = :topicsId")
}) 
public class Comments {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "TOPICS_ID", nullable = false)
	private int topicsId;
	
	@Column(name = "USER_ID", nullable = false)
	private int userId;
	
	@Column(name = "COMMENTS_TYPE", nullable = false)
	private int commentsType;
	
	@Column(name = "COMMENTS_TEXT", nullable = false)
	private String commentsText;
	
	@Column(name = "USER_DISPLAY_NAME", nullable = true)
	private String userDisplayName;
	
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
	 * @return the topicsId
	 */
	public int getTopicsId() {
		return topicsId;
	}

	/**
	 * @param topicsId the topicsId to set
	 */
	public void setTopicsId(int topicsId) {
		this.topicsId = topicsId;
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
	 * @return the commentsType
	 */
	public int getCommentsType() {
		return commentsType;
	}

	/**
	 * @param commentsType the commentsType to set
	 */
	public void setCommentsType(int commentsType) {
		this.commentsType = commentsType;
	}

	/**
	 * @return the commentsText
	 */
	public String getCommentsText() {
		return commentsText;
	}

	/**
	 * @param commentsText the commentsText to set
	 */
	public void setCommentsText(String commentsText) {
		this.commentsText = commentsText;
	}

	/**
	 * @return the userDisplayName
	 */
	public String getUserDisplayName() {
		return userDisplayName;
	}

	/**
	 * @param userDisplayName the userDisplayName to set
	 */
	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
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

	public Comments(int topicsId, int userId, int commentsType, String commentsText, String userDisplayName,
			Date createdOn) {
		super();
		this.topicsId = topicsId;
		this.userId = userId;
		this.commentsType = commentsType;
		this.commentsText = commentsText;
		this.userDisplayName = userDisplayName;
		this.createdOn = createdOn;
	}

	public Comments() {
		super();
	}

	}
