package com.cafe.members;

public class OrderHistoryDTO {
	private int orderNum;
	private int totalPaymentAmount;
	private int storeNum;
	private int statusNum;
	private int userNum;
	private int cardNum;
	private String order_Date;
	private int cancelNum = -1;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getTotalPaymentAmount() {
		return totalPaymentAmount;
	}

	public void setTotalPaymentAmount(int totalPaymentAmount) {
		this.totalPaymentAmount = totalPaymentAmount;
	}

	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}

	public int getStatusNum() {
		return statusNum;
	}

	public void setStatusNum(int statusNum) {
		this.statusNum = statusNum;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public String getOrder_Date() {
		return order_Date;
	}

	public void setOrder_Date(String order_Date) {
		this.order_Date = order_Date;
	}

	public int getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(int cancelNum) {
		this.cancelNum = cancelNum;
	}

}
