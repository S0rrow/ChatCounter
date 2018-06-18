package edu.handong.csee.java.hw3;

import java.util.HashMap;
/**
 * 
 * @author s0rrow
 *
 */
public class MessageMapper {
	
	HashMap<String, Integer> chatRedundancy = new HashMap<String, Integer>();// nickname, redundancy
	HashMap<String[], String> userdata= new HashMap<String[], String>();// nickname, date and time, message
	
	public void setUser(HashMap<String[], String> userdata) {
		this.userdata.putAll(userdata);
	}
	
	public synchronized HashMap<String[], String> getUser() {
		return userdata;
	}
	
	public HashMap<String, Integer> getMap(){
		for(String[] whoandwhen:userdata.keySet()) {
			String nickname = whoandwhen[0];
			chatRedundancy.merge(nickname,1,Integer::sum);
		}
		return chatRedundancy;
	}
}
