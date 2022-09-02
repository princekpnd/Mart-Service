package com.shop.shopservice.notification;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class OneSignalApiUtils {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		createNotificationForAll("This is simple basic title coming from java program", "This is description coming from java programme using one singal API");
//	//	createNotificationForUser("This is simple basic title coming from java program", "This is description coming from java programme using one singal API","ed6dc0db-44e7-4c33-a19d-16c672bd94c8");
//	}

	public static void createNotificationForUser(String title, String messaage, String playerId){
		try {
			   String jsonResponse;
			   
			   URL url = new URL("https://onesignal.com/api/v1/notifications");
			   HttpURLConnection con = (HttpURLConnection)url.openConnection();
			   con.setUseCaches(false);
			   con.setDoOutput(true);
			   con.setDoInput(true);
			   String litralBack = "\"";
			   playerId = litralBack+playerId+litralBack;
			   title = litralBack+title+litralBack;
			   messaage = litralBack+messaage+litralBack;
			   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			   con.setRequestProperty("Authorization", "Basic OGFmODRiMmMtOWYzNS00MjUwLWJiZTctNmVjMjVjNzI4NGQx");
			   con.setRequestMethod("POST");

			   String strJsonBody = "{"
			                      +   "\"app_id\": \"dc0ca4a2-c99b-40a9-900e-3ee57c47eaaa\","
			                      +   "\"include_player_ids\": ["+playerId+"],"
			                      +   "\"data\": {\"foo\": \"bar\"},"
			                      + "\"headings\": {\"en\": "+title+"},"
			                      +   "\"contents\": {\"en\": "+messaage+"}"
			                      + "}";
			         
			   
			   System.out.println("strJsonBody:\n" + strJsonBody);

			   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			   con.setFixedLengthStreamingMode(sendBytes.length);

			   OutputStream outputStream = con.getOutputStream();
			   outputStream.write(sendBytes);

			   int httpResponse = con.getResponseCode();
			   System.out.println("httpResponse: " + httpResponse);

			   if (  httpResponse >= HttpURLConnection.HTTP_OK
			      && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
			      Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
			      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      scanner.close();
			   }
			   else {
			      Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
			      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      scanner.close();
			   }
			   System.out.println("jsonResponse:\n" + jsonResponse);
			   
			} catch(Throwable t) {
			   t.printStackTrace();
			}
	}
	
	public static void createNotificationForAll(String title, String messaage){
		try {
			   String jsonResponse;
			   
			   URL url = new URL("https://onesignal.com/api/v1/notifications");
			   HttpURLConnection con = (HttpURLConnection)url.openConnection();
			   con.setUseCaches(false);
			   con.setDoOutput(true);
			   con.setDoInput(true);
			   String litralBack = "\"";
			   title = litralBack+title+litralBack;
			   messaage = litralBack+messaage+litralBack;
			   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			   con.setRequestProperty("Authorization", "Basic OGFmODRiMmMtOWYzNS00MjUwLWJiZTctNmVjMjVjNzI4NGQx");
			   con.setRequestMethod("POST");

			   String strJsonBody = "{"
			                      +   "\"app_id\": \"dc0ca4a2-c99b-40a9-900e-3ee57c47eaaa\","
			                      +   "\"included_segments\": [\"All\"],"
			                      +   "\"data\": {\"Java\": \"From Java Programme\"},"
			                      + "\"headings\": {\"en\": "+title+"},"
			                      +   "\"contents\": {\"en\": "+messaage+"}"
			                      + "}";
			         
			   
			   System.out.println("strJsonBody:\n" + strJsonBody);

			   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			   con.setFixedLengthStreamingMode(sendBytes.length);

			   OutputStream outputStream = con.getOutputStream();
			   outputStream.write(sendBytes);

			   int httpResponse = con.getResponseCode();
			   System.out.println("httpResponse: " + httpResponse);

			   if (  httpResponse >= HttpURLConnection.HTTP_OK
			      && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
			      Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
			      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      scanner.close();
			   }
			   else {
			      Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
			      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      scanner.close();
			   }
			   System.out.println("jsonResponse:\n" + jsonResponse);
			   
			} catch(Throwable t) {
			   t.printStackTrace();
			}

	}
}
