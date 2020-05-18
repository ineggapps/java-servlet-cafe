package com.cafe.members;

public class CardDTO {
	private int cardNum;
	private int userNum;
	private int modelNum;
	private String cardIdentity;

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public int getModelNum() {
		return modelNum;
	}

	public void setModelNum(int modelNum) {
		this.modelNum = modelNum;
	}

	public String getCardIdentity() {
		return cardIdentity;
	}

	public void setCardIdentity(String cardIdentity) {
		this.cardIdentity = cardIdentity;
	}

	public CardDTO() {
	}

	public CardDTO(int cardNum, int userNum, int modelNum, String cardIdentity) {
		this.cardNum = cardNum;
		this.userNum = userNum;
		this.modelNum = modelNum;
		this.cardIdentity = cardIdentity;
	}

	@Override
	public String toString() {
		return "CardDTO [cardNum=" + cardNum + ", userNum=" + userNum + ", modelNum=" + modelNum + ", cardIdentity="
				+ cardIdentity + "]";
	}
	
}
