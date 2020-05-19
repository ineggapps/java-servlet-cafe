package com.store;

public class StoreDTO {
	private int storeNum;
	private String storeName;
	private String tel;
	private String storeAddress;
	private boolean isVisible;

	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public String toString() {
		return "StoreDTO [storeNum=" + storeNum + ", storeName=" + storeName + ", tel=" + tel + ", storeAddress="
				+ storeAddress + ", isVisible=" + isVisible + "]";
	}

	public StoreDTO() {
	}

	public StoreDTO(int storeNum, String storeName, String tel, String storeAddress, boolean isVisible) {
		this.storeNum = storeNum;
		this.storeName = storeName;
		this.tel = tel;
		this.storeAddress = storeAddress;
		this.isVisible = isVisible;
	}

}
