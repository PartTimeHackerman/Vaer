package vaer.model;

import java.util.*;

public class TimeInterval {
	
	private static Map<Long, Jobs> jobsMap = new HashMap<>();
	private static Timer timer = new Timer();
	private static Long minPeroid = Long.MAX_VALUE;
	private static TimerTask updateJobs = new TimerTask() {
		@Override
		public void run() {
			jobsMap.forEach((peroid, jobs) -> jobs.addTime(minPeroid));
		}
	};
	
	public static Job addInterval(Runnable runnable, Long peroid) {
		Jobs jobs = jobsMap
				.getOrDefault(peroid, new Jobs(peroid));
		jobsMap.putIfAbsent(peroid, jobs);
		
		Job job = jobs.addRunnable(runnable);
		
		if (peroid < minPeroid) {
			minPeroid = peroid;
			if (timer != null)
				try {
					timer.cancel();
				} catch (Exception ignored) {
				}
			timer = new Timer();
			timer.scheduleAtFixedRate(updateJobs, 0L, minPeroid);
		}
		return job;
	}
	
	public static class Jobs {
		Long maxTime;
		Long elapsedTime = 0L;
		List<Job> jobs = new ArrayList<>();
		
		Jobs(Long maxTime) {
			this.maxTime = maxTime;
		}
		
		Job addRunnable(Runnable runnable) {
			Job job = new Job(this, runnable);
			jobs.add(job);
			return job;
		}
		
		void removeJob(Job job) {
			jobs.remove(job);
		}
		
		void addTime(Long time) {
			elapsedTime += time;
			runJobsIfTimeIsRight();
		}
		
		void runJobsIfTimeIsRight() {
			if (elapsedTime >= maxTime) {
				elapsedTime = 0L;
				for (int i = 0; i < jobs.size(); i++) {
					jobs.get(i).runnable.run();
				}
			}
		}
	}
	
	public static class Job {
		Jobs jobs;
		Runnable runnable;
		
		Job(Jobs jobs, Runnable runnable) {
			this.jobs = jobs;
			this.runnable = runnable;
		}
		
		public void cancel() {
			jobs.removeJob(this);
		}
		
	}
}
