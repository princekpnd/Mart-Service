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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "TIMELINE")
@NamedQueries({
    @NamedQuery(name="TimeLine.findAll",
                query="SELECT t FROM TimeLine t"),
    @NamedQuery(name="TimeLine.findByUserConsultant",
    			query="SELECT t FROM TimeLine t WHERE t.userId = :userId and t.consultantId = :consultantId"),
    @NamedQuery(name="TimeLine.findByUserAndCatagory",
				query="SELECT t FROM TimeLine t WHERE t.userId = :userId and t.catagoryId = :catagoryId"),
    @NamedQuery(name="TimeLine.findByUser",
				query="SELECT t FROM TimeLine t WHERE t.userId = :userId"),
    @NamedQuery(name="TimeLine.findByConsultant",
				query="SELECT t FROM TimeLine t WHERE t.consultantId = :consultantId"),
    @NamedQuery(name="TimeLine.findByTwitterId",
				query="SELECT t FROM TimeLine t WHERE t.twitterId = :twitterId"),
    @NamedQuery(name="TimeLine.findByCatagory",
				query="SELECT t FROM TimeLine t WHERE t.catagoryId = :catagoryId"),
    @NamedQuery(name="TimeLine.findByUserConsultantAnswered",
				query="SELECT t FROM TimeLine t WHERE t.userId = :userId and t.consultantId = :consultantId and t.isAnswered = :isAnswered"),
})
public class TimeLine {

	

	@Id
	@Column(name = "TIMELINE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "USER_ID")
	private int userId;
	
	@Column(name = "CONSULTANT_ID")
	private int consultantId;
	
	@Field(store = Store.NO)
	@NotNull
	@Column(name = "Q_ARTICLE")
    private String qArticle;
	
	@Field(store = Store.NO)
	@Column(name = "A_ARTICLE")
    private String aArticle;
	
	@Column(name = "LIKE_COUNT")
	private int likeCount;
	
	@Column(name = "CATAGORY_ID")
	private int catagoryId;
	
	@Column(name = "DISLIKE_COUNT")
	private int dislikeCount;
	
	@Column(name = "SHARE_URL")
	private String shareUrl;
	
	@Column(name = "LIKE_USER_ID")
	private String likeUserId;
	
	@Column(name = "DISLIKE_USER_ID")
	private String disLikeUserId;
	
	@Column(name = "ANSWER_STATUS")
	private boolean isAnswered;
	
	@Column(name = "Q_TYPE")
	private int qType;
	
	@Column(name = "TWITTER_ID")
	private String twitterId;

	public TimeLine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TimeLine(int consultantId, String qArticle, int qType, Date createdOn) {
		super();
		this.consultantId = consultantId;
		this.qArticle = qArticle;
		this.qType = qType;
		this.createdOn = createdOn;
	}

	public TimeLine(int userId,int consultantId, String qArticle, int qType, Date createdOn) {
		super();
		this.userId = userId;
		this.consultantId = consultantId;
		this.qArticle = qArticle;
		this.qType = qType;
		this.createdOn = createdOn;
	}
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "ANSWERED_ON")
	private Date answeredOn;
	
	@Transient
	private String userName;
	@Transient
	private String consultantName;
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
	 * @return the consultantId
	 */
	public int getConsultantId() {
		return consultantId;
	}

	/**
	 * @param consultantId the consultantId to set
	 */
	public void setConsultantId(int consultantId) {
		this.consultantId = consultantId;
	}

	/**
	 * @return the qArticle
	 */
	public String getqArticle() {
		return qArticle;
	}

	/**
	 * @param qArticle the qArticle to set
	 */
	public void setqArticle(String qArticle) {
		this.qArticle = qArticle;
	}

	/**
	 * @return the aArticle
	 */
	public String getaArticle() {
		return aArticle;
	}

	/**
	 * @param aArticle the aArticle to set
	 */
	public void setaArticle(String aArticle) {
		this.aArticle = aArticle;
	}

	/**
	 * @return the likeCount
	 */
	public int getLikeCount() {
		return likeCount;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * @return the dislikeCount
	 */
	public int getDislikeCount() {
		return dislikeCount;
	}

	/**
	 * @param dislikeCount the dislikeCount to set
	 */
	public void setDislikeCount(int dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

	/**
	 * @return the shareUrl
	 */
	public String getShareUrl() {
		return shareUrl;
	}

	/**
	 * @param shareUrl the shareUrl to set
	 */
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	/**
	 * @return the likeUserId
	 */
	public String getLikeUserId() {
		return likeUserId;
	}

	/**
	 * @param likeUserId the likeUserId to set
	 */
	public void setLikeUserId(String likeUserId) {
		this.likeUserId = likeUserId;
	}

	/**
	 * @return the disLikeUserId
	 */
	public String getDisLikeUserId() {
		return disLikeUserId;
	}

	/**
	 * @param disLikeUserId the disLikeUserId to set
	 */
	public void setDisLikeUserId(String disLikeUserId) {
		this.disLikeUserId = disLikeUserId;
	}

	/**
	 * @return the isAnswered
	 */
	public boolean isAnswered() {
		return isAnswered;
	}

	/**
	 * @param isAnswered the isAnswered to set
	 */
	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	/**
	 * @return the qType
	 */
	public int getqType() {
		return qType;
	}

	/**
	 * @param qType the qType to set
	 */
	public void setqType(int qType) {
		this.qType = qType;
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

	/**
	 * @return the answeredOn
	 */
	public Date getAnsweredOn() {
		return answeredOn;
	}

	/**
	 * @param answeredOn the answeredOn to set
	 */
	public void setAnsweredOn(Date answeredOn) {
		this.answeredOn = answeredOn;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the consultantName
	 */
	public String getConsultantName() {
		return consultantName;
	}

	/**
	 * @param consultantName the consultantName to set
	 */
	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
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
	 * @return the twitterId
	 */
	public String getTwitterId() {
		return twitterId;
	}

	/**
	 * @param twitterId the twitterId to set
	 */
	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	
	
}
