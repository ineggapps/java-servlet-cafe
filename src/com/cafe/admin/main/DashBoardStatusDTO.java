package com.cafe.admin.main;

public class DashBoardStatusDTO {
	private int paymentCount; // 결제 완료
	private int beforeMakingCount; //제조 대기
	private int makingCount; // 제조 중
	private int doneCount; // 제조 완료

	public int getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(int paymentCount) {
		this.paymentCount = paymentCount;
	}

	public int getBeforeMakingCount() {
		return beforeMakingCount;
	}

	public void setBeforeMakingCount(int beforeMakingCount) {
		this.beforeMakingCount = beforeMakingCount;
	}

	public int getMakingCount() {
		return makingCount;
	}

	public void setMakingCount(int makingCount) {
		this.makingCount = makingCount;
	}

	public int getDoneCount() {
		return doneCount;
	}

	public void setDoneCount(int doneCount) {
		this.doneCount = doneCount;
	}

	@Override
	public String toString() {
		return "DashBoardStatusDTO [paymentCount=" + paymentCount + ", beforeMakingCount=" + beforeMakingCount
				+ ", makingCount=" + makingCount + ", doneCount=" + doneCount + "]";
	}

	
}
