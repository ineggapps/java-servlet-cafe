package com.cafe.members;

public class CardChargeDTO {
	private int chargeNum;
	private int cardNum;
	private int chargeAmount;
	private String chargeDate;

	public int getChargeNum() {
		return chargeNum;
	}

	public void setChargeNum(int chargeNum) {
		this.chargeNum = chargeNum;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public int getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(int chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(String chargeDate) {
		this.chargeDate = chargeDate;
	}

	public CardChargeDTO() {
	}

	public CardChargeDTO(int chargeNum, int cardNum, int chargeAmount, String chargeDate) {
		this.chargeNum = chargeNum;
		this.cardNum = cardNum;
		this.chargeAmount = chargeAmount;
		this.chargeDate = chargeDate;
	}

	public CardChargeDTO(int cardNum, int chargeAmount) {
		this.cardNum = cardNum;
		this.chargeAmount = chargeAmount;
	}

	
}
