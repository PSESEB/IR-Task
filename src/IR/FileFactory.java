package IR;

public class FileFactory {

	public static Doc getInstance(String path) {
		
		
		return new Doc("name","content",path);
	}
	
	private static String[] parseText(String path) {
		
	}
	
	private static String[] parseHTML(String path) {
	
	}
	
	public static ArrayList<String> traverseFolderStructure(String startFolder){
		
		return new ArrayList<String>;
	}
}
