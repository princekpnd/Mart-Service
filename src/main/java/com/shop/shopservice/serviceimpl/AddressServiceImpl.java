package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IAddressDAO;
import com.shop.shopservice.entity.Address;
import com.shop.shopservice.service.IAddressService;

@Transactional
@Repository
public class AddressServiceImpl  implements IAddressService{
	

	@Autowired
	private IAddressDAO addressDao;

	@Override
	public List<Address> getAllAddress() {
		return addressDao.getAllAddress() ;
	}

	@Override
	public List<Address> getAddressByShopId(String shopId) {
		return addressDao.getAddressByShopId(shopId);
	}

	@Override
	public boolean addressExists(String userId) {
		return addressDao.addressExists(userId) ;
	}
	
	public boolean createAddress(Address address) {		
			addressDao.addAddress(address);			
			return true;		
	}

	@Override
	public Address getAddress(int id) {
		return addressDao.getAddressById(id) ;
	}

	@Override
	public boolean updateAddress(Address address) {
		addressDao.updateAddress(address);
		return true;
	}

	@Override
	public List<Address> getAllAddressByUserId(String userId) {
		return addressDao.getAllAddressByUserId(userId);
	}

	@Override
	public List<Address> getAllAddressByShopId(String shopId) {
		return addressDao.getAllAddressByShopId(shopId);
	}

	@Override
	public List<Address> getAddressByUserIdAndShopId(String userId, String shopId) {
		return addressDao.getAddressByUserIdAndShopId(userId,shopId);
	}

	@Override
	public Address getDefaultAddress(String userId) {
		return addressDao.getDefaultAddress(userId);
	}

	@Override
	public boolean deleteAddress(int id) {
		return addressDao.deleteAddress(id);
	}




}
