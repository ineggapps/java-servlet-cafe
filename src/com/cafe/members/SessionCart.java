package com.cafe.members;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cafe.menu.MenuDTO;

public class SessionCart {
	private int totalPaymentAmount;
	private int totalQuantity;
	private Map<Integer, MenuDTO> items = new HashMap<>();// 장바구니

	public int getTotalPaymentAmount() {
		// 총액 계산
		totalPaymentAmount = 0;
//		if (items != null && items.size() > 0) {
//			for (MenuDTO item : items) {
//				totalPaymentAmount += item.getPrice() * item.getQuantity();
//			}
//		}
		for(int menuNum: items.keySet()) {
			MenuDTO dto = items.get(menuNum);
			if(dto==null) {
				items.remove(menuNum);
				continue;
			}
			int price = dto.getPrice();
			int quantity = dto.getQuantity();
			totalPaymentAmount += price * quantity;
		}
		return totalPaymentAmount;
	}

	public int getTotalQuantity() {
		totalQuantity = 0;
		if (items != null && items.size() > 0) {
//			for (MenuDTO item : items) {
//				totalQuantity += item.getQuantity();
//			}
			for (int menuNum: items.keySet()) {
				totalQuantity += items.get(menuNum).getQuantity();
			}
		}
		return totalQuantity;
	}

	
	public Map<Integer, MenuDTO> getItems() {
		return items;
	}
	
	public MenuDTO getItem(int menuNum) {
		return items.get(menuNum);
	}
	
	public void addItem(MenuDTO item) {
//		items.add(item);
		MenuDTO dto = items.get(item.getMenuNum());
		if(dto==null) {
			//비어 있으면 새로 넣기
			items.put(item.getMenuNum(), item);
		}

		totalPaymentAmount += item.getPrice();
	}

	@Override
	public String toString() {
		return "SessionCart [totalPaymentAmount=" + totalPaymentAmount + ", totalQuantity=" + totalQuantity + ", items="
				+ items + "]";
	}

}
