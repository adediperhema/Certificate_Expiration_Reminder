package expiration_reminder;


import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MainScheduler {
	public static void main(String[] args) throws SchedulerException {
		
		/* JobDetail j = JobBuilder.newJob(expiration_reminder2.class).build(); */
		JobDetail j = JobBuilder.newJob(CertificateExpirationReminder.class).build();
		Trigger t = TriggerBuilder.newTrigger().withIdentity("CroneTrigger").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(86400).repeatForever()).build();
        
		Scheduler s = StdSchedulerFactory.getDefaultScheduler();
		s.start();
		s.scheduleJob(j, t);
	}

}
