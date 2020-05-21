package com.cafe.members;

public class OrderException extends Exception{
	private static final long serialVersionUID = 1L;

	public OrderException(String msg) {
		super(msg);
	}
	
	public OrderException(Exception e) {
		super(e);
	}
}
