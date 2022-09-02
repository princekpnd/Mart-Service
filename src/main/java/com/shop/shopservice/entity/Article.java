package com.shop.shopservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Article")
@NamedQueries({
    @NamedQuery(name="Article.findAll",
                query="SELECT a FROM Article a"),
    @NamedQuery(name="Article.findByType",
    			query="SELECT a FROM Article a WHERE a.articleType = :articleType")
}) 
public class Article {

	
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "ArticleId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Teaser")
	private String teaser;
	
	@Column(name = "Article_Type")
	private int articleType;
	
	@Column(name = "MO_ID")
	private int moId;
	
	@Column(name = "BodyContent")
	private String body;
	
	@Column(name = "Publish_Status")
	private Boolean isPublished;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTeaser() {
		return teaser;
	}

	public void setTeaser(String teaser) {
		this.teaser = teaser;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the articleType
	 */
	public int getArticleType() {
		return articleType;
	}

	/**
	 * @param articleType the articleType to set
	 */
	public void setArticleType(int articleType) {
		this.articleType = articleType;
	}

	/**
	 * @return the moId
	 */
	public int getMoId() {
		return moId;
	}

	/**
	 * @param moId the moId to set
	 */
	public void setMoId(int moId) {
		this.moId = moId;
	}

	public Article(String title, int articleType, int moId) {
		super();
		this.title = title;
		this.articleType = articleType;
		this.moId = moId;
	}

	/**
	 * @return the isPublished
	 */
	public Boolean getIsPublished() {
		return isPublished;
	}

	/**
	 * @param isPublished the isPublished to set
	 */
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}
	
	
}