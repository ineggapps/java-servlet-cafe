package com.cafe.admin.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe.admin.main.MainServlet;
import com.cafe.auth.AuthDTO;
import com.cafe.auth.LoginException;
import com.cafe.auth.SessionAuthInfo;
import com.cafe.members.ErrorMessage;
import com.util.EspressoServlet;

@WebServlet("/admin/auth/*")
public class AuthServlet extends EspressoServlet {

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

	// PARAM
	private static final String PARAM_USER_ID = "userId";
	private static final String PARAM_USER_PW = "userPwd";
	
	//ATTRIBUTE
	private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		contextPath = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();

		if (uri.indexOf(API_LOGIN) != -1) {
			loginForm(req, resp);
		} else if (uri.indexOf(API_LOGIN_OK) != -1) {
			loginSubmit(req, resp);
		}
	}

	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_LOGIN;
		try {
			SessionAuthInfo info = getSessionAuthInfo(req);
			if(info!=null && info.isAdmin()) {
				resp.sendRedirect(contextPath + "/" + ADMIN + "/main");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, path);
	}

	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AdminAuthDAO dao = new AdminAuthDAO();
		try {
			String userId = req.getParameter(PARAM_USER_ID);
			String userPwd = req.getParameter(PARAM_USER_PW);
			AuthDTO dto = dao.readAdmin(userId);
			System.out.println(userId + ", " + userPwd);
			if(dto!=null && dto.getUserPwd().equals(userPwd)) {
				HttpSession session = req.getSession();
				session.setAttribute(MainServlet.SESSION_INFO, 
						new SessionAuthInfo(dto.getUserNum(), dto.getUserId(), 
								dto.getUserName(), dto.getNickname(), dto.isAdmin()));
				resp.sendRedirect(contextPath + "/admin/main/index.do" );
				return;
			}else {
				throw new LoginException("관리자가 아니거나 정보가 없습니다.");
			}
		}catch(LoginException e) { 
			e.printStackTrace();
			req.setAttribute(ATTRIBUTE_ERROR_MESSAGE, new ErrorMessage("", "관리자 인증에 실패했습니다."));
			forward(req, resp, VIEWS +  JSP_LOGIN);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LOGIN);
		}
	}

}
