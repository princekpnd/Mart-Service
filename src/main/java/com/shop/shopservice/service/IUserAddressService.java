package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.UserAddress;

public interface IUserAddressService {
	List<UserAddress> getAllUserAddress();

	public List<UserAddress> getAddressByUserId(String userId);

	public List<UserAddress> getAddressByShopId(String shopId);

	public boolean userAddressExists(String userId);

	public boolean createUserAddress(UserAddress userAddress);

	public boolean updateUserAddress(UserAddress userAddress);

	public UserAddress getUserAddress(String userId);

}
