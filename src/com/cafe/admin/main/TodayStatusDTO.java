package com.cafe.admin.main;

public class TodayStatusDTO {
	private String todayMenuName; // 오늘의 효자품목
	private String thumbnail;
	private int todayTotalSales;// 오늘의 매출액

	public String getTodayMenuName() {
		return todayMenuName;
	}

	public void setTodayMenuName(String todayMenuName) {
		this.todayMenuName = todayMenuName;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getTodayTotalSales() {
		return todayTotalSales;
	}

	public void setTodayTotalSales(int todayTotalSales) {
		this.todayTotalSales = todayTotalSales;
	}

}
