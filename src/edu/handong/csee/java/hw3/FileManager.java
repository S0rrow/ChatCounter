package edu.handong.csee.java.hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
/**
 * 
 * @author s0rrow
 *
 */
public class FileManager {
	MessageManager messagemanager = new MessageManager();
	
	public void ScanFile(String fileName) {
		File file = new File(fileName);
		try {
			BufferedReader BR = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			String cache = "";
			int line = 0;
			while((cache = BR.readLine())!= null) {
				messagemanager.getLine(cache);
				line++;
			}
			messagemanager.setLine(line);
			messagemanager.parseMessage();
			BR.close();
		} catch(FileNotFoundException e) {
			System.out.println("Error, File not found: "+ e.getMessage());
			
		}
		catch (IOException e) {
			System.out.println("Error, IOException: "+ e.getMessage());
		}
	}
	
	
	public void HashMapWriteFile(HashMap<String, Integer> data, String output) {
		try {
			FileWriter writer = new FileWriter(output);
			for(String key:data.keySet()) {
				Integer value = data.get(key);
				writer.append(key+","+value+"\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Problem with output to file " + output);
		}
	}
	
	public void TreeMapWriteFile(TreeMap<Integer, String> data, String output) {
		try {
			FileWriter writer = new FileWriter(output);
			for(Integer value:data.keySet()) {
				String key = data.get(value);
				writer.append(key+","+value+"\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Problem with output to file " + output);
		}
	}
	
	public void ArrayListWriteFile(ArrayList<String> data, String output) {
		try {
			FileWriter writer = new FileWriter(output);
			for(int i = 0; i < data.size(); i++) {
				writer.append(data.get(i)+","+"\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Problem with output to file " + output);
		}
	}
	
	public HashMap<String[], String> getUser() {
		return messagemanager.getUser();
	}
}
