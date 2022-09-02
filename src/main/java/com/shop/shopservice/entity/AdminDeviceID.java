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
@Table(name="Admin_DEVICE_ID_MAPPING")
@NamedQueries({
	@NamedQuery(name="AdminDeviceID.fetchAdminDeviceDetailsByDeviceId",query="select ad from AdminDeviceID ad where ad.deviceId LIKE :deviceId"),
	@NamedQuery(name="AdminDeviceID.fetchAdminId",query="select ad.admin from AdminDeviceID ad where ad.deviceId LIKE :deviceId"),
	@NamedQuery(name="AdminDeviceID.updateDeviceId",query="UPDATE AdminDeviceID SET deviceId = ? where id = ?"),
	@NamedQuery(name="AdminDeviceID.getDeviceID", query = "select ad.apnsTokenId from AdminDeviceID ad " +
	" where ad.admin.adminId = :adminId and ad.apnsTokenId !=null")
})

public class AdminDeviceID implements Serializable {
	
	private static final long serialVersionUID = -7610182756366363751L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADMIN_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Admin admin;
	
	public AdminDeviceID() {
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

		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}

		/**
		 * @return the admin
		 */
		public Admin getAdmin() {
			return admin;
		}

		/**
		 * @param admin the admin to set
		 */
		public void setAdmin(Admin admin) {
			this.admin = admin;
		}

		/**
		 * @return the deviceId
		 */
		public String getDeviceId() {
			return deviceId;
		}

		/**
		 * @param deviceId the deviceId to set
		 */
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
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
