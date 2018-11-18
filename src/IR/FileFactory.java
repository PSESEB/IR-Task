package IR;

import java.io.File;
import java.util.ArrayList;

/**
 * Factory Class to parse Files and explore Folder structure
 * @author weenzeal, Michael
 *
 */
public class FileFactory {

	/**
	 * Typical Factory Method to parse Files to Doc Instances
	 * @param path Path to document that should be parsed
	 * @return Doc Instance of parsed Document
	 */
	public static Doc getInstance(String path) {
		
		String[] parsed = new String[2];
		if(isTextFile(path)) {
			parsed = parseText(path);
		}else if(isHTMLFile(path)){
			parsed = parseHTML(path);
		}else {
			System.out.println("Something is wrong with the Traversal Method! Non txt or html files Found!");
			System.exit(1);
		}
		
		return new Doc(parsed[0],parsed[1],path);
	}
	

	/**
	 * Extracts content + name from txt file
	 * @param path Parse Text
	 * @return name + content in Array
	 */
	private static String[] parseText(String path) {
		
		return new String[2];
	}
	
	/**
	 * Extracts content + title form HTML file
	 * @param path Parse HTML
	 * @return title + content in Array
	 */
	private static String[] parseHTML(String path) {
	
		return new String[2];
	}
	
	/**
	 * Recursively traverses Folder Structure and adds txt and html files to list
	 * @param startFolder
	 * @return List of all txt and html Files in complete Folder Structure starting at start Folder
	 */
	public static ArrayList<String> traverseFolderStructure(String startFolder){
		
		File folder = new File(startFolder);
		File[] directoryList = folder.listFiles();

		ArrayList<String> fileList = new ArrayList<String>();
		
		for (File f : directoryList) {
		  if (f.isFile()) {
			if(isTextFile(f.getName()) || isHTMLFile(f.getName())) {
				
				fileList.add(f.getAbsolutePath());
			}
		  } else if (f.isDirectory()) {
		   fileList.addAll(traverseFolderStructure(f.getAbsolutePath()));
		  }
		}
		
		return fileList;
	}
	
	/**
	 * Checks if file is txt file
	 * @param file
	 * @return true if txt else false
	 */
	private static boolean isTextFile(String file) {
		file = file.toLowerCase();
		if(file.lastIndexOf('.') > 0 && file.substring(file.lastIndexOf('.')+1).equals("txt")) {
			return true;
		}
		return false;
			
	}
	
	/**
	 * Checks if file is html file
	 * @param file
	 * @return true if html else false
	 */
	private static boolean isHTMLFile(String file) {
		file = file.toLowerCase();
		if(file.lastIndexOf('.') > 0) {
		String extension = file.substring(file.lastIndexOf('.')+1);
		if(extension.equals("html") || extension.equals("htm")) {
			return true;
		}
		}
		return false;
			
	}
}
