package edu.hanodng.csee.java.hw3;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageManager {
	String oneline = "";
	int line = 0;
	ArrayList<String> lines = new ArrayList<String>();
	ArrayList<String> nicknames = new ArrayList<String>();
	ArrayList<String> messages = new ArrayList<String>();
	ArrayList<String> datetime = new ArrayList<String>();
	ArrayList<String> currentdate = new ArrayList<String>();
	
	public void getLine(String cache) {
		lines.add(cache);
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public void MessageParser() {
		int curYear = -1;
		int curMonth = -1;
		int curDay = -1;
		String curDate = "";
		String pattern1 = "-+\\s[0-9]+.\\s[0-9]+.\\s[0-9]+.+";
		String pattern2 = ".+,\\s(.+)\\s([0-9]),\\s([0-9]+)\\s.+";
		String pattern3 = "(\\[.+\\])\\s(\\[.+\\])\\s.+";
		for(int i = 0; i < lines.size(); i++) {
			curYear = -1;
			curMonth = -1;
			curDay = -1;
			curDate = "";
			oneline = lines.get(i);
			
			if(oneline.matches(pattern1)) {
				Pattern p = Pattern.compile(pattern1);
				Matcher matcher = p.matcher(oneline);
				if(matcher.find()) {
					curYear = Integer.parseInt(matcher.group(1));
					curMonth = Integer.parseInt(matcher.group(2));
					curDay = Integer.parseInt(matcher.group(3));
				}
				curDate = getCurrentDate(curYear, curMonth, curDay);
			} else if(oneline.matches(pattern2)) {
				Pattern p = Pattern.compile(pattern2);
				Matcher matcher = p.matcher(oneline);
				if(matcher.find()) {
					curMonth = getMonthNumberFromString(matcher.group(1));
					curDay = Integer.parseInt(matcher.group(2));
					curYear = Integer.parseInt(matcher.group(3));
				}
				curDate = getCurrentDate(curYear, curMonth, curDay);
			}
			
			if(oneline.matches(pattern3)) {
				Pattern p = Pattern.compile(pattern3);
				Matcher matcher = p.matcher(oneline);
				if(matcher.find()) {
					nicknames.add(matcher.group(1));
					datetime.add(matcher.group(2));
					messages.add(matcher.group(3));
				}
			}
			
			currentdate.add(curDate);
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
		case "datetime": return datetime;
		case "currentdate": return currentdate;
		case "lines": return lines;
		default: return null;
		}
	}
	
	public void MessageRebundancyChecker() {
		
	}
	
	public void CrossChecker() {
		
	}

}
