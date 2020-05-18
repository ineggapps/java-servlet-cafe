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
	private static final String MAIN = "/main/index.do";
	
	
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

	// COLUMN
	private static final String PARAM_USER_NUM = "userNum";
	private static final String PARAM_EMAIL1 = "email1";
	private static final String PARAM_EMAIL2 = "email2";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_USER_ID = "userId";
	private static final String PARAM_USER_PWD = "userPwd";
	private static final String PARAM_USER_NAME = "userName";
	private static final String PARAM_NICKNAME = "nickname";
	private static final String PARAM_CREATED_DATE = "created_date";
	private static final String PARAM_UPDATED_DATE = "updated_date";
	private static final String PARAM_PHONE = "phone";
	private static final String PARAM_ENABLED = "enabled";

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
		AuthDAO dao = new AuthDAO();
		try {
			String userId = req.getParameter(PARAM_USER_ID);
			String userPwd = req.getParameter(PARAM_USER_PWD);
			SessionAuthInfo info = null;
			AuthDTO dto = dao.readMember(userId);
			System.out.println(userId + ", " + userPwd);
			System.out.println(dto);
			if(dto==null) {
				//로그인 정보가 존재하지 않아서 로그인 실패
				throw new LoginException("아이디 또는 암호가 맞지 않습니다."); 
			}
			if(!dto.getUserId().equals(userId) || !dto.getUserPwd().equals(userPwd)) {
				//로그인 정보가 하나라도 일치하지 않으면
				throw new LoginException("아이디 또는 암호가 맞지 않습니다.");
			}
			info = new SessionAuthInfo(dto.getUserNum(), dto.getUserId(), dto.getUserName(), dto.getNickname());
			req.getSession().setAttribute(SESSION_INFO, info);
			resp.sendRedirect(contextPath + MAIN);
		}catch(LoginException e) { 
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LOGIN);
		}
		catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LOGIN);
			return;
		}
	}

	// 회원 가입
	protected void joinForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_JOIN;
		forward(req, resp, path);
	}

	protected void joinSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			AuthDAO dao = new AuthDAO();
			String email1 = req.getParameter(PARAM_EMAIL1);
			String email2 = req.getParameter(PARAM_EMAIL2);
			String userId = req.getParameter(PARAM_USER_ID);
			String userPwd = req.getParameter(PARAM_USER_PWD);
			String userName = req.getParameter(PARAM_USER_NAME);
			String nickname = req.getParameter(PARAM_NICKNAME);
			String phone = req.getParameter(PARAM_PHONE);
			AuthDTO dto = new AuthDTO(email1 + "@" + email2, userId, userPwd, userName, nickname, phone);
			dao.insertMember(dto);
			System.out.println(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(apiPath + API_LOGIN);
	}

}
