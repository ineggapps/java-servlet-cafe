package com.cafe.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.EspressoServlet;

@WebServlet("/auth/*")
public class AuthServlet extends EspressoServlet {

	// PATH
	private static final String API_NAME = "/auth";
	private static final String CAFE = "cafe";
	private static final String VIEW = "/WEB-INF/views";
	private static final String VIEWS = VIEW + "/" + CAFE;
	private static final String SESSION_INFO = "member";
	// PATH(dynamic)
	private static String contextPath;
	private static String apiPath;

	// API
	private static final String API_LOGIN = "/login.do";
	private static final String API_LOGIN_OK = "/login_ok.do";
	private static final String API_JOIN = "/join.do";
	private static final String API_JOIN_OK = "/join_ok.do";

	private static final String API_FIND_EMAIL = "/find_email.do";
	private static final String API_FIND_PASSWORD = "/find_password.do";

	// JSP
	private static final String JSP_LOGIN = "/auth_login.jsp";
	private static final String JSP_JOIN = "/auth_join.jsp";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		contextPath = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();

		if (uri.indexOf(API_LOGIN) != -1) {
			loginForm(req, resp);
		} else if (uri.indexOf(API_LOGIN_OK) != -1) {
			loginSubmit(req, resp);
		} else if (uri.indexOf(API_JOIN) != -1) {
			joinForm(req, resp);
		} else if (uri.indexOf(API_JOIN_OK) != -1) {
			joinSubmit(req, resp);
		}

	}

	// 회원 로그인
	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_LOGIN;
		forward(req, resp, path);
	}

	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	// 회원 가입
	protected void joinForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_JOIN;
		forward(req, resp, path);
	}

	protected void joinSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
