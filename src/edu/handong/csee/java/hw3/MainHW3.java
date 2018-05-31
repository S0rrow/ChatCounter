package edu.handong.csee.java.hw3;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
/**
 * 
 * @author s0rrow
 *
 */
public class MainHW3 {
	
	ArrayList<String> directories = new ArrayList<String>();
	FileManager filemanager = new FileManager();
	MessageManager messagemanager = new MessageManager();
	
	public static void main(String[] args) {
		MainHW3 actor = new MainHW3();
		actor.acto(args);
	}
	
	private void acto(String[] args) {
		directories = setDir();
		for(int i = 0; i< directories.size(); i++) {
			filemanager.ScanFile(directories.get(i));
		}
		messagemanager = filemanager.getMessageManager();
		HashMap<String, Integer> inputdata = messagemanager.returnMap();
		TreeMap<Integer, String> sorteddata = new TreeMap<Integer, String>();
		for(String key:inputdata.keySet()) {
			Integer value = inputdata.get(key);
			sorteddata.put(value, key);
		}
		filemanager.TreeMapWriteFile(sorteddata, "chatcount.csv");
	}
	
	private ArrayList<String> setDir() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Type in the directory of file: ");
		ArrayList<String> names = new ArrayList<String>();
		names.add(keyboard.nextLine());
		keyboard.close();
		return names;
	}
}
