package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Search;

public interface ISearchDAO {
	
	List<Search> getAll();
	
	Search getById(int id);
	
	void createSearch(Search search);
	
	boolean truncateTable();

}
