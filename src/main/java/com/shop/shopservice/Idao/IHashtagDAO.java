package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Hashtag;
import com.shop.shopservice.entity.Topics;

public interface IHashtagDAO {

	public List<Hashtag> listHashtag();

	public Hashtag getHashtag(int id);

	public void addHashtag(Hashtag hashtag);

	public void updateHashtag(Hashtag hashtag);

	public boolean deleteHashtag(int id);

	public List<Hashtag> findHashtagByCatagory(int catagory);

}
