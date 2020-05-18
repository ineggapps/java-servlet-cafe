package com.cafe.members;

public class CardDTO {
	private int cardNum;
	private String cardName;
	private int userNum;
	private int modelNum;
	private String cardIdentity;
	private int balance; // 최대 충전 잔액은 55만원까지만 가능하다.

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

	public CardDTO() {
	}

	public CardDTO(int cardNum, int userNum, int modelNum, String cardIdentity) {
		this.cardNum = cardNum;
		this.userNum = userNum;
		this.modelNum = modelNum;
		this.cardIdentity = cardIdentity;
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
