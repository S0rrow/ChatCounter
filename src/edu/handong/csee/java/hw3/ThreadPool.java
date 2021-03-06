package edu.handong.csee.java.hw3;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * 
 * @author s0rrow
 *
 */
public class ThreadPool{
	
	int numThreads;
	private ArrayList<ThreadRunner> threads = new ArrayList<ThreadRunner>();
	MessageMapper mapper;
	private boolean active = true;
	
	public ThreadPool(int numThreads) {// initiate thread pool.
		this.numThreads = numThreads;
		mapper = new MessageMapper();
		for(int i = 0; i < numThreads; i++) {
			threads.add(new ThreadRunner(i));
		}
		for(ThreadRunner thread: threads) {
			new Thread(thread).start();
		}
	}
	
	public synchronized HashMap<String, Integer> addMap(HashMap<String[], String> userdata) throws Exception{
		if(!active) throw new Exception("Thread pool inactive");
		mapper.setUser(userdata);
		return mapper.getMap();
	}
	
	public synchronized void stop() throws InterruptedException {
		active = false;
		for( ThreadRunner r : threads ){
			r.stop();
		}
	}
}

