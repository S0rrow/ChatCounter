package edu.handong.csee.java.hw3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
	MessageManager manager = new MessageManager();
	MessageMapper mapper = new MessageMapper();
	
	String inputPath;
	int numThreads;
	String outputPath;
	
	public static void main(String[] args) {
		MainHW3 actor = new MainHW3();
		actor.acto(args);
	}
	
	private void acto(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			
			System.out.println("You provided \"" + inputPath + "\" as the value of the option i");
			
		}
		
		directories = setDir(inputPath);
		ThreadPool tp = new ThreadPool(numThreads);
		HashMap<String, Integer> inputdata = new HashMap<String, Integer>();
		
		for(String filename:directories) {
			filemanager.ScanFile(inputPath+"\\"+filename);
			try {
				inputdata = tp.addMap(filemanager.getUser());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		TreeMap<Integer, String> sorteddata = new TreeMap<Integer, String>();
		
		for(String key:inputdata.keySet()) {
			Integer value = inputdata.get(key);
			sorteddata.put(value, key);
		}
		
		filemanager.TreeMapWriteFile(sorteddata, "chatcount.csv");
	}
	
	private ArrayList<String> setDir(String inputPath) {
		File directory = new File(inputPath);
		File[] directories = directory.listFiles();
		ArrayList<String> names = new ArrayList<String>();
		for(File tempFile:directories) {
			names.add(tempFile.getName());
		}
		return names;
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			inputPath = cmd.getOptionValue("i");
			numThreads = Integer.parseInt(cmd.getOptionValue("c"));
			outputPath = cmd.getOptionValue("o");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}
		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("i").longOpt("path")
				.desc("Set a path of a directory or file to parse")
				.hasArg()
				.argName("Path name to get input files to parse")
				.required()
				.build());
		
		options.addOption(Option.builder("c").longOpt("numThreads")
				.desc("Set the number of threads")
				.hasArg()
				.argName("Thread number to display")
				.required()
				.build());
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set the directory to get the output file placed")
				.hasArg()
				.argName("Path name to get output file placed")
				.required()
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
