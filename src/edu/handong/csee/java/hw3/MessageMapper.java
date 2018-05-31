package edu.handong.csee.java.hw3;

import java.util.HashMap;
/**
 * 
 * @author s0rrow
 *
 */
public class MessageMapper {
	HashMap<String, Integer> chatRedundancy = new HashMap<String, Integer>();// nickname, redundancy
	HashMap<String[], String> userdata = new HashMap<String[], String>();// nickname, date and time, message
	String[] identifier = new String[3];
	int initiator = 0;
	public void mapUser(String nickname, String datetime, String message) {
		identifier[0] = nickname;
		identifier[1] = datetime;
		userdata.put(identifier, message);
	}
	
	
	public HashMap<String, Integer> getMap(){
		for(String[] whoandwhen:userdata.keySet()) {
			String nickname = whoandwhen[0];
			chatRedundancy.merge(nickname,1,Integer::sum);
		}
		return chatRedundancy;
	}
}
