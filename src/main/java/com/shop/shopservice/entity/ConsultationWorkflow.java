package com.shop.shopservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Avinash
 *
 */
@Entity
@Table(name = "CONSULTATION_WORKFLOW")
@NamedQueries({
    @NamedQuery(name="ConsultationWorkflow.findAll",
                query="SELECT cw FROM ConsultationWorkflow cw"),
    @NamedQuery(name="ConsultationWorkflow.findByUserIdConsultantId",
    			query="SELECT cw FROM ConsultationWorkflow cw WHERE cw.userId = :userId and cw.consultantId =:consultantId"),
    @NamedQuery(name="ConsultationWorkflow.findByConsultantId",
		query="SELECT cw FROM ConsultationWorkflow cw WHERE cw.consultantId =:consultantId"),
    @NamedQuery(name="ConsultationWorkflow.findByUserIdConsultantIdwithstatus",
    		    query="SELECT cw FROM ConsultationWorkflow cw WHERE cw.userId = :userId and cw.consultantId =:consultantId and cw.accepted =:status"),
}) 
public class ConsultationWorkflow {

	
	@Id
	@Column(name = "CONSULTATION_WORKFLOW_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "USER_ID")
	private int userId;
	
	@Column(name = "CONSULTANT_ID")
	private int consultantId;
	
	

	@Column(name = "ARTICLE_ID")
	private String articleId;
	
	@Column(name = "PRICE")
	private int price;
	
	@Column(name = "STATUS")
	private boolean accepted;
	
	@Column(name = "REASON_FOR_REJECT")
	private String reasonForReject;
	
	@Column(name = "FINISHEDATE")
	private String finishedAt;
	
	@Column(name = "CREATEDATE")
	private String createdAt;

	@Column(name = "RATING")
	private int rating;
	
	@Column(name = "FEEDBACK")
	private String feedback;
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
	 * @return the articleId
	 */
	public String getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	/**
	 * @return the reasonForReject
	 */
	public String getReasonForReject() {
		return reasonForReject;
	}

	/**
	 * @param reasonForReject the reasonForReject to set
	 */
	public void setReasonForReject(String reasonForReject) {
		this.reasonForReject = reasonForReject;
	}

	/**
	 * @return the finishedAt
	 */
	public String getFinishedAt() {
		return finishedAt;
	}

	/**
	 * @param finishedAt the finishedAt to set
	 */
	public void setFinishedAt(String finishedAt) {
		this.finishedAt = finishedAt;
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

	public ConsultationWorkflow(int userId, int consultantId, int price, boolean status) {
		super();
		this.userId = userId;
		this.consultantId = consultantId;
		this.price = price;
		this.accepted = status;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}

	/**
	 * @param feedback the feedback to set
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public ConsultationWorkflow() {
		super();
	}	
}