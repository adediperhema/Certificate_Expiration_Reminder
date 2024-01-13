package expiration_reminder;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobObject implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		System.out.println("Hurray!!! My Scheduler is working");
		System.out.println("The time now is"+ new Date());
		
	}

	
}
