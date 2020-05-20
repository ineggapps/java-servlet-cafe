package com.cafe.members;

import java.util.List;

public class OrderHistoryDTO {
	private int orderNum;
	private int totalPaymentAmount;
	private int storeNum;
	private int statusNum;
	private String statusName;
	private int userNum;
	private int cardNum;
	private String orderDate;
	private int cancelNum = -1;
	private List<OrderDetailDTO> items;

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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(int cancelNum) {
		this.cancelNum = cancelNum;
	}

	public List<OrderDetailDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderDetailDTO> items) {
		this.items = items;
	}

}
