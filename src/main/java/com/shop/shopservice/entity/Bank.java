package com.shop.shopservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BANK")
@NamedQueries({ 
	@NamedQuery(name = "Bank.findAll",
				query = "SELECT bk FROM Bank bk"),
	 @NamedQuery(name="Bank.findByUserAccountNumber",
                query="SELECT bk FROM Bank bk WHERE bk.accountNum = :accountNum"),
	@NamedQuery(name = "Bank.findByShopId",
	           query = "SELECT bk FROM Bank bk WHERE bk.shopId= :shopId and isActive is TRUE and isDeleted is FALSE ")
	})


public class Bank implements Serializable{
	
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(generator = "sequence")
//	@SequenceGenerator(name ="sequence", sequenceName = "seq", allocationSize = 101)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "ACCOUNT_NUM", nullable = false)
	private int accountNum;
	
	@Column(name = "BANK_NAME", nullable = false)
	private String bankName;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "ADDRESS", nullable = false)
	private String address;
	
	@Column(name = "IFSC", nullable = false)
	private String ifsc;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	public Bank() {
		super();
	}
	
	public Bank(int accountNum, String name) {
		this.accountNum = accountNum;
		this.name = name;
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
	 * @return the accountNum
	 */
	public int getAccountNum() {
		return accountNum;
	}

	/**
	 * @param accountNum the accountNum to set
	 */
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the ifsc
	 */
	public String getIfsc() {
		return ifsc;
	}

	/**
	 * @param ifsc the ifsc to set
	 */
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
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
