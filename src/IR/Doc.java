package IR;

public class Doc {
	
	private String title;
	private String content;
	private String path;
	

	public Doc(String  title, String content,String path) {
		this.title = title;
		this.content = content;
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


}
