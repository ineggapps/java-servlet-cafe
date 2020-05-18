package com.cafe.auth;

public class AuthDTO {
	private int userNum;
	private String email;
	private String userId;
	private String userPwd;
	private String userName;
	private String nickname;
	private String createdDate;
	private String updatedDate;
	private String phone;
	private int enabled;
	private boolean isAdmin = false;

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "AuthDTO [userNum=" + userNum + ", email=" + email + ", userId=" + userId + ", userName=" + userName
				+ ", nickname=" + nickname + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", phone=" + phone + ", enabled=" + enabled + "]";
	}

	public AuthDTO() {
	}

	public AuthDTO(String email, String userId, String userPwd, String userName, String nickname, String phone) {
		this.email = email;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.nickname = nickname;
		this.phone = phone;
	}

	public AuthDTO(int userNum, String email, String userId, String userPwd, String userName, String nickname,
			String createdDate, String updatedDate, String phone, int enabled, boolean isAdmin) {
		this.userNum = userNum;
		this.email = email;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.nickname = nickname;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.phone = phone;
		this.enabled = enabled;
		this.isAdmin = isAdmin;
	}
	
}
