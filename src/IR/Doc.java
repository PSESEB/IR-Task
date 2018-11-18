package IR;

public class Doc {
	
	private String title;
	private String content;
	private String path;
	

	/**
	 * Constructor
	 * @param title Title of File
	 * @param content Content
	 * @param path File Path
	 */
	public Doc(String  title, String content,String path) {
		this.title = title;
		this.content = content;
		this.path = path;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return content
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}


}
