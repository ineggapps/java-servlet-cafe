package com.cafe.auth;

public class LoginException extends Exception{
	private static final long serialVersionUID = 5574894641057043832L;
	//로그인 실패 시
	
	public LoginException(String msg){
		super(msg);
	}
}
