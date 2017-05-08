package vaer.model;


import java.util.Timer;
import java.util.TimerTask;

public class TimeInterval {
	
	public Timer addInterval(Runnable runnable, Long peroid) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(getTask(runnable), 0L, peroid);
		return timer;
	}
	
	private TimerTask getTask(Runnable runnable) {
		return new TimerTask() {
			@Override
			public void run() {
				runnable.run();
			}
		};
	}
}
