/**
 * 
 */
package expiration_reminder;

/**
 * @author rhema.adedipe
 *
 */
import java.io.FileReader;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;


public class DBConnection {
	
	private static Connection connection ;
	public static Connection dbConnection( ){
		

	
	
	try (FileReader reader = new FileReader("C:\\Users\\rhema.adedipe\\eclipse-workspace\\expiration_reminder\\config.properties")) {
		Properties properties = new Properties();
		properties.load(reader);
	    //Httpclient_url = properties.getProperty("config.url");
		String jdbcURL = properties.getProperty("jdbcURL");
		String dbusername = properties.getProperty("dbusername");
		String password = properties.getProperty("password");
		//sql_query = properties.getProperty("sql");
		//System.out.println(Httpclient_url);
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    connection = DriverManager.getConnection(jdbcURL,dbusername,password);

		
	}	 catch (Exception e) {
		e.printStackTrace();
	}
	return connection;
	
}
}