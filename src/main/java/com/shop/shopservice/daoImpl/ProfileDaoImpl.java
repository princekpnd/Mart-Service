package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IProfileDAO;
import com.shop.shopservice.entity.Profile;

@Repository
@Transactional
public class ProfileDaoImpl implements IProfileDAO{

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public List<Profile> getAllProfle(int count) {
		List<Profile> profileList= entityManager.createNamedQuery("Profile.findAll",Profile.class).setMaxResults(count).getResultList();
		return profileList;
	}

	@Override
	public List<Profile> getAllProfleOnCatagory(int catagory) {
		List<Profile> profileList= entityManager.createNamedQuery("Profile.findByCatagory",Profile.class).setParameter("catagoryId", catagory).setMaxResults(50).getResultList();
		return profileList;	
		}

	@Override
	public List<Profile> getAllProfleOnSubCatagory(List<Integer> subCatagoryList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProfile(Profile profile) {
		entityManager.persist(profile);
	}

	@Override
	public void updateProfile(Profile profile) {
		entityManager.merge(profile);
		
	}

	@Override
	public Profile getProfileById(int profileId) {
		return this.entityManager.find(Profile.class, profileId);
		}

	@Override
	public Profile getProfileByUserId(int userId) {
		Profile profile = null;
		try { 
		profile = (Profile) entityManager.createNamedQuery("Profile.findByUserProfile",Profile.class).setParameter("userId", userId).getSingleResult();
		} catch(NoResultException nre) {}
		return profile;
	}

	@Override
	public Profile getProfileByTwitterHandle(String twitterHandle) {
		Profile profile = null;
		try { 
		profile = (Profile) entityManager.createNamedQuery("Profile.findUserByTwitterId",Profile.class).setParameter("twitterHandle", twitterHandle).getSingleResult();
		} catch(NoResultException nre) {}
		return profile;
	}

	public List<Profile> findProfileOnAvailibility(int availability, int count){
		List<Profile> profileList= entityManager.createNamedQuery("Profile.findProfileOnAvailibility",Profile.class).setParameter("availability", availability).setMaxResults(count).getResultList();
		return profileList;	
	}
}
