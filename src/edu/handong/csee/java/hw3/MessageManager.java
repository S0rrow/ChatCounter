package edu.handong.csee.java.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author s0rrow
 *
 */
public class MessageManager {
	String oneline = "";
	int line = 0;
	MessageMapper mapper = new MessageMapper();
	ArrayList<String> lines = new ArrayList<String>();//file's message data
	ArrayList<String> nicknames = new ArrayList<String>();
	ArrayList<String> messages = new ArrayList<String>();
	ArrayList<String> times = new ArrayList<String>();
	ArrayList<String> currentdates = new ArrayList<String>();
	ArrayList<String> datetimes = new ArrayList<String>();
	
	HashMap<String[], String> userdata = new HashMap<String[], String>();
	
	public void getLine(String cache) {
		lines.add(cache);
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public void parseMessage() {
		int curYear = -1;
		int curMonth = -1;
		int curDay = -1;
		String curDate = "";
		
		String nickname = "";
		String time = "";
		String message = "";
		String datetime = "";
		
		String pattern1 = "-+\\s[0-9]+.\\s[0-9]+.\\s[0-9]+.+";
		String subpattern1 = "-+\\s([0-9]+).\\s([0-9]+).\\s+([0-9]+).+";
		String pattern2 = ".+,\\s(.+)\\s([0-9]),\\s([0-9]+)\\s.+";
		String pattern3 = "(\\[.+\\])\\s(\\[.+\\])\\s.+";
		String subpattern3 = "\\[(.+)\\]\\s(\\[.+\\])\\s(.+)";
		String pattern4 = "([0-9]+)\\-([0-9]+)\\-([0-9]+)\\s([0-9]+)\\:([0-9]+)\\:..\\,\\\"(.+)\\\"\\,\\\"(.+)\\\"";
		String pattern5 = "([0-9]+)\\-([0-9]+)\\-([0-9]+)\\s([0-9]+)\\:([0-9]+)\\:..\\,\\\"(.+)\\\"\\,\\\"(.+)";
		
		for(int i = 0; i < lines.size(); i++) {
			curYear = -1;
			curMonth = -1;
			curDay = -1;
			curDate = "";
			oneline = lines.get(i);
			
			if(oneline.matches(pattern1)) {
				Pattern p = Pattern.compile(subpattern1);
				Matcher matcher = p.matcher(oneline);
				if(matcher.find()) {
					//System.out.println("pattern 1");
					//System.out.println(i+":"+matcher.group(1)+","+matcher.group(2)+","+matcher.group(3));
					curYear = Integer.parseInt(matcher.group(1));
					curMonth = Integer.parseInt(matcher.group(2));
					curDay = Integer.parseInt(matcher.group(3));
				}
				curDate = getCurrentDate(curYear, curMonth, curDay);
			} else if(oneline.matches(pattern2)) {
				Pattern p = Pattern.compile(pattern2);
				Matcher matcher = p.matcher(oneline);
				if(matcher.find()) {
					//System.out.println("pattern 2");
					//System.out.println(i+":"+matcher.group(1)+","+matcher.group(2)+","+matcher.group(3));
					curMonth = getMonthNumberFromString(matcher.group(1));
					curDay = Integer.parseInt(matcher.group(2));
					curYear = Integer.parseInt(matcher.group(3));
				}
				curDate = getCurrentDate(curYear, curMonth, curDay);
			}
			
			if(oneline.matches(pattern3)) {
				Pattern p = Pattern.compile(subpattern3);
				Matcher matcher = p.matcher(oneline);
				if(matcher.find()) {
					//System.out.println("pattern 3");
					//System.out.println(i+":"+matcher.group(1));
					
					nicknames.add(nickname = matcher.group(1));
					times.add(time = matcher.group(2));
					messages.add(message = matcher.group(3));
					datetimes.add(datetime = getDateTime(curDate, time));
					mapUser(nickname, datetime, message);
				}
			}//end of loop for sizes.
			
			//parsing part for when file is csv.
			if(oneline.matches(pattern4) || oneline.matches(pattern5)) {
				Pattern p = null;
				
				if(oneline.matches(pattern4)) {
					p = Pattern.compile(pattern4);
				} else if(oneline.matches(pattern5)) {
					p = Pattern.compile(pattern5);
				}
				Matcher matcher = p.matcher(oneline);
				if(matcher.find()) {
					curYear = Integer.parseInt(matcher.group(1));
					curMonth = Integer.parseInt(matcher.group(2));
					curDay = Integer.parseInt(matcher.group(3));
					
					int curHour = Integer.parseInt(matcher.group(4));
					int curMin = Integer.parseInt(matcher.group(5));
					
					curDate = getCurrentDate(curYear, curMonth, curDay);
					
					nicknames.add(nickname = matcher.group(6));
					messages.add(message = matcher.group(7));
					times.add(time = String.valueOf(curHour*60+curMin));
					datetimes.add(datetime = getDateTime(curDate,time));
					mapUser(nickname, datetime, message);
				}
			}
			
			currentdates.add(curDate);
			mapper.getMap();
		}
	}
	
	private String getCurrentDate(int curYear, int curMonth, int curDay) {
		String year = ""+curYear;
		String month = ""+curMonth;
		String day = ""+curDay;
		return year+month+day;
	}
	
	private int getMonthNumberFromString(String month) {
		switch(month) {
		case "January": return 1;
		case "February": return 2;
		case "March": return 3;
		case "April": return 4;
		case "May": return 5;
		case "June": return 6;
		case "July": return 7;
		case "August": return 8;
		case "September": return 9;
		case "October": return 10;
		case "November": return 11;
		case "December": return 12;
		default: return 0;
		}
	}
	
	
	public ArrayList<String> getArray(String arrayname){
		switch(arrayname) {
		case "nicknames": return nicknames;
		case "messages": return messages;
		case "time": return times;
		case "datetime": return datetimes;
		case "currentdate": return currentdates;
		case "lines": return lines;
		default: return null;
		}
	}
	

	private String formatFor2Digit(int i) {
		if(i>=10) return Integer.toString(i);
		else return "0"+i;
	}
	
	private String getDateTime(String curDate, String time) {
		String pattern1 = "\\[(.+)\\s([0-9]+):([0-9]+)\\]";
		String pattern2 = "\\[([0-9]+):([0-9]+)\\s(.+)\\]";
		Pattern p = Pattern.compile(pattern1);
		Matcher matcher = p.matcher(time);
		String meridiem = "";
		String hour = "";
		String min = "";
		
		if(matcher.find()) {
			meridiem = matcher.group(1);
			hour = matcher.group(2);
			min = matcher.group(3);
			if(meridiem.equals("오전")) {
				if(hour.equals("12")) return formatFor2Digit(0)+":"+formatFor2Digit(Integer.parseInt(min));
				return formatFor2Digit(Integer.parseInt(hour))+":"+formatFor2Digit(Integer.parseInt(min));
			}
			if(hour.equals("12")) return formatFor2Digit(Integer.parseInt(hour))+":"+formatFor2Digit(Integer.parseInt(min));
			return formatFor2Digit(Integer.parseInt(hour)+12)+":"+formatFor2Digit(Integer.parseInt(min));
		}
		
		
		p = Pattern.compile(pattern2);
		matcher = p.matcher(time);
		
		if(matcher.find()) {
			meridiem = matcher.group(3);
			hour = matcher.group(1);
			min = matcher.group(2);
			if(meridiem.equals("AM")) {
				if(hour.equals("12")) return formatFor2Digit(0)+":"+formatFor2Digit(Integer.parseInt(min));
				return formatFor2Digit(Integer.parseInt(hour))+":"+formatFor2Digit(Integer.parseInt(min)); 
			}
			if(hour.equals("12")) return formatFor2Digit(Integer.parseInt(hour))+":"+formatFor2Digit(Integer.parseInt(min));
			return formatFor2Digit(Integer.parseInt(hour)+12)+":"+formatFor2Digit(Integer.parseInt(min));
		}
		return null;
	}

	
	public HashMap<String[], String> returnMap(){
		return userdata;
	}
	
	private void mapUser(String nickname, String datetime, String message) {
		String[] identifier = new String[3];
		identifier[0] = nickname;
		identifier[1] = datetime;
		userdata.put(identifier, message);
	}
}
