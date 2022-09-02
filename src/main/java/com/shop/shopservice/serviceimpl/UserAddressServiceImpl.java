package com.shop.shopservice.serviceimpl;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IUserAddressDAO;
import com.shop.shopservice.entity.UserAddress;
import com.shop.shopservice.service.IUserAddressService;
@Transactional
@Repository
public class UserAddressServiceImpl implements IUserAddressService{
	@Autowired
	IUserAddressDAO  userAddressDao;

	@Override
	public List<UserAddress> getAllUserAddress() {
		return  userAddressDao.getAllUserAddress();
	}

	@Override
	public List<UserAddress> getAddressByUserId(String userId) {
		return userAddressDao.getAddressByUserId(userId);
	}

	@Override
	public List<UserAddress> getAddressByShopId(String shopId) {
		return userAddressDao.getAddressByShopId(shopId);
	}

	@Override
	public boolean userAddressExists(String userId) {
		return userAddressDao.userAddressExists(userId);
	}

	@Override
	public boolean createUserAddress(UserAddress userAddress) {
		

			if (userAddressExists(userAddress.getUserId())) {
				return false;
			} else {

				userAddressDao.addUserAddress(userAddress);
				userAddress = null;
				return true;
			}
		}

	@Override
	public boolean updateUserAddress(UserAddress userAddress) {
		userAddressDao.updateUserAddress(userAddress);
		return true;
	}

	@Override
	public UserAddress getUserAddress(String userId) {
		return userAddressDao.getUserAddress(userId);
	}
	}


