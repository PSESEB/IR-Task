package IR;
import java.io.*;
import java.util.ArrayList;

import org.jsoup.Jsoup;


import org.jsoup.nodes.Document;
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
	public static Doc getInstance(String path){
		
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
	 *
	 */
	private static String[] parseText(String path){
		
		File f = new File(path);
		String name = f.getName().replaceFirst("[.][^.]+$", "");
		BufferedReader br;
		String[] text = new String[2];
		try {
			br = new BufferedReader(new FileReader(path));
		
		
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String content = sb.toString();
		    
		    text[0] = name;
		    text[1] = content;
		    br.close();
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return text;
	}
	
	/**
	 * Extracts content + title form HTML file
	 * @param path Parse HTML
	 * @return title + content in Array
	 * @throws IOException 
	 */
	private static String[] parseHTML(String path)  {
		File file = new File(path);
		String[] html = new String[2];
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		
		
		String st;
		String content="";
		while ((st = br.readLine()) != null) {
			
			content+=st;
		}
		Document doc = Jsoup.parse(content);
		
		String title = doc.title();
        if(title.equals("")) {
        	title = file.getName().replaceFirst("[.][^.]+$", "");
        }
		String body = doc.body().text();
		html[0] = title;
		html[1] = body;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		return html;
		
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
