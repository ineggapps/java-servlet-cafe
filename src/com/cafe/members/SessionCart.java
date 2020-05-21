package com.cafe.members;

import java.util.ArrayList;
import java.util.List;

import com.cafe.menu.MenuDTO;

public class SessionCart {
	private int totalPaymentAmount;
	private int totalQuantity;
	private List<MenuDTO> items = new ArrayList<>();// 장바구니

	public int getTotalPaymentAmount() {
		// 총액 계산
		totalPaymentAmount = 0;
		if (items != null && items.size() > 0) {
			for (MenuDTO item : items) {
				totalPaymentAmount += item.getPrice() * item.getQuantity();
			}
		}
		return totalPaymentAmount;
	}

	public int getTotalQuantity() {
		totalQuantity = 0;
		if (items != null && items.size() > 0) {
			for (MenuDTO item : items) {
				totalQuantity += item.getQuantity();
			}
		}
		return totalQuantity;
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
		for (MenuDTO dto : items) {
			s.append(dto + "\n");
		}
		s.append("=================\n");
		return "SessionCart [totalPaymentAmount=" + totalPaymentAmount + ", items=" + s.toString() + "]";
	}

}
