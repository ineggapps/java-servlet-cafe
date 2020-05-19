package com.cafe.members;

public class CardDTO {
	private int cardNum;
	private String cardName;
	private int userNum;
	private int modelNum;
	private String cardIdentity;
	private int balance; // 최대 충전 잔액은 55만원까지만 가능하다.
	private String thumbnail;

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
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

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public CardDTO() {
	}

	public CardDTO(int cardNum, String cardName, int userNum, int modelNum, String cardIdentity, int balance,
			String thumbnail) {
		this.cardNum = cardNum;
		this.cardName = cardName;
		this.userNum = userNum;
		this.modelNum = modelNum;
		this.cardIdentity = cardIdentity;
		this.balance = balance;
		this.thumbnail = thumbnail;
	}

	public CardDTO(String cardName, int userNum, int modelNum) {
		this.cardName = cardName;
		this.userNum = userNum;
		this.modelNum = modelNum;
	}

	public CardDTO(String cardName, int userNum, int modelNum, int balance) {
		this.cardName = cardName;
		this.userNum = userNum;
		this.modelNum = modelNum;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "CardDTO [cardNum=" + cardNum + ", userNum=" + userNum + ", modelNum=" + modelNum + ", cardIdentity="
				+ cardIdentity + "]";
	}

}
