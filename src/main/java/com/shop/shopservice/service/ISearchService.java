package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Search;

public interface ISearchService {
	
	List<Search> getAll();
	
	public Search getById(int id);
	
	boolean createSearch(Search search);
	
	boolean truncateTable();
	
	void truncateSearchKeyword();

}
