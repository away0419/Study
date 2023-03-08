package dto;

public class NewsDTO {
	String title;
	String content;
	String reg;
	String url;

	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public NewsDTO(String title, String content, String reg, String url) {
		super();
		this.title = title;
		this.content = content;
		this.reg = reg;
		this.url = url;
	}

	public NewsDTO() {
		super();
	}

	public NewsDTO(String title, String content, String reg) {
		super();
		this.title = title;
		this.content = content;
		this.reg = reg;
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

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	@Override
	public String toString() {
		return "NewsDTO [title=" + title + ", content=" + content + ", reg=" + reg + ", url=" + url + "]";
	}

}
