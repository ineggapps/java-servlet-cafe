package com.cafe.admin.main;

public class OrderCancelDTO {
	private int cancelNum;
	private int orderNum;
	private int paymentAmount;
	private int cardNum;
	private String cancelDate;

	public int getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(int cancelNum) {
		this.cancelNum = cancelNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	@Override
	public String toString() {
		return "OrderCancelDTO [cancelNum=" + cancelNum + ", orderNum=" + orderNum + ", paymentAmount=" + paymentAmount
				+ ", cardNum=" + cardNum + ", cancelDate=" + cancelDate + "]";
	}

}
