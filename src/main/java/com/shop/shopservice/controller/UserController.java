package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.ILookUpType;
import com.shop.shopservice.Idao.IUsersDAO;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Address;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminDeviceID;
import com.shop.shopservice.entity.Cashier;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.MyResponse;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.entity.UserDeviceID;
import com.shop.shopservice.service.ArticleService;
import com.shop.shopservice.service.IAddressService;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.ICashierService;
import com.shop.shopservice.service.IUserService;
import com.shop.shopservice.email.EmailInterface;

/**
 * @author Avinash
 *
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);
	@SuppressWarnings("unused")
	@Autowired
	private Environment environment;

	@SuppressWarnings("unused")
	@Autowired
	private ArticleService articleService;

	@Autowired
	private ICashierService cashierService;

	@Autowired
	private IAddressService addressService;

	@Autowired
	private EmailInterface emailUtils;

	@Autowired
	IUsersDAO userDao;

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IUserService UserService;

	@Autowired
	private ILookUpType lookUpType;

	@Autowired
	private ILookUp lookup;

	@RequestMapping(value = "/hallo/{name}", method = RequestMethod.GET)
	public String sayhi(@PathVariable String name) {
		return "Hallo <h2> " + name + "</h1>";
	}

	@GetMapping("getopt")
	public String getopt() {
		int randomPin = (int) (Math.random() * 9000) + 1000;
		String otp = String.valueOf(randomPin);
		return otp;
	}

	@GetMapping("get/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
		User user = UserService.getUser(userId.intValue());
		List<Address> addressList = new ArrayList<Address>();
		Address address = addressService.getDefaultAddress(String.valueOf(userId));
		if (null != address) {
			addressList.add(address);
			user.setAddress(addressList);
			user.setPwd("");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("send/email")
	ResponseEntity<Map<String, String>> sendMail() {
		Map<String, String> response = new HashMap<String, String>();
		response.put("Data", "remappingFunction");
		emailUtils.sendMailJetEmail("Job Vacncies",
				"Mr. Vishal Shrivastav,\n We are from MILAAN SEARCH IT PROJECTS \nWe are hiring a new React-Native developer.\n If interested please contact us on milaanservices@gmail.com or Our Manager Mr. Prince Kumar on 7739031245 or walk in Interview at \n Inorbit complex, Flat-101, Phulwari Road Khagaul, Near BMP-16, Patna 801505, Bihar.",
				"vkvishaljanu@gmail.com", "Vishal Shrivastav");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("getuser/{shopId}")
	public ResponseEntity<List<User>> getUserByShopId(@PathVariable("shopId") String shopId) {
		List<User> userList = UserService.getUserByShopId(shopId);
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	@GetMapping("getalluser/{count}")
	public ResponseEntity<List<User>> getAlluser(@PathVariable("count") Integer count) {
		List<User> userList = UserService.getAllUsers(count);
		for (int i = 0; i < userList.size(); i++) {
			userList.get(i).setPwd("");
			userList.get(i).setOtp("");
		}
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	@GetMapping("getbyuseridandshopid/{userId}/{shopId}")
	public ResponseEntity<List<User>> getUserByUserIdAndShopId(@PathVariable("userId") String userId,
			@PathVariable("shopId") String shopId) {
		List<User> userList = UserService.getUserByUserIdAndShopId(Integer.parseInt(userId), shopId);
		List<Address> address = addressService.getAddressByUserIdAndShopId(userId, shopId);
		userList.get(0).setAddress(address);
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	/*
	 * @GetMapping("get/userListbyconsultant/{consultantId}") public
	 * ResponseEntity<List<User>>
	 * getUserListForConsultant(@PathVariable("consultantId") Integer consultantId)
	 * { List<ConsultationWorkflow> consultationWorkflowList =
	 * ConsultationWorkflowDAO.getConsultationWorkflowByConsultantId(consultantId);
	 * List<User> userList = new ArrayList<User>(); for(ConsultationWorkflow cw :
	 * consultationWorkflowList) {
	 * userList.add(UserService.getUser(cw.getUserId())); } return new
	 * ResponseEntity<List<User>>(userList, HttpStatus.OK); }
	 */

	@GetMapping("getbyemail/{emailId}")
	public ResponseEntity<User> getUserByEmailId(@PathVariable("emailId") String emailId) {
		User user = UserService.getUsersByEmailId(emailId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("loginuser/{emailId}/{pwd}")
	public ResponseEntity<User> login(@PathVariable("emailId") String emailId, @PathVariable("pwd") String pwd) {
		User user = UserService.loginUser(emailId, pwd);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		userDao.indexUser();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@GetMapping("search/{keyword}")
	public ResponseEntity<List<User>> searchAllUserBykeyword(@PathVariable("keyword") String keyword) {
		List<User> result = new ArrayList<User>();
		result = userDao.searchUser(keyword);
		return new ResponseEntity<List<User>>(result, HttpStatus.OK);
	}

	@GetMapping("getbymobileno/{mobileNo}")
	public ResponseEntity<User> getByMobileNumber(@PathVariable("mobileNo") String mobileNo) {
		User user = UserService.getUserByMobileNumber(mobileNo);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("checkby/refralnumber111/{mobileNo}")
	public ResponseEntity<Map<String, String>> checkByRefralNumber(@PathVariable("mobileNo") String mobileNo){
		Map<String, String> response = new HashMap<String, String>();
		boolean User = UserService.checkByRefralNumber(mobileNo);
		if(User) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "all ready created");
		}else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "User not ready created");
		}
		return ResponseEntity.ok().body(response);
	}
	
	
	@GetMapping("getby/mobileno/{mobileNo}")
	public ResponseEntity<User> getActiveUserByMobileNumber(@PathVariable("mobileNo") String mobileNo) {
		User user = UserService.getActiveUserByMobileNumber(mobileNo);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

//	@GetMapping("getbyuseridandshopid/{userId}/{shopId}")
//	public ResponseEntity<List<User>> getByUserIdAndShopId(@PathVariable("userId") int userId, @PathVariable("shopId") String shopId){
//		List<User> userList = UserService.getByUserIdAndShopId(userId, shopId);
//		
//		return new ResponseEntity<List<User>>(userList , HttpStatus.OK);
//	}

	@GetMapping("wishlist/add/{userId}/{productId}")
	public ResponseEntity<Map<String, String>> addWishListByProductId(@PathVariable("userId") Integer userId,
			@PathVariable("productId") String productId) {
		Map<String, String> response = new HashMap<String, String>();
		User user = UserService.getUser(userId.intValue());

		boolean result = UserService.addWishListByProductId(user, productId);

		response.put("Product Id", productId);
		if (result) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "add wishList");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "not add wishList");
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("check/active/user/{emailId}/{mobileNo}")
	public ResponseEntity<MyResponse> checkExitByEmailIdAndMobile(@PathVariable("emailId") String emailId,
			@PathVariable("mobileNo") String mobileNo) {
		Map<String, String> response = new HashMap<String, String>();
		MyResponse myResponse = new MyResponse();
		boolean user = UserService.checkExitByEmailId(emailId, mobileNo);
		User user1 = UserService.getUserByMobileNumber(mobileNo);
		if (user) {
			response.put("status", Boolean.TRUE.toString());
			myResponse.setStatus("4000");
			myResponse.setData(user1.getShopId());
		} else {
			response.put("status", Boolean.FALSE.toString());
		}
		return ResponseEntity.ok().body(myResponse);
	}

	@GetMapping("wishlisttest")
	public ResponseEntity<Map<String, String>> addWishListByProductIdTest() {
		Map<String, String> response = new HashMap<String, String>();
		// User user = UserService.
		String str2 = "hello,computer,34,55,234,122";
		String[] array = str2.split(",");
		array = (String[]) ArrayUtils.remove(array, 2);
		array = (String[]) ArrayUtils.add(array, "100");
		// array=(String[]) ArrayUtils.
		String str = String.join(" ", array);
		response.put("data", str);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("sendotp/{mobileNo}")
	public ResponseEntity<Map<String, String>> getUserByMobileNumber(@PathVariable("mobileNo") String mobileNo) {
		Map<String, String> response = new HashMap<String, String>();
		if (mobileNo != null) {
			if (mobileNo.contains("@milaan.com")) {
				Admin admin = adminService.getAdminByEmailId(mobileNo);
				if (admin != null) {
					response.put("status", Boolean.TRUE.toString());
					response.put("description", "User exists with given mobile number");
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "User does exist with given mobile number");
				}
			} else {
				if (UserService.usersExists(mobileNo)) {
					response.put("status", Boolean.TRUE.toString());
					response.put("description", "User exists with given mobile number");
				} else {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "User does exist with given mobile number");
				}
			}
		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("validatebyotp/{mobileNo}/{otp}")
	public ResponseEntity<User> getuserByOtp(@PathVariable("mobileNo") String mobileNo,
			@PathVariable("otp") String otp) {
		User user = UserService.getUserByOtp(mobileNo, otp);
		if (null != user) {
			user.setIsActive(Boolean.TRUE);
			user.setDeleted(Boolean.FALSE);
			UserService.updateUser(user);
			user.setPwd("");
			user.setUserDeviceIDList(null);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return null;
		}
	}

	@GetMapping("resend/otp/{emailId}")
	public ResponseEntity<Map<String, String>> resendOtp(@PathVariable("emailId") String emailId) {
		Map<String, String> response = new HashMap<String, String>();
		boolean user = UserService.resendOtp(emailId);
		if (user) {
			User user1 = UserService.getUsersByEmailId(emailId);
			String randomPin = String.valueOf((int) (Math.random() * 9000) + 1000);
			user1.setOtp(randomPin);
			UserService.updateUser(user1);
			emailUtils.sendMailJetEmail("OTP Verification",
					user1.getOtp() + " is your One Time Verification(OTP) code to confirm your email-ID at MILAAN",
					user1.getEmailId(), user1.getFirstName());

			response.put("status", Boolean.TRUE.toString());
			response.put("description", "OTP has been sent to your email-ID");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "OTP has not been sent to your email-ID");
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("get/user/{refralNumber}")
	public ResponseEntity<User> getByRefralNumber(@PathVariable("refralNumber") String refralNumber){
		User user = UserService.getByRefralNumber(refralNumber);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("forget/otp/{emailId}")
	public ResponseEntity<Map<String, String>> forgetPwd(@PathVariable("emailId") String emailId) {
		Map<String, String> response = new HashMap<String, String>();
		boolean user = UserService.resendOtp(emailId);
		if (user) {
			User user1 = UserService.getUsersByEmailId(emailId);
			String pwd = user1.getPwd();
			String pwd2 = new String(new Base64().decode(pwd.getBytes()));
			emailUtils.sendMailJetEmail("OTP Verification",
					"'" + pwd2 + "'" + " is your password to login your MILAAN account", user1.getEmailId(),
					user1.getFirstName());

			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Your password has been sent to your email-ID");
			response.put("user1", Strings.EMPTY);

		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Your password has not been sent to your email-ID");
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("get/otp/varification/{otp}/{emailId}")
	public ResponseEntity<User> getUserByOtpAndEmailId(@PathVariable("otp") String otp,
			@PathVariable("emailId") String emailId) {
		User user = UserService.getUserByOtpAndEmailId(otp, emailId);
		if (user != null) {
			user.setActive(Boolean.TRUE);
			user.setDeleted(Boolean.FALSE);
			UserService.updateUser(user);
			user.setPwd("");
			user.setUserDeviceIDList(null);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return null;
		}

	}

	@GetMapping("validateUserByDeviceId/{deviceId}")
	public ResponseEntity<User> validateUserByDeviceId(@PathVariable("deviceId") String deviceId) {
		User user = UserService.validateUserByDeviceId(deviceId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create/signup")
	ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.MOBILE_NUMBER));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.F_NAME) && null != json.get(ServiceConstants.L_NAME)
				&& null != json.get(ServiceConstants.MOBILE_NUMBER) && null != json.get(ServiceConstants.PWD)
				&& null != json.get(ServiceConstants.EMAIL_ID)) {
			User user = new User(json.get(ServiceConstants.MOBILE_NUMBER), json.get(ServiceConstants.F_NAME));

			/*
			 * user.setPwd(new String(new org.springframework.security.crypto.codec.Base64()
			 * .encode(json.get(ServiceConstants.PWD).getBytes())));
			 */
			user.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));
			if (null != json.get(ServiceConstants.PWD)) {
				user.setPwd(new String(new Base64().encode(json.get(ServiceConstants.PWD).getBytes())));
			}
			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				user.setEmailId(json.get(ServiceConstants.EMAIL_ID));
				String randomPin = String.valueOf((int) (Math.random() * 9000) + 1000);
				user.setOtp(randomPin);

			}
			if (null != json.get(ServiceConstants.REFRAL_NUMBER)) {
				user.setRefralNumber(json.get(ServiceConstants.REFRAL_NUMBER));
			}
			user.setShopId(json.get(ServiceConstants.SHOP_ID));
			user.setFirstName(json.get(ServiceConstants.F_NAME));
			user.setLastName(json.get(ServiceConstants.L_NAME));
			user.setUserType(Integer.parseInt(json.get(ServiceConstants.USER_TYPE)));
			user.setIsActive(Boolean.FALSE);
			user.setDeleted(Boolean.FALSE);
			user.setCreatedOn(new Date());
			user.setLastLoginDate(new Date());
			user.setFirstOrder(Boolean.TRUE);

//			user.setAvatar(json.get(ServiceConstants.AVATAR));
//			user.setDues(Float.parseFloat(json.get(ServiceConstants.DUES)));
//			user.setDuesStatus(Boolean.parseBoolean(json.get(ServiceConstants.DUES_STATUS)));
//			user.setLastPaid(Float.parseFloat(json.get(ServiceConstants.LAST_PAID)));
//			user.setLastPaidOn(new Date());
//
//			user.setOtp(json.get(ServiceConstants.OTP));

			response.put("mobileNum", json.get(ServiceConstants.MOBILE_NUMBER));
			if (UserService.checkExitByEmailId(user.getEmailId(), user.getMobileNo())) {
				String randomPin = String.valueOf((int) (Math.random() * 9000) + 1000);
				user = UserService.getUsersByEmailId(user.getEmailId());
				if(null != user) {
					user.setOtp(randomPin);
					user.setActive(Boolean.FALSE);
					if (null != json.get(ServiceConstants.PWD)) {
						user.setPwd(new String(new Base64().encode(json.get(ServiceConstants.PWD).getBytes())));
					}
					UserService.updateUser(user);

					emailUtils.sendMailJetEmail("OTP Verification",
							user.getOtp() + " is your One Time Verification(OTP) code to confirm your email-ID at MILAAN",
							user.getEmailId(), user.getFirstName());

					String otp = user.getOtp();
				}
				

				response.put("status", Boolean.TRUE.toString());
				response.put("description", "User already exist with given mobile number");
			}

			else {
				boolean result = UserService.createUserSignup(user);
				
				if(null != json.get(ServiceConstants.REFRAL_NUMBER)  && UserService.checkByRefralNumber(json.get(ServiceConstants.REFRAL_NUMBER))) {
					User firstUser = UserService.getActiveUserByMobileNumber(json.get(ServiceConstants.REFRAL_NUMBER));
					firstUser.setWalletBalance(firstUser.getWalletBalance() +10);
					UserService.updateUser(firstUser);
				}
				
				emailUtils.sendMailJetEmail("OTP Verification",
						user.getOtp() + " is your One Time Verification(OTP) code to confirm your email-ID at MILAAN",
						user.getEmailId(), user.getFirstName());
				response.put("status", Strings.EMPTY + result);
				response.put("description",
						"User created successfully with given mobile number, go through your inbox to activate");
			}

		}

		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateUser(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.EMAIL_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID)
				&& null != UserService.getUser(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			User user = UserService.getUser(Integer.parseInt(json.get(ServiceConstants.ID)));
			if (null != json.get(ServiceConstants.UNAME)) {
				String name = json.get(ServiceConstants.UNAME).toString();
				user.setName(name);

			}

			if (null != json.get(ServiceConstants.PWD)) {
				String pwd = json.get(ServiceConstants.PWD).toString();
				System.out.println(pwd);
				user.setPwd(pwd);

			}
			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER).toString();
				System.out.println(mobileNo);
				user.setMobileNo(mobileNo);

			}

			if (null != json.get(ServiceConstants.WALLET_BALANCE)) {
				int walletBalance = Integer.parseInt(json.get(ServiceConstants.WALLET_BALANCE));
				user.setWalletBalance(walletBalance);
			}
			if (null != json.get(ServiceConstants.UNAME)) {
				String name = json.get(ServiceConstants.UNAME).toString();
				System.out.println(name);
				user.setDisplayName(name);

			}
			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				// UserService.userExistByEmail(emailId);
				String emailId = json.get(ServiceConstants.EMAIL_ID).toString();
				boolean userExit = UserService.userExistByEmail(emailId);
				if (userExit) {
					System.out.println("User already exist with given emailId");
				} else {
					System.out.println(emailId);
					user.setEmailId(emailId);
				}

			}
			if (null != json.get(ServiceConstants.F_NAME)) {
				String firstName = json.get(ServiceConstants.F_NAME).toString();
				System.out.println(firstName);
				user.setFirstName(firstName);

			}

			if (null != json.get(ServiceConstants.L_NAME)) {
				String lastName = json.get(ServiceConstants.L_NAME).toString();
				System.out.println(lastName);
				user.setLastName(lastName);

			}

			if (null != json.get(ServiceConstants.OTP)) {
				String otp = json.get(ServiceConstants.OTP).toString();
				System.out.println(otp);
				user.setOtp(otp);

			}
			if (null != json.get(ServiceConstants.ACTIVATE_IND)) {
				String activateInd = json.get(ServiceConstants.ACTIVATE_IND).toString();
				System.out.println(activateInd);
				user.setIsActive(Boolean.valueOf(activateInd));
			}
			if (null != json.get(ServiceConstants.DELETED_IND)) {
				String deletedInd = json.get(ServiceConstants.DELETED_IND).toString();
				System.out.println(deletedInd);
				user.setDeleted(Boolean.valueOf(deletedInd));
			}

			if (null != json.get(ServiceConstants.LAST_LOGIN_DATE)) {
				Date lastLoginDate = new Date();
				user.setLastLoginDate(lastLoginDate);
			}

			if (null != json.get(ServiceConstants.DUES)) {
				int dues = Integer.parseInt(json.get(ServiceConstants.DUES).toString());
				user.setDues(dues);
			}
//			if(null != json.get(ServiceConstants.LAST_PAID)) {
//				float lastPaid = Float.parseFloat(json.get(ServiceConstants.LAST_PAID).toString());
//				user.setLastPaid(lastPaid);
//			}
//			if(null != json.get(ServiceConstants.LAST_PAID_ON)) {
//				Date lastPaidOn = new Date(json.get(ServiceConstants.LAST_PAID_ON).toString());
//				user.setLastPaidOn(lastPaidOn);
//			}
			if (null != json.get(ServiceConstants.AVATAR)) {
				String avatar = json.get(ServiceConstants.AVATAR).toString();
				user.setAvatar(avatar);
			}

			if (null != json.get(ServiceConstants.DEVICE)) {
				String deviceId = json.get(ServiceConstants.DEVICE).toString();
				System.out.println(deviceId);
				UserDeviceID ud = user.getUserDeviceIDList().size() > 0 ? user.getUserDeviceIDList().get(0) : null;
				if (ud == null) {
					UserDeviceID ud1 = new UserDeviceID();
					ud1.setUser(user);
					ud1.setDeviceId(deviceId);
					List<UserDeviceID> ud1list = new ArrayList<UserDeviceID>();
					ud1list.add(ud1);
					user.setUserDeviceIDList(ud1list);
				} else {
					ud.setDeviceId(deviceId);
				}
			}
			UserService.updateUser(user);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "User updated");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "User doesn't exist with given email or userid");
		}
		return ResponseEntity.ok().body(response);
//		return ResponseEntity.created(new URI("/api/user/create/" + result)).body(result);
	}

	@PostMapping("/login")
	ResponseEntity<?> validateUser(@Valid @RequestBody Map<String, String> json, HttpServletRequest request) {
		if (null != json.get(ServiceConstants.EMAIL_ID) && null != json.get(ServiceConstants.PWD)
				&& null != json.get(ServiceConstants.PLAYER_ID)) {

			String playerId = json.get(ServiceConstants.PLAYER_ID);
			Admin admin = null;
			User user = null;
			LookUp lookUP = null;
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "REG_ADDRESS");
			int regAddress = lookUp.getLookUpId();
			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "REG_IMAGE");
			int regImage = lookUp2.getLookUpId();
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "REG_COMPLETED");
			int regCompleted = lookUp3.getLookUpId();
			Map<String, String> response = new HashMap<String, String>();
			log.info("Request to validate admin: {}", json.get(ServiceConstants.EMAIL_ID));
			if (json.get(ServiceConstants.EMAIL_ID).contains("@milaan.com")) {
				if (adminService.checkAdminByEmailId(json.get(ServiceConstants.EMAIL_ID))) {
					admin = adminService.loginAdmin(json.get(ServiceConstants.EMAIL_ID),
							json.get(ServiceConstants.PWD));
					if (null != admin && null != json.get(ServiceConstants.DEVICE)
							&& json.get(ServiceConstants.DEVICE).length() > 10) {
						AdminDeviceID deviceId = admin.getAdminDeviceIDList().stream()
								.filter(ad -> json.get(ServiceConstants.DEVICE).equals(ad.getDeviceId())).findAny()
								.orElse(null);
						if (null == deviceId) {
							deviceId = new AdminDeviceID();
							deviceId.setAdmin(admin);
							deviceId.setDeviceId(json.get(ServiceConstants.DEVICE));
							deviceId.setPlayerId(json.get(ServiceConstants.PLAYER_ID));
							admin.getAdminDeviceIDList().add(deviceId);
						} else {
							deviceId.setPlayerId(json.get(ServiceConstants.PLAYER_ID));
							int index = admin.getAdminDeviceIDList().indexOf(deviceId);
							admin.getAdminDeviceIDList().get(index).setPlayerId(playerId);
							adminService.updateAdmin(admin);
						}
					}

					if (null != admin) {
						admin.setLastLoginDate(new Date());
						admin.setLogedIn(Boolean.TRUE);
						admin.setPlayerId(playerId);
						admin.setToken(UUID.randomUUID().toString() + "-" + admin.getAdminId());
						adminService.updateAdmin(admin);
						admin.setOtp(Strings.EMPTY);
						admin.setPwd(Strings.EMPTY);

						admin.setAdminDeviceIDList(null);
						return ResponseEntity.ok().body(admin);
					} else {
						response.put("status", String.valueOf(false));
						response.put("description", "Invalid password.");
						return ResponseEntity.ok().body(response);
					}
				} else if (adminService.checkDeactiveAdminByEmailId(json.get(ServiceConstants.EMAIL_ID))) {
					Admin deactiveAdmin = adminService.getDeactiveAdmin(json.get(ServiceConstants.EMAIL_ID),
							json.get(ServiceConstants.PWD));
					if (null != deactiveAdmin) {
						response.put("status", String.valueOf(true));
						response.put("adminId", String.valueOf(deactiveAdmin.getAdminId()));
						response.put("shopId", deactiveAdmin.getShopId());
						response.put("mobileNo", deactiveAdmin.getMobileNo());
						response.put("registrationStatus", String.valueOf(deactiveAdmin.getRegistrationStatus()));
						if (deactiveAdmin.getRegistrationStatus() == regCompleted) {
							response.put("description", "Please ask for activation.");
						} else if (deactiveAdmin.getRegistrationStatus() == regAddress) {
							response.put("description", "User created but address has not been created.");
						} else if (deactiveAdmin.getRegistrationStatus() == regImage) {
							response.put("descreption", "User created but image has not been uploaded");
						}
					} else {
						response.put("status", String.valueOf(false));
						response.put("description", "Invalid password.");
					}
					return ResponseEntity.ok().body(response);
				} else {
					response.put("status", String.valueOf(false));
					response.put("description", "User does not exist with given emailid.");
					return ResponseEntity.ok().body(response);
				}
			} else {

				user = UserService.loginUser(json.get(ServiceConstants.EMAIL_ID), json.get(ServiceConstants.PWD));
				if (null != user && null != json.get(ServiceConstants.DEVICE)
						&& json.get(ServiceConstants.DEVICE).length() > 10) {
					UserDeviceID deviceId = user.getUserDeviceIDList().stream()
							.filter(ud -> json.get(ServiceConstants.DEVICE).equals(ud.getDeviceId())).findAny()
							.orElse(null);
					if (null == deviceId) {
						deviceId = new UserDeviceID();
						deviceId.setUser(user);
						deviceId.setDeviceId(json.get(ServiceConstants.DEVICE));
						deviceId.setPlayerId(json.get(ServiceConstants.PLAYER_ID));
						user.getUserDeviceIDList().add(deviceId);
					} else {
						deviceId.setPlayerId(json.get(ServiceConstants.PLAYER_ID));
						int index = user.getUserDeviceIDList().indexOf(deviceId);
						user.getUserDeviceIDList().get(index).setPlayerId(playerId);
						UserService.updateUser(user);
					}
				}

				if (null != user) {
					user.setLastLoginDate(new Date());
					user.setToken(UUID.randomUUID().toString() + "-" + user.getUserId());
					user.setPlayerId(playerId);
					user.setLogedIn(Boolean.TRUE);
					UserService.updateUser(user);
					user.setPwd(Strings.EMPTY);
					user.setUserDeviceIDList(null);
					return ResponseEntity.ok().body(user);
				}

				else {
					return ResponseEntity.noContent().build();
				}
			}
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@PostMapping("/login/test")
	ResponseEntity<?> validateUserTest(@Valid @RequestBody Map<String, String> json, HttpServletRequest request) {
		MyResponse myResponse = new MyResponse();
		if (null != json.get(ServiceConstants.EMAIL_ID) && null != json.get(ServiceConstants.PWD)
				&& null != json.get(ServiceConstants.PLAYER_ID)) {

			String playerId = json.get(ServiceConstants.PLAYER_ID);
			Admin admin = null;
			User user = null;
			LookUp lookUP = null;
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "REG_ADDRESS");
			int regAddress = lookUp.getLookUpId();
			LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "REG_IMAGE");
			int regImage = lookUp2.getLookUpId();
			LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "REG_COMPLETED");
			int regCompleted = lookUp3.getLookUpId();
			Map<String, String> response = new HashMap<String, String>();
			log.info("Request to validate admin: {}", json.get(ServiceConstants.EMAIL_ID));
			if (json.get(ServiceConstants.EMAIL_ID).contains("@milaan.com")) {
				if (adminService.checkAdminByEmailId(json.get(ServiceConstants.EMAIL_ID))) {
					admin = adminService.loginAdmin(json.get(ServiceConstants.EMAIL_ID),
							json.get(ServiceConstants.PWD));
					if (null != admin && null != json.get(ServiceConstants.DEVICE)
							&& json.get(ServiceConstants.DEVICE).length() > 10) {
						AdminDeviceID deviceId = admin.getAdminDeviceIDList().stream()
								.filter(ad -> json.get(ServiceConstants.DEVICE).equals(ad.getDeviceId())).findAny()
								.orElse(null);
						if (null == deviceId) {
							deviceId = new AdminDeviceID();
							deviceId.setAdmin(admin);
							deviceId.setDeviceId(json.get(ServiceConstants.DEVICE));
							deviceId.setPlayerId(json.get(ServiceConstants.PLAYER_ID));
							admin.getAdminDeviceIDList().add(deviceId);
						} else {
							deviceId.setPlayerId(json.get(ServiceConstants.PLAYER_ID));
							int index = admin.getAdminDeviceIDList().indexOf(deviceId);
							admin.getAdminDeviceIDList().get(index).setPlayerId(playerId);
							adminService.updateAdmin(admin);
						}
					}

					if (null != admin) {
						admin.setLastLoginDate(new Date());
						admin.setLogedIn(Boolean.TRUE);
						admin.setPlayerId(playerId);
						admin.setToken(UUID.randomUUID().toString() + "-" + admin.getAdminId());
						adminService.updateAdmin(admin);
						admin.setOtp(Strings.EMPTY);
						admin.setPwd(Strings.EMPTY);

						admin.setAdminDeviceIDList(null);
						return ResponseEntity.ok().body(admin);
					} else {
						myResponse.setData(admin);
						myResponse.setDescription("Invalid password.");
						myResponse.setStatus("4000");
//						response.put("status", String.valueOf(false));
//						response.put("description", "Invalid password.");
						return ResponseEntity.ok().body(response);
					}
				} else if (adminService.checkDeactiveAdminByEmailId(json.get(ServiceConstants.EMAIL_ID))) {
					Admin deactiveAdmin = adminService.getDeactiveAdmin(json.get(ServiceConstants.EMAIL_ID),
							json.get(ServiceConstants.PWD));
					if (null != deactiveAdmin) {
						myResponse.setData(deactiveAdmin.getAdminId());
						myResponse.setData(deactiveAdmin.getShopId());
						myResponse.setData(deactiveAdmin.getMobileNo());
						myResponse.setData(deactiveAdmin.getRegistrationStatus());
						myResponse.setStatus("4001");
//						response.put("status", String.valueOf(true));
//						response.put("adminId", String.valueOf(deactiveAdmin.getAdminId()));
//						response.put("shopId", deactiveAdmin.getShopId());
//						response.put("mobileNo", deactiveAdmin.getMobileNo());
//						response.put("registrationStatus", String.valueOf(deactiveAdmin.getRegistrationStatus()));
						if (deactiveAdmin.getRegistrationStatus() == regCompleted) {
//							response.put("description", "Please ask for activation.");
							myResponse.setDescription("Please ask for activation.");
							myResponse.setStatus("4002");
						} else if (deactiveAdmin.getRegistrationStatus() == regAddress) {
							myResponse.setDescription("User created but address has not been created.");
							myResponse.setStatus("4002");
							// response.put("description", "User created but address has not been
							// created.");
						} else if (deactiveAdmin.getRegistrationStatus() == regImage) {
							myResponse.setDescription("User created but image has not been uploaded.");
							myResponse.setStatus("4002");
							// response.put("descreption", "User created but image has not been uploaded");
						}
					} else {
						myResponse.setStatus("false");
						myResponse.setDescription("Invalid password.");
						myResponse.setStatus("4000");
//						response.put("status", String.valueOf(false));
//						response.put("description", "Invalid password.");
					}
					return ResponseEntity.ok().body(response);
				} else {
					myResponse.setStatus("false");
					myResponse.setDescription("User does not exist with given emailid.");
					myResponse.setStatus("4000");
//					response.put("status", String.valueOf(false));
//					response.put("description", "User does not exist with given emailid.");
					return ResponseEntity.ok().body(response);
				}
			} else {
				boolean userCreated = UserService.checkExitByEmailId(json.get(ServiceConstants.EMAIL_ID),
						json.get(ServiceConstants.MOBILE_NUMBER));
				if (userCreated) {
					user = UserService.loginUser(json.get(ServiceConstants.EMAIL_ID), json.get(ServiceConstants.PWD));

					if (null != user && null != json.get(ServiceConstants.DEVICE)
							&& json.get(ServiceConstants.DEVICE).length() > 10) {
						UserDeviceID deviceId = user.getUserDeviceIDList().stream()
								.filter(ud -> json.get(ServiceConstants.DEVICE).equals(ud.getDeviceId())).findAny()
								.orElse(null);
						if (null == deviceId) {
							deviceId = new UserDeviceID();
							deviceId.setUser(user);
							deviceId.setDeviceId(json.get(ServiceConstants.DEVICE));
							deviceId.setPlayerId(json.get(ServiceConstants.PLAYER_ID));
							user.getUserDeviceIDList().add(deviceId);
						} else {
							deviceId.setPlayerId(json.get(ServiceConstants.PLAYER_ID));
							int index = user.getUserDeviceIDList().indexOf(deviceId);
							user.getUserDeviceIDList().get(index).setPlayerId(playerId);
							UserService.updateUser(user);
						}
					}

					if (null != user) {
						user.setLastLoginDate(new Date());
						user.setToken(UUID.randomUUID().toString() + "-" + user.getUserId());
						user.setPlayerId(playerId);
						user.setLogedIn(Boolean.TRUE);
						UserService.updateUser(user);
						user.setPwd(Strings.EMPTY);
						user.setUserDeviceIDList(null);
						return ResponseEntity.ok().body(user);
					}

					else {
						return ResponseEntity.noContent().build();
					}
				} else {
					myResponse.setStatus("4000");
					myResponse.setDescription("Invalid email-ID.");
					myResponse.setData(user);
				}

			}
		} else {
			return ResponseEntity.noContent().build();
		}
		return null;

	}

//	@PostMapping("/logout")
//	ResponseEntity<?> logoutUser(@Valid @RequestBody Map<String, String> json, HttpServletRequest request) {
//		User result = null;
//		UserDeviceID dvice = null;
//		Admin admin = null;
//		if (null != json.get(ServiceConstants.EMAIL_ID)) {
//			log.info("Request to logout user: {}", json.get(ServiceConstants.EMAIL_ID));
//			result = UserService.getUsersByEmailId(json.get(ServiceConstants.EMAIL_ID));
//			if (null != json.get(ServiceConstants.DEVICE) && json.get(ServiceConstants.DEVICE).length() > 10) {
//				dvice = result.getUserDeviceIDList().stream()
//						.filter(ud -> json.get(ServiceConstants.DEVICE).equals(ud.getDeviceId())).findAny()
//						.orElse(null);
//				if (null != dvice) {
//					UserService.deleteUserDevice(dvice);
//				}
//			}
//		}
//		if (null != result && null != dvice) {
//			admin.setLogedIn(Boolean.FALSE);
//			adminService.updateAdmin(admin);
//			return ResponseEntity.ok().body("Logout successfully");
//		} else {
//			return ResponseEntity.noContent().build();
//		}
//	}
//	@GetMapping()

	@PostMapping("/logout")
	ResponseEntity<?> logoutUser(@Valid @RequestBody Map<String, String> json, HttpServletRequest request) {
		User user = null;
		UserDeviceID userDeviceID = null;
		AdminDeviceID adminDeviceID = null;
		Admin admin = null;
		if (null != json.get(ServiceConstants.EMAIL_ID)) {
			log.info("Request to logout user: {}", json.get(ServiceConstants.EMAIL_ID));
			if (json.get(ServiceConstants.EMAIL_ID).contains("@milaan.com")) {
				admin = adminService.getAdminByEmailId(json.get(ServiceConstants.EMAIL_ID));
				if (null != json.get(ServiceConstants.DEVICE) && json.get(ServiceConstants.DEVICE).length() > 10) {
					adminDeviceID = admin.getAdminDeviceIDList().stream()
							.filter(ud -> json.get(ServiceConstants.DEVICE).equals(ud.getDeviceId())).findAny()
							.orElse(null);
					if (null != adminDeviceID) {
						adminService.deleteAdminDevice(adminDeviceID);
					}
				}

				if (null != admin && null != adminDeviceID) {
					admin.setLogedIn(Boolean.FALSE);
					adminService.updateAdmin(admin);
					return ResponseEntity.ok().body("Logout successfully");
				} else {
					return ResponseEntity.noContent().build();
				}

			} else {
				user = UserService.sendOtp(json.get(ServiceConstants.EMAIL_ID));
				if (null != json.get(ServiceConstants.DEVICE) && json.get(ServiceConstants.DEVICE).length() > 10) {
					userDeviceID = user.getUserDeviceIDList().stream()
							.filter(ud -> json.get(ServiceConstants.DEVICE).equals(ud.getDeviceId())).findAny()
							.orElse(null);
					if (null != userDeviceID) {
						UserService.deleteUserDevice(userDeviceID);
					}
				}

				if (null != user && null != userDeviceID) {
					user.setLogedIn(Boolean.FALSE);
					UserService.updateUser(user);
					return ResponseEntity.ok().body("Logout successfully");
				} else {
					return ResponseEntity.noContent().build();
				}
			}

		}
		return ResponseEntity.noContent().build();
	}

//	@PostMapping("/admin/validate")
//	ResponseEntity<Map<String, String>> validateAdminUser(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) {
//		log.info("Request to validate user: {}", json.get(ServiceConstants.EMAILID));
//		Map<String, String> response = new HashMap<String, String>();
////		response.put(ServiceConstants.EMAILID, json.get(ServiceConstants.EMAILID));
//		User result = UserService.loginAdminUser(json.get(ServiceConstants.EMAILID), json.get(ServiceConstants.PWD));
//		if (null != result) {
//			String uuid = UUID.randomUUID().toString();
//			result.setLastLoginDate(new Date());
//			UserService.updateUser(result);
//			request.getSession().setAttribute(ServiceConstants.TOKEN, uuid);
//			response.put(ServiceConstants.TOKEN, uuid);
//			response.put(ServiceConstants.USERID, String.valueOf(result.getUserId()));
//	//		response.put(ServiceConstants.USER_TYPE, String.valueOf(result.getUserType()));
//			response.put(ServiceConstants.DISPLAYNAME, result.getFirstName()+" "+result.getLastName());
//		} else {
//			response.put(ServiceConstants.TOKEN, Strings.EMPTY);
//		}
//		return ResponseEntity.ok().body(response);
//	}

	@PostMapping("/validatedevice")
	ResponseEntity<Map<String, String>> validateDeviceUser(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) {
		log.info("Request to validate deviceid: {}", json.get(ServiceConstants.DEVICE));
		Map<String, String> response = new HashMap<String, String>();
		// response.put(ServiceConstants.DEVICE, json.get(ServiceConstants.DEVICE));
		User result = UserService.validateUserByDeviceId(json.get(ServiceConstants.DEVICE));
		if (null != result) {
			String uuid = UUID.randomUUID().toString();
			request.getSession().setAttribute(ServiceConstants.TOKEN, uuid);
			response.put(ServiceConstants.TOKEN, uuid);
		} else {
			response.put(ServiceConstants.TOKEN, Strings.EMPTY);
		}
		return ResponseEntity.ok().body(response);
	}

//	@GetMapping("viewWalletBalance/{userId}")
//	public ResponseEntity<Map<String, String>> viewWalletBalance(@PathVariable("userId") Integer userId) {
//		Map<String, String> response = new HashMap<String, String>();
//		int walletBalance = UserService.viewWalletBalance(userId);
//		response.put(ServiceConstants.USERID, String.valueOf(userId));
//		response.put(ServiceConstants.WALLET_BALANCE, String.valueOf(walletBalance));
//		return ResponseEntity.ok().body(response);
//	}
//	
//	@PostMapping("/updateWalletBalance")
//	ResponseEntity<Map<String, String>> updateWalletBalance(@Valid @RequestBody Map<String, String> json,
//			HttpServletRequest request) throws URISyntaxException {
//		log.info("Request to update updateWalletBalance: {}", json.get(ServiceConstants.USERID));
//		Map<String, String> response = new HashMap<String, String>();
//		if (null != json.get(ServiceConstants.USERID)
//				&& null != UserService.getUser(Integer.parseInt(json.get(ServiceConstants.USERID)))) {
//			if (null != json.get(ServiceConstants.AMOUNT)) {
//				int amount = Integer.parseInt(json.get(ServiceConstants.AMOUNT));
//				System.out.println(amount);
//				UserService.updateWalletBalance(Integer.parseInt(json.get(ServiceConstants.USERID)), amount);
//				response.put("status", Boolean.TRUE.toString());
//			}
//			response.put("description", "User wallet updated");
//		} else {
//			response.put("description", "User wallet has not been update , please contact admin");
//		}
//		return ResponseEntity.ok().body(response);
//	}
//	
//	@GetMapping("/follow/byuser/{consultantId}/{userId}")
//	public ResponseEntity<User> followConsultantByUser(@PathVariable("consultantId") Integer consultantId,@PathVariable("userId") Integer userId) {
//		User user = UserService.getUser(consultantId.intValue());
//		UserService.followConsultantByUser(user, userId);
//		return new ResponseEntity<User>(user, HttpStatus.OK);
//	}

	@DeleteMapping("delete/{userId}")
	ResponseEntity<Map<String, String>> deleteUser(@PathVariable("userId") int userId) {
		Map<String, String> response = new HashMap<String, String>();
		User user = UserService.getUser(userId);

		if (null != user) {
			boolean result = UserService.deleteUser(userId);
			if (result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description", "User delete with given userId:" + userId);
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "User does not exist with given userId ");

		}
		return ResponseEntity.ok().body(response);
	}

}
