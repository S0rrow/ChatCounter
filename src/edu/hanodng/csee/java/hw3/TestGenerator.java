package edu.hanodng.csee.java.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TestGenerator {

	ArrayList<String> names = new ArrayList<String>();
	HashMap<String, Integer> output = new HashMap<String, Integer>();
	FileManager manager = new FileManager();
	int size = 0;
	int[] count = {3,4,2};
	
	public static void main(String[] args) {
		TestGenerator actor = new TestGenerator();
		actor.acto();
	}
	
	private void acto() {
		//testerArrayList("Liel");
		//testerArrayList("Doyoung");
		//testerArrayList("Nyam");
		//testerHashMap(names, count);
		//manager.HashMapWriteFile(output, "output.csv");
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Type in the directory of file: ");
		ArrayList<String> names = new ArrayList<String>();
		names.add(keyboard.nextLine());
		for(int i = 0;i<names.size(); i++) {
			System.out.println(i);
			System.out.println(names.get(i));
		}
		keyboard.close();
	}
	
	private void testerArrayList(String name) {
		names.add(name);
		size++;
	}
	
	private void testerHashMap(ArrayList<String> names, int[] count) {
		for(int i = 0; i<names.size();i++) {
			output.put(names.get(i), count[i]);
		}
	}
}
