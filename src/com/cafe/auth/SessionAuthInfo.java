package com.cafe.auth;

public class SessionAuthInfo {
	private int userNum;
	private String userId;
	private String userName;
	private String nickname;

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
	public SessionAuthInfo() {
	}

	public SessionAuthInfo(int userNum, String userId, String userName, String nickname) {
		this.userNum = userNum;
		this.userId = userId;
		this.userName = userName;
		this.nickname = nickname;
	}

	
}
