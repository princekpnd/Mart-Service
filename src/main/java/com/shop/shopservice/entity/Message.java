package com.shop.shopservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE")
public class Message {

	
	@Id
	@Column(name = "MESSAGEID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "USER_FROM")
	private int userFrom;
	
	@Column(name = "USER_TO")
	private int userTo;
	
	@Column(name = "BODYCONTENT")
	private String messagebody;
	
	@Column(name = "CREATEDATE")
	private String createdAt;

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
	 * @return the userFrom
	 */
	public int getUserFrom() {
		return userFrom;
	}

	/**
	 * @param userFrom the userFrom to set
	 */
	public void setUserFrom(int userFrom) {
		this.userFrom = userFrom;
	}

	/**
	 * @return the userTo
	 */
	public int getUserTo() {
		return userTo;
	}

	/**
	 * @param userTo the userTo to set
	 */
	public void setUserTo(int userTo) {
		this.userTo = userTo;
	}

	/**
	 * @return the messagebody
	 */
	public String getMessagebody() {
		return messagebody;
	}

	/**
	 * @param messagebody the messagebody to set
	 */
	public void setMessagebody(String messagebody) {
		this.messagebody = messagebody;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Message(int userFrom, int userTo, String messagebody) {
		super();
		this.userFrom = userFrom;
		this.userTo = userTo;
		this.messagebody = messagebody;
	}

	public Message() {
		super();
	}
	
}