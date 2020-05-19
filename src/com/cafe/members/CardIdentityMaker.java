package com.cafe.members;

public class CardIdentityMaker {
	
	public String getCardIdentity() {
		StringBuilder s = new StringBuilder();
		long millis = System.currentTimeMillis(); //13자리만 따오기
		s.append(millis + String.format("%03d",(int)(Math.random()*1000)));
		return s.toString();
	}
	
}
