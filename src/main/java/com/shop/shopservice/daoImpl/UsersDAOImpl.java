package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.shopservice.Idao.IUsersDAO;
import com.shop.shopservice.entity.Address;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.entity.UserDeviceID;
import com.shop.shopservice.service.IAddressService;

@Repository
@Transactional
public class UsersDAOImpl implements IUsersDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IAddressService addressService;

	@Override
	public List<User> getAllUsers(int count) {
		List<User> profileList = entityManager.createNamedQuery("User.findAll", User.class).setMaxResults(count)
				.getResultList();
		return profileList;
	}

	@Override
	public User getUsersById(int userId) {
		return this.entityManager.find(User.class, userId);
	}

	@Override
	public User getUserByOtp(String mobileNo, String otp) {
		return this.entityManager.createNamedQuery("User.findByOtp", User.class).setParameter("mobileNo", mobileNo)
				.setParameter("otp", otp).getSingleResult();
	}

	@Override
	public void addUser(User user) {
		entityManager.persist(user);

	}

	@Override
	public void updateUsers(User user) {
		entityManager.merge(user);
	}

	@Override
	public void deleteUsers(int userId) {
		entityManager.find(User.class, userId).setIsActive(Boolean.FALSE);
		entityManager.flush();

	}

	@Override
	public boolean usersExists(String emailId) {
		User user = entityManager.createNamedQuery("User.findByMobile", User.class).setParameter("emailId", emailId)
				.getResultList().stream().findFirst().orElse(null);
		;
		return null != user ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public User sendOtp(String mobileNo) {
		return (User) entityManager.createNamedQuery("User.findOtpByMobileNumber", User.class)
				.setParameter("mobileNo", mobileNo).getSingleResult();

	}

	@Override
	public User validateUserByPwd(String email, String pwd) {
		User user = null;
//		try {
		user = (User) entityManager.createNamedQuery("User.validatePwd", User.class).setParameter("email", email)
				.setParameter("pwd", pwd).getResultList().stream().findFirst().orElse(null);
//		} catch (NoResultException nre) {
//			nre.printStackTrace();
//		}
		return null != user ? user : null;
	}

	@Override
	public User validateUserByDeviceId(String deviceId) {
		User ud = null;
		try {
			ud = (User) entityManager.createNamedQuery("UserDeviceID.fetchUserId", User.class)
					.setParameter("deviceId", deviceId).getSingleResult();
		} catch (NoResultException nre) {
		}
		return null != ud ? ud : null;
	}

	@Override
	public User getUsersByEmailId(String emailId) {
		User user = entityManager.createNamedQuery("User.findByEmail", User.class).setParameter("emailId", emailId)
				.getResultList().stream().findFirst().orElse(null);
		return null != user ? user : null;

	}

//	@Override
//	public int viewWalletBalance(int userId) {
//		return entityManager.createNamedQuery("User.viewWalletBalance", Integer.class).setParameter("userId", userId).getSingleResult();
//	}

//	public boolean updateWalletBalance(int userId, int amount) {
//		User user = getUsersById(userId);
//		int amt = user.getWalletBalance();
//		if(amount > 0) {
//			amt = amt + amount;		
//		} else if (amount < 0){
//			if (amt > amount)
//			amt = amt + amount;
//			else {
//				return false;
//			}
//		}
//		user.setWalletBalance(amt);
//		updateUsers(user);
//		return true;
//	}

//	@Override
//	public User validateAdminUserByPwd(String email, String pwd) {
//		User user = null;
//		try {
//			user = (User) entityManager.createNamedQuery("User.validateAdminPwd", User.class)
//					.setParameter("email", email).setParameter("pwd", pwd).getSingleResult();
//		} catch (NoResultException nre) {
//			nre.printStackTrace();
//		}
//		return user;
//	}

	/*
	 * public void followConsultantByUser(User consultant, Integer userId) { if
	 * (null != consultant && null != userId) { String followedBy =
	 * consultant.getFollowedBy(); if (!Strings.isBlank(followedBy) &&
	 * !followedBy.contains(Integer.toString(userId))) { followedBy = followedBy +
	 * "," + Integer.toString(userId);
	 * consultant.setFollowCount(consultant.getFollowCount() + 1); } else if
	 * (!Strings.isBlank(followedBy) &&
	 * followedBy.contains(Integer.toString(userId))) {
	 * 
	 * followedBy = followedBy.startsWith(Integer.toString(userId)) &&
	 * followedBy.contains(",") ? followedBy.replace(Integer.toString(userId) + ",",
	 * Strings.EMPTY) : followedBy.replace("," + Integer.toString(userId),
	 * Strings.EMPTY); if (followedBy.startsWith(Integer.toString(userId)) &&
	 * !followedBy.contains(",") ) followedBy=
	 * followedBy.replace(Integer.toString(userId), Strings.EMPTY);
	 * 
	 * consultant.setFollowCount(consultant.getFollowCount() - 1); } else if
	 * (Strings.isBlank(followedBy)) { followedBy = Integer.toString(userId);
	 * consultant.setFollowCount(consultant.getFollowCount() == 0 ? 1 :
	 * consultant.getFollowCount() + 1); } consultant.setFollowedBy(followedBy);
	 * entityManager.persist(consultant); consultant = null; } }
	 */

	@Override
	public void deleteUserDevice(UserDeviceID ud) {
		Query query = entityManager.createQuery("delete UserDeviceID where id = " + ud.getId());
		query.executeUpdate();
		// entityManager.createNamedQuery("UserDeviceID.updateDeviceId",UserDeviceID.class).setParameter("deviceId",
		// ud.getDeviceId()+"DEL").setParameter("id", ud.getId()).executeUpdate();
		entityManager.flush();
	}

//	@Override
//	public void addWishListByProductId(User user, String productId) {		
//		if (null != user) {
//			String wishList = user.getWishList();
//			int wishCount = user.getWishCount();
//			if (!Strings.isBlank(wishList) && !wishList.contains(productId)) {
//				wishList = wishList + "," + productId;
//				user.setWishCount(wishCount + 1);
//			} else if (!Strings.isBlank(wishList) && wishList.contains(productId)) {
//				wishList = wishList.startsWith(productId) && wishList.contains(",")
//						? wishList.replace(productId + ",", Strings.EMPTY)
//						: wishList.replace("," + productId, Strings.EMPTY);
//				if (wishList.startsWith(productId) && !wishList.contains(","))
//					wishList = wishList.replace(productId, Strings.EMPTY);
//
//				user.setWishCount(wishCount - 1);
//			} else if (Strings.isBlank(wishList)) {
//				wishList = productId;
//				user.setWishCount(wishCount == 0 ? 1 : wishCount + 1);
//			}
//			user.setWishList(wishList);
//			entityManager.persist(user);
//			user = null;
//		}
//
//	}
	@Override
	public void addWishListByProductId(User user, String productId) {
		if (null != user) {
			String wishList = user.getWishList();
			String[] wishlistArray = {};

			boolean find = false;
			int index = 0;
			String wishLisListFinal = null;
			int wishCount = user.getWishCount();
			if (!Strings.isBlank(wishList)) {
				wishlistArray = wishList.split(",");
				for (int i = 0; i < wishlistArray.length; i++) {
					if (wishlistArray[i].equals(productId)) {
						index = i;
						find = Boolean.TRUE;
					}
				}
			}
			if (find) {
				wishlistArray = (String[]) ArrayUtils.remove(wishlistArray, index);
				user.setWishCount(wishCount - 1);
				wishLisListFinal = String.join(",", wishlistArray);
			} else {
				wishlistArray = (String[]) ArrayUtils.add(wishlistArray, productId);
				user.setWishCount(wishCount + 1);
				wishLisListFinal = String.join(",", wishlistArray);
			}

			user.setWishList(wishLisListFinal);
			entityManager.persist(user);
			user = null;
		}
	}

	@Override
	public List<User> getUserByUserIdAndShopId(int userId, String shopId) {
		List<User> userList = entityManager.createNamedQuery("User.findByUserIdAndShopId", User.class)
				.setParameter("userId", userId).setParameter("shopId", shopId).getResultList();
		return userList;
	}

	@Override
	public boolean userExistByEmail(String emailId) {
		User user = entityManager.createNamedQuery("User.findByEmail", User.class).setParameter("emailId", emailId)
				.getResultList().stream().findFirst().orElse(null);
		;
		return null != user ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public boolean deleteUser(int userId) {
		List<Address> addressList = addressService.getAllAddressByUserId(String.valueOf(userId));
		if (addressList.size() > 0) {
			for (int i = 0; i < addressList.size(); i++) {
				int addressId = addressList.get(0).getId();
				addressService.deleteAddress(addressId);
			}
			Query query = entityManager.createQuery("delete User where userId = " + userId);
			query.executeUpdate();
			entityManager.flush();
			return true;
		}
		Query query = entityManager.createQuery("delete User where userId = " + userId);
		query.executeUpdate();
		entityManager.flush();
		return true;
	}

	public void indexUser() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the serach index: " + e.toString());
		}
	}

	@Override
	public List<User> searchUser(String keyword) {
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(User.class)
				.get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("firstName", "firstName")
				.matching(keyword).createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, User.class);

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<User> results = (List<User>) jpaQuery.getResultList();

		return results;
	}

	@Override
	public User getUserByMobileNumber(String mobileNo) {
		User user = entityManager.createNamedQuery("User.findByMobileNumber", User.class)
				.setParameter("mobileNo", mobileNo).getSingleResult();
		return user;
	}

	@Override
	public List<User> getUserByShopId(String shopId) {
		List<User> userList = entityManager.createNamedQuery("User.findByShopId", User.class)
				.setParameter("shopId", shopId).getResultList();
		return userList;
	}

	@Override
	public User getUserByOtpAndEmailId(String otp, String emailId) {
		User user = entityManager.createNamedQuery("User.getByOtpAndEmail", User.class).setParameter("otp", otp)
				.setParameter("emailId", emailId).getResultList().stream().findFirst().orElse(null);
		return user;
	}

	@Override
	public boolean resendOtp(String emailId) {
		User user = entityManager.createNamedQuery("User.getByOtpAndResendOtp", User.class)
				.setParameter("emailId", emailId).getResultList().stream().findFirst().orElse(null);
		return null != user ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public boolean checkExitByEmailId(String emailId, String mobileNo) {
		User user = entityManager.createNamedQuery("User.checkExitByEmailId", User.class)
				.setParameter("emailId", emailId).setParameter("mobileNo", mobileNo).getResultList().stream()
				.findFirst().orElse(null);
		return null != user ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public User getByRefralNumber(String refralNumber) {
		User user = entityManager.createNamedQuery("User.getByRefralNumber", User.class).setParameter("refralNumber", refralNumber).getResultList().stream().findFirst().orElse(null);
		return user;
		
	}

	@Override
	public User getActiveUserByMobileNumber(String mobileNo) {
		User user = entityManager.createNamedQuery("User.getByUserActiveByMobileNumber", User.class).setParameter("mobileNo", mobileNo).getResultList().stream().findFirst().orElse(null);
		return user;
	}

	@Override
	public boolean checkByRefralNumber(String mobileNo) {
		User user = entityManager.createNamedQuery("User.findByRefralNumber", User.class).setParameter("mobileNo", mobileNo).getResultList().stream().findFirst().orElse(null);
		return null != user ? Boolean.TRUE : Boolean.FALSE;
	}

}
