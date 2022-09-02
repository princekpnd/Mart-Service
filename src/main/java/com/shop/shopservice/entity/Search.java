package com.shop.shopservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.shop.shopservice.utils.PrePersistUtil;
@Entity
@Table(name ="SEARCH_KEYWORD")
@NamedQueries({ 
	@NamedQuery(name = "Search.getAll",
			query = "SELECT se FROM Search se"),
	@NamedQuery(name ="Search.findById",
			query = "SELECT se FROM Search se WHERE se.id = :id"),
//	@NamedQuery(name ="Search.truncate",
//			 query = "TRUNCATE se SearchKeyword")
})
public class Search  implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "SEARCH_KEY", nullable = false)
	private String searchKey;
	
	@Column(name = "TYPE", nullable = false)
	private String type;
	
	@Column(name = "TYPE_ID", nullable = false)
	private String typeId;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "SUB_CAT_ID", nullable = false)
	private String subCategoryId;
	
	
	public Search() {
	super();
	}

	
	public Search(String type, String searchKey) {
	
		this.searchKey =searchKey;
		this.type =type;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	


	public String getSearchKey() {
		return searchKey;
	}


	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getTypeId() {
		return typeId;
	}


	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSubCategoryId() {
		return subCategoryId;
	}


	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}	
	

	@PrePersist
	void preInsert() {
		PrePersistUtil.pre(this);
	}

}
