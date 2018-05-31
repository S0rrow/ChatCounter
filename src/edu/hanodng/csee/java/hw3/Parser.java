package edu.hanodng.csee.java.hw3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	String cache = null;
	
	public void setData(String data) {
		cache = data;
	}
	
	private String parse() {
		int curYear = -1;
		int curMonth = -1;
		int curDay = -1;
		String curDate = "";
		String pattern1 = "-+\\s[0-9]+.\\s[0-9]+.\\s[0-9]+.+";
		String pattern2 = ".+,\\s(.+)\\s([0-9]),\\s([0-9]+)\\s.+";
		String pattern3 = "(\\[.+\\])\\s(\\[.+\\])\\s.+";
		if(cache.matches(pattern1)) {
			Pattern p = Pattern.compile(pattern1);
			Matcher matcher = p.matcher(cache);
			if(matcher.find()) {
				curYear = Integer.parseInt(matcher.group(1));
				curMonth = Integer.parseInt(matcher.group(2));
				curDay = Integer.parseInt(matcher.group(3));
			}
			curDate = getCurrentDate(curYear, curMonth, curDay);
		} else if(cache.matches(pattern2)) {
			Pattern p = Pattern.compile(pattern2);
			Matcher matcher = p.matcher(cache);
			if(matcher.find()) {
				curMonth = getMonthNumberFromString(matcher.group(1));
				curDay = Integer.parseInt(matcher.group(2));
				curYear = Integer.parseInt(matcher.group(3));
			}
			curDate = getCurrentDate(curYear, curMonth, curDay);
		}
		
		if(cache.matches(pattern3)) {
			Pattern p = Pattern.compile(pattern3);
			Matcher matcher = p.matcher(cache);
			if(matcher.find()) {
				String nickname = matcher.group(1);
				String time = matcher.group(2);
				String messagestring = matcher.group(3);
			}
		}
		return curDate;
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
		return formatFor2Digit(Integer.parseInt(hour))+":"+formatFor2Digit(Integer.parseInt(min));
	}
}
