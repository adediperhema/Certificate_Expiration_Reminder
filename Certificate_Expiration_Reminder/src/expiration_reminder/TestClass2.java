package expiration_reminder;

import java.io.FileReader;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class expiration_reminder2 implements Job {
	static String c;
	static String app;
	static String[] arrOfStr2;

    public long daysBetween(Date one, Date two) {
    	
    	long difference = (one.getTime() - two.getTime())/86400000; //86400000 is the number of milliseconds in a day
        return Math.abs(difference);
    }
	public static void main(String args[]) throws ParseException 
    { 

    	try (FileReader reader = new FileReader("C:\\Users\\rhema.adedipe\\eclipse-workspace\\expiration_reminder\\config.properties")) {
    		Properties properties = new Properties();
    		properties.load(reader);
    		app = properties.getProperty("app");
    		
    	}	 catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    
        String[] arrOfStr = app.split("#"); 
        
       
		for (String a : arrOfStr) {

			// System.out.println(a);
			arrOfStr2 = a.split("~");
			//String appName = arrOfStr2[0];

			String date = arrOfStr2[1];
			//LocalDate currentDate = LocalDate.now();
			//System.out.println(currentDate.getDayOfMonth());
	
			//currentDate.getDayOfMonth();

			// String sDate1="31/12/1998";
			/*
			 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			 * LocalDate date2 = LocalDate.parse(date, formatter);
			 */
			//int y = currentDate.getDayOfMonth() - date2.getDayOfMonth();
			//System.out.println(date+"\t"+ y);

			/*
			 * if (currentDate.getDayOfMonth() - date2.getDayOfMonth() <= 7) {
			 * 
			 * }
			 */
            
			
			
			  Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(date);
			  
			  Date today = new Date();
			  Calendar myNextCalendar = Calendar.getInstance();
			  myNextCalendar.setTime(date1); 
			  Date d = myNextCalendar.getTime();
			  expiration_reminder2 obj = new expiration_reminder2();
			  long days = obj.daysBetween(today, d);
			  long days2 = days + 1;
			  
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			  String todaysDate = sdf.format(today);
			  String newDate = sdf.format(d);
			  
			  if (days2 <= 7) {
			  System.out.println(days2+ "days from today's date of "+todaysDate + " to expiry date of "+ newDate );
			  
			 
		}}

		/*
		 * for(String b : arrOfStr2) {
		 * 
		 * System.out.println(b);
		 * 
		 * }
		 */

		// currentDate.getDayOfMonth();
		// String date2 = date.toString();
		// for(arrOfStr2[1].length() >= 3) {
		// for(int i = 0; i<=date[3]; i++)

	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Hurray!!! My Scheduler is working");
		System.out.println("The time now is " + new Date());
		
	}


}
       


