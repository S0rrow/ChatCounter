package edu.hanodng.csee.java.hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
			//messagemanager.MessageParser();
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
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(output));
			for(String key:data.keySet()) {
				Integer value = data.get(key);
				outputStream.writeUTF(key+","+value+"\n");
			}
			outputStream.close();
		} catch (IOException e) {
			System.out.println("Problem with output to file " + output);
		}
	}
	
	public void ArrayListWriteFile(ArrayList<String> data, String output) {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(output));
			for(int i = 0; i < data.size(); i++) {
				outputStream.writeUTF(data.get(i));
			}
			outputStream.close();
		} catch (IOException e) {
			System.out.println("Problem with output to file " + output);
		}
	}
	
	public MessageManager getMessageManager() {
		return messagemanager;
	}
}
