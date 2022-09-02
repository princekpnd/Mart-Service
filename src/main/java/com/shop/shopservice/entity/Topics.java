package com.shop.shopservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.shop.shopservice.utils.PrePersistUtil;

/**
 * @author Avinash
 *
 */
@Entity
@Indexed
@Table(name = "TOPICS")
@NamedQueries({
    @NamedQuery(name="Topics.findAll",
                query="SELECT t FROM Topics t"),
    @NamedQuery(name="Topics.findByUser",
    			query="SELECT t FROM Topics t WHERE t.userId = :userId"),
    @NamedQuery(name="Topics.findByUserAndCatagory",
				query="SELECT t FROM Topics t WHERE t.userId = :userId and t.catagoryId = :catagoryId"),
    @NamedQuery(name="Topics.findByHashtag",
				query="SELECT t FROM Topics t WHERE t.hashtagId = :hashtagId"),
    @NamedQuery(name="Topics.findByCatagory",
				query="SELECT t FROM Topics t WHERE t.catagoryId = :catagoryId"),
    })
public class Topics {

	@Id
	@Column(name = "TOPICS_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "USER_ID")
	private int userId;
	
	@Field(store = Store.YES)
	@NotNull
	@Column(name = "TOPICS_ARTICLE")
	private String topicsArticle;
	
	@Column(name = "LIKE_COUNT")
	private int likeCount;
	
	@Column(name = "FAVORITE_COUNT")
	private int favoriteCount;
	
	@Column(name = "FAVORITE_USER_ID")
	private String favoriteUserId;
	
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
	
	@Column(name = "TOPICS_TYPE")
	private int topicsType;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "COMMENTS_COUNT")
	private int commentsCount;
	
	@Column(name = "HASHTAG")
	private int hashtagId;
	
	@Field(store = Store.YES)
	@Column(name = "KEYWORD")
	private String keyword;
	
	@Column(name = "USER_DISPLAY_NAME")
	private String userDisplayName;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
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
	 * @return the topicsArticle
	 */
	public String getTopicsArticle() {
		return topicsArticle;
	}

	/**
	 * @param topicsArticle the topicsArticle to set
	 */
	public void setTopicsArticle(String topicsArticle) {
		this.topicsArticle = topicsArticle;
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
	 * @return the topicsType
	 */
	public int getTopicsType() {
		return topicsType;
	}

	/**
	 * @param topicsType the topicsType to set
	 */
	public void setTopicsType(int topicsType) {
		this.topicsType = topicsType;
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
	 * @return the commentsCount
	 */
	public int getCommentsCount() {
		return commentsCount;
	}

	/**
	 * @param commentsCount the commentsCount to set
	 */
	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	/**
	 * @return the hashtag
	 */
	public int getHashtagId() {
		return hashtagId;
	}

	/**
	 * @param hashtag the hashtag to set
	 */
	public void setHashtag(int hashtagId) {
		this.hashtagId = hashtagId;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public Topics(int userId, @NotNull String topicsArticle, int catagoryId, String userDisplayName) {
		super();
		this.userId = userId;
		this.topicsArticle = topicsArticle;
		this.catagoryId = catagoryId;
		this.userDisplayName = userDisplayName;
	}

	public Topics() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the favoriteCount
	 */
	public int getFavoriteCount() {
		return favoriteCount;
	}

	/**
	 * @param favoriteCount the favoriteCount to set
	 */
	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	/**
	 * @return the favoriteUserId
	 */
	public String getFavoriteUserId() {
		return favoriteUserId;
	}

	/**
	 * @param favoriteUserId the favoriteUserId to set
	 */
	public void setFavoriteUserId(String favoriteUserId) {
		this.favoriteUserId = favoriteUserId;
	}

	/**
	 * @param hashtagId the hashtagId to set
	 */
	public void setHashtagId(int hashtagId) {
		this.hashtagId = hashtagId;
	}
	
	@PrePersist
    void preInsert() {
       PrePersistUtil.pre(this);
    }
}
