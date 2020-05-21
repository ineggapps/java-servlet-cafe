package com.cafe.members;

public class ErrorMessage {
	private String title;
	private String content;

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

	public ErrorMessage(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
