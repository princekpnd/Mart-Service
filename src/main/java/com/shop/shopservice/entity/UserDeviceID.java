package com.shop.shopservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name="USER_DEVICE_ID_MAPPING")
@NamedQueries({
	@NamedQuery(name="UserDeviceID.fetchUserDeviceDetailsByDeviceId",query="select ud from UserDeviceID ud where ud.deviceId LIKE :deviceId"),
	@NamedQuery(name="UserDeviceID.fetchUserId",query="select ud.user from UserDeviceID ud where ud.deviceId LIKE :deviceId"),
	@NamedQuery(name="UserDeviceID.updateDeviceId",query="UPDATE UserDeviceID SET deviceId = ? where id = ?"),
	@NamedQuery(name="UserDeviceID.getDeviceID", query = "select ud.apnsTokenId from UserDeviceID ud " +
	" where ud.user.userId = :userId and ud.apnsTokenId !=null")
})

public class UserDeviceID implements Serializable {
	
	private static final long serialVersionUID = -7610182756366363751L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private User user;
	
	public UserDeviceID() {
		super();
	}

	//@NotNull
	@Column(name="DEVICE_ID",nullable=false)
	private String deviceId;
	
	@Column(name="PLAYER_ID",nullable=false)
	private String playerId;
	
	@Column(name="APNS_TOKEN_ID")
	private String apnsTokenId;
	
	@Transient
	private boolean isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the apnsTokenId
	 */
	public String getApnsTokenId() {
		return apnsTokenId;
	}

	/**
	 * @param apnsTokenId the apnsTokenId to set
	 */
	public void setApnsTokenId(String apnsTokenId) {
		this.apnsTokenId = apnsTokenId;
	}

	/**
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	
}
