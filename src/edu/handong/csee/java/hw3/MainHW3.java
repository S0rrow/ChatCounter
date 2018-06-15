package edu.handong.csee.java.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * 
 * @author s0rrow
 *
 */
public class MainHW3 {
	
	ArrayList<String> directories = new ArrayList<String>();
	FileManager filemanager = new FileManager();
	MessageManager messagemanager = new MessageManager();
	
	String path;
	boolean verbose;
	boolean help;
	
	public static void main(String[] args) {
		MainHW3 actor = new MainHW3();
		actor.acto(args);
	}
	
	private void acto(String[] args) {
		directories = setDir();
		
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			
			System.out.println("You provided \"" + path + "\" as the value of the option p");
			
			if(verbose) {
				System.out.println("Your program is terminated. (This message is shown because you turned on -v option!");
			}
		}
		
		directories.add(path);
		
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
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			path = cmd.getOptionValue("p");
			verbose = cmd.hasOption("v");
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}
		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("p").longOpt("path")
				.desc("Set a path of a directory or a file to display")
				.hasArg()
				.argName("Path name to display")
				.required()
				.build());
		
		options.addOption(Option.builder("v").longOpt("verbose")
				.desc("Display detailed messages!")
				.argName("verbose option")
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Help")
		        .build());

		return options;
	}
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "chat counter program";
		String footer ="\nissues may be reported to https://github.com/S0rrow/HW3";
		formatter.printHelp("HW3", header, options, footer, true);
	}
}
