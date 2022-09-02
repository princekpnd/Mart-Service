package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IProfileDAO;
import com.shop.shopservice.entity.Profile;
import com.shop.shopservice.service.IProfileService;
@Transactional
@Repository
public class ProfileServiceImpl implements IProfileService {

	@Autowired
	IProfileDAO profileDao; 
	@Override
	public List<Profile> getAllProfle(int count) {
		return profileDao.getAllProfle(count);
	}

	@Override
	public List<Profile> getAllProfleOnCatagory(int catagory) {
		return profileDao.getAllProfleOnCatagory(catagory);
	}

	@Override
	public List<Profile> getAllProfleOnSubCatagory(List<Integer> subCatagoryList) {
		return profileDao.getAllProfleOnSubCatagory(subCatagoryList);
		}

	@Override
	public void createProfile(Profile profile) {
		profileDao.createProfile(profile);
	}

	@Override
	public void updateProfile(Profile profile) {
		profileDao.updateProfile(profile);
	}

	@Override
	public Profile getProfileById(int profileId) {
		return profileDao.getProfileById(profileId);
	}

	@Override
	public Profile getProfileByUserId(int userId) {
		return profileDao.getProfileByUserId(userId);
		}

	@Override
	public Profile getProfileByTwitterHandle(String twitterHandle) {
		return profileDao.getProfileByTwitterHandle(twitterHandle);
	}

	@Override
	public List<Profile> findProfileOnAvailibility(int availability, int count) {
		return profileDao.findProfileOnAvailibility(availability, count);
	}

}
