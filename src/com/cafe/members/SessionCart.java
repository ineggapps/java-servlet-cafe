package com.cafe.members;

import java.util.ArrayList;
import java.util.List;

import com.cafe.menu.MenuDTO;

public class SessionCart {
	private int totalPaymentAmount;
	private List<MenuDTO> items = new ArrayList<>();// 장바구니

	public int getTotalPaymentAmount() {
		return totalPaymentAmount;
	}

	public void setTotalPaymentAmount(int totalPaymentAmount) {
		this.totalPaymentAmount = totalPaymentAmount;
	}
	

	public List<MenuDTO> getItems() {
		return items;
	}

	public void addItem(MenuDTO item) {
		items.add(item);
		totalPaymentAmount += item.getPrice();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("=================\n");
		for(MenuDTO dto: items) {
			s.append(dto+"\n");
		}
		s.append("=================\n");
		return "SessionCart [totalPaymentAmount=" + totalPaymentAmount + ", items=" + s.toString() + "]";
	}

}
