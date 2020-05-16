package com.cafe.admin.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.EspressoServlet;

@WebServlet("/admin/auth/*")
public class AuthServlet extends EspressoServlet{
	
	// PATH
	private static final String API_NAME = "/admin/auth";
	private static final String ADMIN = "admin";
	private static final String VIEW = "/WEB-INF/views";
	private static final String VIEWS = VIEW + "/" + ADMIN;
	private static final String SESSION_INFO = "member";

	// PATH(dynamic)
	private static String contextPath;
	private static String apiPath;

	// API
	private static final String API_LOGIN = "/login.do";
	private static final String API_LOGIN_OK = "/login_ok.do";

	// JSP
	private static final String JSP_LOGIN = "/admin_login.jsp";

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		contextPath = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();

		if(uri.indexOf(API_LOGIN)!=-1) {
			loginForm(req, resp);
		}else if(uri.indexOf(API_LOGIN_OK)!=-1) {
			loginSubmit(req, resp);
		}
	}

	
	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_LOGIN;
		forward(req, resp, path);
	}	
	
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(contextPath + "/admin/main/index.do" );
	}	
	
}
