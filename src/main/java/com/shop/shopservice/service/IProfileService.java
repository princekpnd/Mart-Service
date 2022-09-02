package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Profile;

public interface IProfileService {

	public List<Profile> getAllProfle(int count);

	public List<Profile> getAllProfleOnCatagory(int catagory);

	public List<Profile> getAllProfleOnSubCatagory(List<Integer> subCatagoryList);

	public void createProfile(Profile profile);

	public void updateProfile(Profile profile);

	public Profile getProfileById(int profileId);

	public Profile getProfileByUserId(int userId);

	public Profile getProfileByTwitterHandle(String twitterHandle);

	public List<Profile> findProfileOnAvailibility(int availability, int count);
}
