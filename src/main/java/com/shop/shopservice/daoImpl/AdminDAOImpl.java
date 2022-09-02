package com.shop.shopservice.daoImpl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IAdminDAO;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminDeviceID;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.UserAddress;
import com.shop.shopservice.service.IUserAddressService;

@Repository
@Transactional
public class AdminDAOImpl implements IAdminDAO {

	@Autowired
	IUserAddressService userAddressService;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Admin> getAllAdmin() {
		List<Admin> adminList = entityManager.createNamedQuery("Admin.findAll", Admin.class).getResultList();
		return adminList;
	}
	
	public int resutl() {
		int admin = entityManager.createNamedQuery("Admin.findAll", Admin.class).getMaxResults();
		return admin;
	}

	@Override
	public Admin getAdminById(int adminId) {
		return this.entityManager.find(Admin.class, adminId);
	}

	@Override
	public Admin getAdminByEmailId(String emailId) {
		return (Admin) entityManager.createNamedQuery("Admin.findByEmail", Admin.class).setParameter("emailId", emailId)
				.getSingleResult();
	}

	@Override
	public Admin getAdminByShopId(String shopId) {
		Admin admin = entityManager.createNamedQuery("Admin.findByShop", Admin.class).setParameter("shopId", shopId)
				.getResultList().stream().findFirst().orElse(null);
		return null != admin ? admin : null;
	}

	@Override
	public List<Admin> getAdminByFirstName(String firstName) {
		List<Admin> adminList = entityManager.createNamedQuery("Admin.findByFirstName", Admin.class)
				.setParameter("firstName", firstName).getResultList();
		return adminList;
	}

	@Override
	public List<Admin> getAdminByAdharNumber(int adharNumber) {
		return this.entityManager.createNamedQuery("Admin.findAdminByAdharNumber", Admin.class)
				.setParameter("adharNumber", adharNumber).getResultList();
	}

	@Override
	public void updateAdmin(Admin admin) {
		entityManager.merge(admin);
	}

	@Override
	public Admin validateAdminByPwd(String emailId, String pwd) {
		Admin admin = null;
		try {
			admin = (Admin) entityManager.createNamedQuery("Admin.validatePwd", Admin.class)
					.setParameter("emailId", emailId).setParameter("pwd", pwd).getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
		return admin;
	}

	@Override
	public Admin validateAdminByDeviceId(String deviceId) {
		Admin ad = null;
		try {
			ad = (Admin) entityManager.createNamedQuery("AdminDeviceID.fetchAdminId", Admin.class)
					.setParameter("deviceId", deviceId).getSingleResult();
		} catch (NoResultException nre) {
		}
		return null != ad ? ad : null;
	}

	@Override
	public void deleteAdminDevice(AdminDeviceID ad) {
		Query query = entityManager.createQuery("delete AdminDeviceID where id = " + ad.getId());
		query.executeUpdate();
		// entityManager.createNamedQuery("UserDeviceID.updateDeviceId",UserDeviceID.class).setParameter("deviceId",
		// ud.getDeviceId()+"DEL").setParameter("id", ud.getId()).executeUpdate();
		entityManager.flush();
	}

	@Override
	public boolean adminExists(String mobileNo) {
		Admin admin = entityManager.createNamedQuery("Admin.findByShopId", Admin.class)
				.setParameter("mobileNo", mobileNo).getResultList().stream().findFirst().orElse(null);
		return null != admin ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addAdmin(Admin admin) {
		entityManager.persist(admin);
	}

	@Override
	public List<Admin> getAdminByAdminIdAndShopId(int adminId, String shopId) {
		List<Admin> adminList = entityManager.createNamedQuery("Admin.findAdminByProductIdAndShopId", Admin.class)
				.setParameter("adminId", adminId).setParameter("shopId", shopId).getResultList();
		return adminList;
	}

	@Override
	public Admin getByMobileNumber(String mobileNo) {
		Admin admin = entityManager.createNamedQuery("Admin.findByMobileNo", Admin.class)
				.setParameter("mobileNo", mobileNo).getSingleResult();
		return admin;
	}

	@Override
	public List<Admin> getByShopType(int shopType) {
		List<Admin> adminList = entityManager.createNamedQuery("Admin.findByShopType", Admin.class)
				.setParameter("shopType", shopType).getResultList();
		return adminList;
	}

	public void indexAdmin() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the serach index: " + e.toString());
		}
	}

	@Override
	public List<Admin> searchAdmin(String keyword) {
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Admin.class)
				.get();

		QueryBuilder queryBuilder1 = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(UserAddress.class).get();

		QueryBuilder queryBuilder2 = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(LookUp.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query queryShop = queryBuilder.keyword().onFields("firstName", "shopName")
				.matching(keyword).createQuery();

		org.apache.lucene.search.Query queryCity = queryBuilder1.keyword().onFields("city").matching(keyword)
				.createQuery();

		org.apache.lucene.search.Query queryShopType = queryBuilder2.keyword().onFields("lookUpName").matching(keyword)
				.createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQueryShop = fullTextEntityManager.createFullTextQuery(queryShop,
				Admin.class);

		org.hibernate.search.jpa.FullTextQuery jpaQueryCity = fullTextEntityManager.createFullTextQuery(queryCity,
				UserAddress.class);

		org.hibernate.search.jpa.FullTextQuery jpaQueryShopType = fullTextEntityManager
				.createFullTextQuery(queryShopType, LookUp.class);

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<Admin> shop = (List<Admin>) jpaQueryShop.getResultList();

		@SuppressWarnings("unchecked")
		List<UserAddress> city = (List<UserAddress>) jpaQueryCity.getResultList();

		@SuppressWarnings("unchecked")
		List<LookUp> shopType = (List<LookUp>) jpaQueryShopType.getResultList();

		List<Admin> result = new ArrayList<Admin>();

		if (shop.size() > 0 || city.size() > 0 || shopType.size() > 0) {

			if (shop.size() > 0) {
				for (int i = 0; i < shop.size(); i++) {
					List<UserAddress> userAddress = userAddressService.getAddressByShopId(shop.get(i).getShopId());
					Admin tempAdmin = getAdminByShopId(shop.get(i).getShopId());
					tempAdmin.setAdminAddress(userAddress);
					result.add(tempAdmin);
				}
			}

			if (city.size() > 0) {
				for (int i = 0; i < city.size(); i++) {
					Admin tempAdmin = getAdminByShopId(city.get(i).getShopId());
					if (tempAdmin != null) {
						List<UserAddress> userAddress = userAddressService.getAddressByShopId(city.get(i).getShopId());
						if (userAddress.size() > 0) {
							tempAdmin.setAdminAddress(userAddress);
							result.add(tempAdmin);
						}
					}
				}
			}

			if (shopType.size() > 0) {
				int shopTypeId = shopType.get(0).getLookUpId();
				List<Admin> adminList = getByShopType(shopTypeId);
				for (int i = 0; i < adminList.size(); i++) {
					Admin tempAdmin = getAdminByShopId(adminList.get(i).getShopId());
					List<UserAddress> userAddress = userAddressService.getAddressByShopId(adminList.get(i).getShopId());
					tempAdmin.setAdminAddress(userAddress);
					result.add(tempAdmin);
				}
			}

			if (result.size() > 1) {
				for (int i = 0; i < result.size(); i++) {
					for (int j = i + 1; j < result.size(); j++) {
						if (result.get(i).getAdminId() == result.get(j).getAdminId()) {
							result.remove(j);
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public Admin getByShopId(String shopId) {
		Admin admin = entityManager.createNamedQuery("Admin.findAdminByShopId", Admin.class)
				.setParameter("shopId", shopId).getSingleResult();
		return admin;
	}

	@Override
	public Admin getDeactiveAdmin(String emailId, String pwd) {
		Admin admin = null;
		try {
			admin = (Admin) entityManager.createNamedQuery("Admin.validateDeactiveAdmin", Admin.class)
					.setParameter("emailId", emailId).setParameter("pwd", pwd).getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
		return admin;
	}

	@Override
	public boolean checkAdminByEmailId(String emailId) {
		Admin admin = entityManager.createNamedQuery("Admin.checkByEmail", Admin.class).setParameter("emailId", emailId)
				.getResultList().stream().findFirst().orElse(null);
		return null != admin ? true : false;
	}

	@Override
	public boolean checkDeactiveAdminByEmailId(String emailId) {
		Admin admin = entityManager.createNamedQuery("Admin.checkDeactiveByEmail",Admin.class).setParameter("emailId", emailId)
				.getResultList().stream().findFirst().orElse(null);
		return null != admin ? true : false;
	}

	@Override
	public boolean checkDeactiveAdminByAdminId(int adminId, int registrationStatus) {
		Admin admin = entityManager.createNamedQuery("Admin.checkDeactiveByAdminId",Admin.class).setParameter("adminId", adminId).setParameter("registrationStatus", registrationStatus)
				.getResultList().stream().findFirst().orElse(null);
		return null != admin ? true : false;
	}
	
	@Override
	public boolean checkActiveAdminByShopId(String shopId) {
		Admin admin = entityManager.createNamedQuery("Admin.findActiveAdmin",Admin.class).setParameter("shopId", shopId)
				.getResultList().stream().findFirst().orElse(null);
		return null != admin ? true : false;
	}

	@Override
	public List<Admin> getActiveAdminByShopId(String shopId) {
		List<Admin> adminList = entityManager.createNamedQuery("Admin.findActiveAdmin",Admin.class).setParameter("shopId", shopId).getResultList();
		return adminList;
	}

	@Override
	public List<Admin> getAllActiveAdmin() {
		List<Admin> adminList = entityManager.createNamedQuery("Admin.findAllActiveAdmin",Admin.class).getResultList();
		return adminList;
	}

	@Override
	public boolean adminAclive(String shopId) {
		Admin admin = entityManager.createNamedQuery("Admin.adminActive",Admin.class).setParameter("shopId", shopId).getResultList().stream().findFirst().orElse(null);
		return null !=admin ? true :false;
	}

	@Override
	public int getResult() {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public int getResult() {
//		TypedQuery<Admin> admin = entityManager.createNamedQuery("Admin.findAll", Admin.class).setMaxResults(int 4);
//		return admin;
//	}

	

//	@Override
//	public List<Admin> searchAdminByShopId(String shopId, String keyword) {
//		// get the full text entity manager
//				FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
//						.getFullTextEntityManager(entityManager);
//
//				// create the query using Hibernate Search query DSL
//				QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Admin.class)
//						.get();
//
//				// a very basic query by keywords
//				org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("shopName").matching(keyword)
//						.createQuery();
//
//				// wrap Lucene query in an Hibernate Query object
//				org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Admin.class);
//
//				// execute search and return results (sorted by relevance as default)
//				@SuppressWarnings("unchecked")
//				List<Admin> results = (List<Admin>) jpaQuery.getResultList();
//
//				return results;
//			}
}
