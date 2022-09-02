package com.shop.shopservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "PROFILE")
@NamedQueries({
    @NamedQuery(name="Profile.findAll",
                query="SELECT p FROM Profile p"),
    @NamedQuery(name="Profile.findByCatagory",
    			query="SELECT p FROM Profile p WHERE p.catagory = :catagoryId"),
    @NamedQuery(name="Profile.findProfileOnAvailibility",
				query="SELECT p FROM Profile p WHERE p.availability = :availability"),
    @NamedQuery(name="Profile.findUserByTwitterId",
				query="SELECT p FROM Profile p WHERE p.twitterHandle = :twitterHandle"),
    @NamedQuery(name="Profile.findTwitterIdByUserId",
				query="SELECT p.twitterHandle FROM Profile p WHERE p.userId = :userId"),
    @NamedQuery(name="Profile.findBySubCatagory",
				query="SELECT p FROM Profile p WHERE p.subCatagoryList = :subCatagoryList"),
    @NamedQuery(name="Profile.findByUserProfile",
                query="SELECT p FROM Profile p WHERE p.userId = :userId"),
})
public class Profile {

	
	@Id
	@Column(name = "PROFILE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	public Profile() {
		super();
	}

	@Column(name = "USER_ID")
	private int userId;

	@Column(name = "ARTICLE_ID")
	private int articleId;
	
	@Column(name = "BACKGROUND_EXPERTISE")
	private String backgroundExpertise;
	
	@Column(name = "SUMMERY_DETAILS")
	private String summeryDetails;
	
	@Column(name = "CATEGORY")
	private int catagory;
	
	@Column(name = "SUB_CATAGORY")
	private String subCatagoryList;
	
	@Column(name = "SERVED_CONSULTATION")
	private int servedConsultation;
	
	@Column(name = "CONSULTATION_PRICE")
	private int consultationPrice;
	
	@Column(name = "TOTAL_EARNINGS")
	private int totalEarning;
	
	@Column(name = "AVG_RATING")
	private int averageRating;
	
	@Column(name = "AVAILIBILITY")
	private int availability;

	@Column(name = "AVG_RESPONSE_TIME")
	private int averageResponseTime;

	@Column(name = "DISPLAY_NAME")
	private String displayName;
	
	@Column(name = "LANGUAGE_PRIMARY")
	private int languagePrimary;
	
	@Column(name = "LANGUAGE_SECONDARY")
	private int languageSeconday;
	
	@Column(name = "TWITTER_HANDLE")
	private String twitterHandle;
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
	 * @return the backgroundExpertise
	 */
	public String getBackgroundExpertise() {
		return backgroundExpertise;
	}

	/**
	 * @param backgroundExpertise the backgroundExpertise to set
	 */
	public void setBackgroundExpertise(String backgroundExpertise) {
		this.backgroundExpertise = backgroundExpertise;
	}

	/**
	 * @return the summeryDetails
	 */
	public String getSummeryDetails() {
		return summeryDetails;
	}

	/**
	 * @param summeryDetails the summeryDetails to set
	 */
	public void setSummeryDetails(String summeryDetails) {
		this.summeryDetails = summeryDetails;
	}

	/**
	 * @return the catagory
	 */
	public int getCatagory() {
		return catagory;
	}

	/**
	 * @param catagory the catagory to set
	 */
	public void setCatagory(int catagory) {
		this.catagory = catagory;
	}

	/**
	 * @return the subCatagoryList
	 */
	public String getSubCatagoryList() {
		return subCatagoryList;
	}

	/**
	 * @param subCatagoryList the subCatagoryList to set
	 */
	public void setSubCatagoryList(String subCatagoryList) {
		this.subCatagoryList = subCatagoryList;
	}

	/**
	 * @return the servedConsultation
	 */
	public int getServedConsultation() {
		return servedConsultation;
	}

	/**
	 * @param servedConsultation the servedConsultation to set
	 */
	public void setServedConsultation(int servedConsultation) {
		this.servedConsultation = servedConsultation;
	}

	/**
	 * @return the consultationPrice
	 */
	public int getConsultationPrice() {
		return consultationPrice;
	}

	/**
	 * @param consultationPrice the consultationPrice to set
	 */
	public void setConsultationPrice(int consultationPrice) {
		this.consultationPrice = consultationPrice;
	}

	/**
	 * @return the totalEarning
	 */
	public int getTotalEarning() {
		return totalEarning;
	}

	/**
	 * @param totalEarning the totalEarning to set
	 */
	public void setTotalEarning(int totalEarning) {
		this.totalEarning = totalEarning;
	}

	/**
	 * @return the averageRating
	 */
	public int getAverageRating() {
		return averageRating;
	}

	/**
	 * @param averageRating the averageRating to set
	 */
	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}

	/**
	 * @return the availability
	 */
	public int getAvailability() {
		return availability;
	}

	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(int availability) {
		this.availability = availability;
	}

	/**
	 * @return the averageResponseTime
	 */
	public int getAverageResponseTime() {
		return averageResponseTime;
	}

	/**
	 * @param averageResponseTime the averageResponseTime to set
	 */
	public void setAverageResponseTime(int averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
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
	 * @return the articleId
	 */
	public int getArticleId() {
		return articleId;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the languagePrimary
	 */
	public int getLanguagePrimary() {
		return languagePrimary;
	}

	/**
	 * @param languagePrimary the languagePrimary to set
	 */
	public void setLanguagePrimary(int languagePrimary) {
		this.languagePrimary = languagePrimary;
	}

	/**
	 * @return the languageSeconday
	 */
	public int getLanguageSeconday() {
		return languageSeconday;
	}

	/**
	 * @param languageSeconday the languageSeconday to set
	 */
	public void setLanguageSeconday(int languageSeconday) {
		this.languageSeconday = languageSeconday;
	}

	/**
	 * @return the twitterHandle
	 */
	public String getTwitterHandle() {
		return twitterHandle;
	}

	/**
	 * @param twitterHandle the twitterHandle to set
	 */
	public void setTwitterHandle(String twitterHandle) {
		this.twitterHandle = twitterHandle;
	}



}