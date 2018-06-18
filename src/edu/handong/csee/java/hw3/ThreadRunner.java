package edu.handong.csee.java.hw3;

public class ThreadRunner implements Runnable{
	int identifier = -1;
	private boolean active = true;
	MessageMapper mapper = new MessageMapper();
	
	public ThreadRunner(int identifier) {
		this.identifier = identifier;
	}
	
	public void run() {
		while(active) {
			try {
				Thread.sleep(1000);
				Runnable r = (Runnable) mapper.getUser();
				r.run();
			} catch (InterruptedException e) {
				stop();
			}
		}
	}
	
	public void stop() {
		active = false;
	}
}
