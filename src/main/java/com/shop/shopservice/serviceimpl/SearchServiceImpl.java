package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ISearchDAO;
import com.shop.shopservice.entity.Search;
import com.shop.shopservice.service.ISearchService;
import com.shop.shopservice.utils.SearchRepository;

@Repository
@Transactional
public class SearchServiceImpl implements ISearchService{
	@Autowired
	ISearchDAO searchDao;
	
	@Autowired
	SearchRepository searchRepository;

	@Override
	public List<Search> getAll() {
		return searchDao.getAll();
	}

	@Override
	public Search getById(int id) {
		return searchDao.getById(id);
	}

	@Override
	public boolean createSearch(Search search) {
		searchDao.createSearch(search);
		search = null;
		return true;
	}

	@Override
	public boolean truncateTable() {
		searchDao.truncateTable();
		return false;
	}

	@Override
	@Transactional
	public void truncateSearchKeyword() {
		searchRepository.truncateSearchKeyword();		
	}

}
