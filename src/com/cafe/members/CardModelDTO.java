package com.cafe.members;

public class CardModelDTO {
	private int modelNum;
	private String modelName;
	private String text;
	private String thumbnail;

	public int getModelNum() {
		return modelNum;
	}

	public void setModelNum(int modelNum) {
		this.modelNum = modelNum;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	
	
	public CardModelDTO() {
	}

	public CardModelDTO(int modelNum, String modelName, String text, String thumbnail) {
		this.modelNum = modelNum;
		this.modelName = modelName;
		this.text = text;
		this.thumbnail = thumbnail;
	}
	
	

}
