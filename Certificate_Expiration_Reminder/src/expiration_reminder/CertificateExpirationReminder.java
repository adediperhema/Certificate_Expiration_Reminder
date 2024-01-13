package expiration_reminder;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class CertificateExpirationReminder implements Job {

	// private static String date;
	private static String payload;
	private static String app;
	private static String Url;
	private static String app1;
	private static String phoneNumber1;
	private static String expiryDate1;
	static Connection connection;
	static PreparedStatement statement;
	static String successful = "00";
	static String failed = "06";

	public static long daysBetween(Date one, Date two) {

		long difference = (one.getTime() - two.getTime()) / 86400000; // 86400000 is the number of milliseconds in a day
		return Math.abs(difference);
	}

	public static void main(String args[]) throws Exception {

		// loadAppCertsData(app1, newDate1,phoneNumber1);
		//CertsDataManager(app1, expiryDate1, phoneNumber1);

	}

	public static void activateAlert(String appName, String phoneNumber, String expiryDate) throws Exception {
		SMS_call(appName, phoneNumber, expiryDate);
		Email_call(appName, phoneNumber,expiryDate);
		Database_call(appName,expiryDate);

	}

	public static String CertsDataManager(String appName, String newDate, String phoneNumber) throws Exception {

		try (FileReader reader = new FileReader("C:\\Users\\rhema.adedipe\\eclipse-workspace\\expiration_reminder\\config.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			app = properties.getProperty("app");
			// phoneNumber = properties.getProperty("phoneNumber");
			Url = properties.getProperty("config.url");

		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] arrOfStr = app.split("#");

		/*
		 * System.out.println(arrOfStr3.length); System.out.println(part1);
		 */

		for (String a : arrOfStr) {

			// System.out.println(a);
			String[] arrOfStr2 = a.split("~");
			appName = arrOfStr2[0];

			String date = arrOfStr2[1];

			Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(date);

			Date today = new Date();
			Calendar myNextCalendar = Calendar.getInstance();
			myNextCalendar.setTime(date1);
			Date expiry_date = myNextCalendar.getTime();
			// expiration_reminder myObject = new expiration_reminder();
			// long days = myObject.daysBetween(today, expiry_date);
			long days = daysBetween(today, expiry_date);
			long total_days = days + 1;
            
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String todaysDate = sdf.format(today);
			newDate = sdf.format(expiry_date);

			if (total_days <= 14 && total_days >= 0) {
				activateAlert(appName, phoneNumber, newDate);
				System.out.println(total_days + " days from today's date of " + todaysDate + " to expiry date of " + newDate);

			}

		}

		return appName;	
		}

	public static String SMS_call(String appName, String phoneNumber, String expiry_date) throws Exception {
		try (FileReader reader = new FileReader(
				"C:\\Users\\rhema.adedipe\\eclipse-workspace\\expiration_reminder\\config.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			phoneNumber = properties.getProperty("phoneNumber");
			Url = properties.getProperty("config.url");

		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] arrOfStr3 = phoneNumber.split("#");
		phoneNumber = arrOfStr3[0];
		//String phoneNumber1 = arrOfStr3[1];
		System.out.println(arrOfStr3.length);
		// long total_days ;
		// String todaysDate;
		// for (int i = 1; i>= arrOfStr3.length; i++) {
		for (String a : arrOfStr3) {
			JSONObject request = new JSONObject();
			request.put("from", "etranzact");
			request.put("to", a);
			request.put("terminalid", "ETZSMS");
			// request.put("text", total_days + " days from today's date of " + todaysDate +
			// " to expiry date of " + expiry_date);
			request.put("text", "The expiry date of " + appName + " is " + expiry_date);
			System.out.println(a);
			payload = request.toString();

			DoRequest.sendJSONPost(Url, payload);
		}
		return appName;

	}
	
	public static String Email_call(String appName, String recepient, String expiry_date) throws Exception {
		try (FileReader reader = new FileReader(
				"C:\\Users\\rhema.adedipe\\eclipse-workspace\\expiration_reminder\\config.properties")) {
			Properties properties = new Properties();
			properties.load(reader);
			recepient = properties.getProperty("recepient_email");
			Url = properties.getProperty("email.url");

		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] arrOfStr3 = recepient.split("#");
		recepient = arrOfStr3[0];
		//String recepient1 = arrOfStr3[1];
		System.out.println(arrOfStr3.length);
		// long total_days ;
		// String todaysDate;
		// for (int i = 1; i>= arrOfStr3.length; i++) {
		for (String a : arrOfStr3) {
			JSONObject request = new JSONObject();
			request.put("recepient", recepient);
			request.put("sender", "rhema.adedipe@etranzact.com.gh");
			request.put("subject", "Certificate Expiry Date Update");
			request.put("message", "The expiry date of " + appName + " is " + expiry_date);
			// request.put("text", total_days + " days from today's date of " + todaysDate +
			// " to expiry date of " + expiry_date);
			//request.put("text", "The expiry date of " + appName + " is " + expiry_date);
			System.out.println(a);
			payload = request.toString();

			DoRequest.sendJSONPost(Url, payload);
		}
		return appName;

	}
	
	/*
	 * public static String Database_call(String appName,String expiryDate) {
	 * 
	 * Connection con = DBConnection.dbConnection(); String sql =
	 * "Insert into CertManagerDB values(?,?)"; try { PreparedStatement statement =
	 * con.prepareStatement(sql);
	 * 
	 * statement.setString(1, appName); statement.setString(2, expiryDate);
	 * 
	 * statement.executeUpdate();
	 * 
	 * } catch (Exception e) { System.out.println(e); } return appName;
	 * 
	 * 
	 * }
	 */
	
		
	public static String Database_call(String appName, String expiry_date) {
		try {
			
			
		    connection = DBConnection.dbConnection();
			if(connection!= null) {
				
				System.out.println("Connected to the database.");
				//Insert statement
				String sql ="INSERT INTO amard.log(name, description, type, monitor_group, server_ip, scheduler_interval, error, monitor_id,tat) VALUES(?,?,?,?,?,?,?,?,?)";
				
			    statement = connection.prepareStatement(sql);
				statement.setString(1, appName+" CERTIFICATE EXPIRY CHECKER");
				statement.setString(2, "This app will expire on" + expiry_date );
				statement.setString(3, "Certificate Expiry");
				statement.setString(4, "Certificates");
				statement.setString(5, "172.16.30.10");
				statement.setInt(6, 16);
				statement.setInt(7, 0);
				statement.setInt(8, 150);
				statement.setInt(9, 1750);
				
				int rows = statement.executeUpdate();
				
				
				if (rows>0) {
					
					System.out.println("New details have been inserted successfully.");
					return successful ;
				}
				
				
				
			}}catch(SQLException ex) {
				ex.printStackTrace();
				
			}
	
		finally {
				try {
		            if (connection != null) 
		            {
		            	
		                connection.close();
		            }
		            if (statement != null) {
		                statement.close();
		            }
		        } catch (SQLException sqlee) {
		            sqlee.printStackTrace();
		        }
				
			
				
			}
		return failed;
		
		
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		  try {   
			CertsDataManager(app1, expiryDate1, phoneNumber1); 
			/*
			 * System.out.println("Hurray!!! My Scheduler is working");
			 * System.out.println("The time now is " + new Date());
			 */   
			  } catch (Exception e) {
				 
				  
		  e.printStackTrace(); 
		  }
		 
		 
		
	}
	
		
	}
	

/*
 * public static RequestObjectModel SMS_call(String phoneNumber) throws
 * IOException, SQLException {
 * 
 * 
 * RequestObjectModel sms_details = new RequestObjectModel();
 * sms_details.setFrom("etranzact"); sms_details.setTo(phoneNumber);
 * sms_details.setTerminalid("ETZSMS"); sms_details.
 * setText("Hello Jane Roger,\r\nPlease use the following credentials to Login to the web portal. \r\nhttp://webpay.etranzactgh.com:80/sugarwallet/login\r\nusername: 166340\r\npassword: [C@5ea2aab]"
 * );
 * 
 * Gson gson = new Gson(); String response = gson.toJson(sms_details); return
 * response;
 * 
 * DAO.add(sms_details); return sms_details;
 * 
 * } }
 */
