package com.shop.shopservice.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TimeLineDTO {

	@Id
	@Column(name = "TIMELINE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "USER_ID")
	private int userId;
	
	@Column(name = "CONSULTANT_ID")
	private int consultantId;
	
	@Column(name = "Q_ARTICLE")
    private String qArticle;
	
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
	
}
