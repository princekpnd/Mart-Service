package com.shop.shopservice.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.ILookUpType;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.controller.AdminController;
import com.shop.shopservice.controller.SendOtp;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.AdminDeviceID;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.User;
import com.shop.shopservice.entity.UserDeviceID;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.IUserService;
import com.shop.shopservice.utils.Calculation;

@RestController
@RequestMapping("/api/sms")
public class SendOTP {
	private final Logger log = LoggerFactory.getLogger(SendOtp.class);
	@Autowired
	private IAdminService adminService;

	@Autowired
	private IUserService UserService;

	@Autowired
	private ILookUpType lookUpType;

	@Autowired
	private ILookUp lookup;

	@GetMapping("check")
	public ResponseEntity<?> sendCheck() {
		Map<String, String> response = new HashMap<String, String>();
//		System.out.println("Message send successfully--" +result.getMessageId());
		DecimalFormat df = new DecimalFormat("#.##");

		double data = 2.985509877;
		String data2 = String.format("%.2f", data);
		float data1 = Float.parseFloat(data2);

		return ResponseEntity.ok().body(data1);
	}

	public static final String ACCOUNT_SID = "AC6cea49ecaadce270d27d41b717e56770";
	public static final String AUTH_TOKEN = "f5f4251aa4a1a4fa19fcde59127bc593";
//	static {
//		System.setProperty(ServiceConstants.AWS_ACCESS_KEY, "AKIA3XBJGJT3KAZIMB35");
//		System.setProperty(ServiceConstants.AWS_SECRET_KEY, "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");		
//	}
//	
//	@GetMapping("get")
//	public ResponseEntity<?> getUserById() {
//		String pattern = "yyyy-MM-dd:HH:mm:a";
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//
//		Date date = null;
//		try {
//			date = simpleDateFormat.parse("2018-09-09:01:30:am");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<>(date, HttpStatus.OK);
//	}

//	@GetMapping("send")
//	public ResponseEntity<?> sendOtp() {
//		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//	    Message message = Message.creator(new PhoneNumber("+91 9155316625"),
//	        new PhoneNumber("+18312641821"), 
//	        "This is the ship that made the Kessel Run in fourteen parsecs?").create();
//		return new ResponseEntity<>(message, HttpStatus.OK);
//	}

	@GetMapping("send")
	public ResponseEntity<?> sendOtp() {
		final StringBuffer stringBuffer = new StringBuffer();
		try {
			// Construct data
			String apiKey = "apikey=" + "NTg2ODYyNWEzNjY2MzIzNjcyNjk2YzZkNTEzNDUzNjc=";
			String message = "&message=" + "This is your message";
			String sender = "&sender=" + "TCLXLX%20";
			String numbers = "&numbers=" + "7739031245";

			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();

		} catch (Exception e) {
			System.out.println("Error SMS " + e);
		}
		return new ResponseEntity<>(stringBuffer, HttpStatus.OK);
	}
	
	@GetMapping("cal")
	public ResponseEntity<?> getCal(){
		Calculation calculation = new Calculation();
		float b = (float) 54.887873444455;
		float a =calculation.DecimalCalculation(b);
		return new ResponseEntity<>(a, HttpStatus.OK);
		
	}

	@PostMapping("/send/notification")
	ResponseEntity<Map<String, String>> sendNotification(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) {
		log.info("Request to validate deviceid: {}", json.get(ServiceConstants.USER_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.USER_ID) && null != json.get(ServiceConstants.USER_TYPE)
				&& null != json.get(ServiceConstants.CONTENT)) {

			String content = json.get(ServiceConstants.CONTENT);
			int userId = Integer.parseInt(json.get(ServiceConstants.USER_ID)),
					userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));

			String jsonResponse = null;
			User user = null;
			Admin admin = null;
			LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "CUSTOMER");
			int userType1 = lookUp.getLookUpId();
			LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "ADMIN");
			int adminType = lookUp1.getLookUpId();
			List<UserDeviceID> userDeviceIDList = null;
			List<AdminDeviceID> adminDeviceIDList = null;

			if (userType == userType1) {
				user = UserService.getUser(userId);
				userDeviceIDList = user.getUserDeviceIDList();
				for (int i = 0; i < userDeviceIDList.size(); i++) {
					String playerId = userDeviceIDList.get(i).getPlayerId();
					try {
						URL url = new URL("https://onesignal.com/api/v1/notifications");
						HttpURLConnection con = (HttpURLConnection) url.openConnection();
						con.setUseCaches(false);
						con.setDoOutput(true);
						con.setDoInput(true);

						con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
						con.setRequestMethod("POST");

//						String strJsonBody = "{" + "\"app_id\": \"43e3395b-0019-492b-b999-4321444f25ad\","
//								+ "\"include_player_ids\": [\"41adf9bf-0e59-496d-9d07-392a0577ce23\"],"
//								+ "\"include_player_ids\": [\"41adf9bf-0e59-496d-9d07-392a0577ce23\"],"
//								+ "\"data\": {\"foo\": \"bar\"}," + "\"contents\": {\"en\": \"English Message\"}" + "}";

						String strJsonBody = "{" + "\"app_id\": \"43e3395b-0019-492b-b999-4321444f25ad\","
								+ "\"include_player_ids\": [\"" + playerId + "\"]," + "\"data\": {\"foo\": \"bar\"},"
								+ "\"contents\": {\"en\": \"" + content + "\"}" + "}";

						System.out.println("strJsonBody:\n" + strJsonBody);

						byte[] sendBytes = strJsonBody.getBytes("UTF-8");
						con.setFixedLengthStreamingMode(sendBytes.length);

						OutputStream outputStream = con.getOutputStream();
						outputStream.write(sendBytes);

						int httpResponse = con.getResponseCode();
						System.out.println("httpResponse: " + httpResponse);

						if (httpResponse >= HttpURLConnection.HTTP_OK
								&& httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
							Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
							jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
							scanner.close();
						} else {
							Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
							jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
							scanner.close();
						}
						System.out.println("jsonResponse:\n" + jsonResponse);

					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			} else if (userType == adminType) {
				admin = adminService.getAdmin(userId);
				adminDeviceIDList = admin.getAdminDeviceIDList();
				for (int i = 0; i < adminDeviceIDList.size(); i++) {
					String playerId = adminDeviceIDList.get(i).getPlayerId();

					try {
						URL url = new URL("https://onesignal.com/api/v1/notifications");
						HttpURLConnection con = (HttpURLConnection) url.openConnection();
						con.setUseCaches(false);
						con.setDoOutput(true);
						con.setDoInput(true);

						con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
						con.setRequestMethod("POST");

//						String strJsonBody = "{" + "\"app_id\": \"43e3395b-0019-492b-b999-4321444f25ad\","
//								+ "\"include_player_ids\": [\"41adf9bf-0e59-496d-9d07-392a0577ce23\"],"
//								+ "\"include_player_ids\": [\"41adf9bf-0e59-496d-9d07-392a0577ce23\"],"
//								+ "\"data\": {\"foo\": \"bar\"}," + "\"contents\": {\"en\": \"English Message\"}" + "}";

						String strJsonBody = "{" + "\"app_id\": \"43e3395b-0019-492b-b999-4321444f25ad\","
								+ "\"include_player_ids\": [\"" + playerId + "\"]," + "\"data\": {\"foo\": \"bar\"},"
								+ "\"contents\": {\"en\": \"" + content + " \"}" + "}";

						System.out.println("strJsonBody:\n" + strJsonBody);

						byte[] sendBytes = strJsonBody.getBytes("UTF-8");
						con.setFixedLengthStreamingMode(sendBytes.length);

						OutputStream outputStream = con.getOutputStream();
						outputStream.write(sendBytes);

						int httpResponse = con.getResponseCode();
						System.out.println("httpResponse: " + httpResponse);

						if (httpResponse >= HttpURLConnection.HTTP_OK
								&& httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
							Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
							jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
							scanner.close();
						} else {
							Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
							jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
							scanner.close();
						}
						System.out.println("jsonResponse:\n" + jsonResponse);

					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			}

		}

		return ResponseEntity.ok().body(response);
	}

}
