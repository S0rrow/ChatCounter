package edu.hanodng.csee.java.hw3;

import java.util.ArrayList;
import java.util.Scanner;

public class MainHW3 {
	ArrayList<String> directories = new ArrayList<String>();
	FileManager filemanager = new FileManager();
	MessageManager messagemanager = new MessageManager();
	public static void main(String[] args) {
		MainHW3 actor = new MainHW3();
		actor.acto();
	}
	
	private void acto() {
		directories = setDir();
		for(int i = 0; i< directories.size(); i++) {
			filemanager.ScanFile(directories.get(i));
		}
		messagemanager = filemanager.getMessageManager();
		ArrayList<String> chats = messagemanager.getArray("lines");
		for(int i = 0; i < chats.size(); i++) {
			System.out.println(chats.get(i));
		}
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
