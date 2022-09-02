package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Address;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.UserAddress;

public interface IUserAddressDAO {
	List<UserAddress> getAllUserAddress();

	public List<UserAddress> getAddressByUserId(String userId);

	public List<UserAddress> getAddressByShopId(String shopId);

	boolean userAddressExists(String userId);

	void addUserAddress(UserAddress userAddress);

	void updateUserAddress(UserAddress userAddress);

	public UserAddress getUserAddress(String userId);

	public void indexUserAddress();

	public List<UserAddress> searchUserAddress(String keyword);

}
