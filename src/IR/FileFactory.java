package IR;

public class FileFactory {

	public static Doc getInstance(String path) {
		
		
		return new Doc("name","content",path);
	}
}
