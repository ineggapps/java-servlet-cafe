package com.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe.auth.SessionAuthInfo;

public abstract class EspressoServlet extends HttpServlet{
	public static final String SESSION_INFO = "member";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher(path);
		dispatcher.forward(req, resp);
	}
	
	protected SessionAuthInfo getSessionAuthInfo(HttpServletRequest req) {
		if(req==null) {
			return null;
		}
		HttpSession session = req.getSession();
		SessionAuthInfo info = (SessionAuthInfo) session.getAttribute(SESSION_INFO);
		return info;
	}
	
	protected abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
