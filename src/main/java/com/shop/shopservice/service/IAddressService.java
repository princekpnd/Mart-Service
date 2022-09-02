package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.Address;

public interface IAddressService {

	List<Address> getAllAddress();

	public List<Address> getAddressByShopId(String shopId);

	public List<Address> getAllAddressByUserId(String userId);

	public List<Address> getAllAddressByShopId(String shopId);

	public List<Address> getAddressByUserIdAndShopId(String userId, String shopId);
	
	
	public Address getDefaultAddress(String userId);

	public boolean addressExists(String userId);

	public boolean createAddress(Address address);

	public Address getAddress(int id);

	public boolean updateAddress(Address address);

	public boolean deleteAddress(int id);

}
