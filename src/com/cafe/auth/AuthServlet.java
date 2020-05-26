package com.cafe.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.EspressoServlet;

@WebServlet("/auth/*") 
public class AuthServlet extends EspressoServlet {
	private static final long serialVersionUID = 1L;
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
	private static final String API_LOGOUT = "/logout.do";
	private static final String API_JOIN = "/join.do";
	private static final String API_JOIN_OK = "/join_ok.do";
	private static final String API_MY_PAGE = "/mypage.do";
	
	// JSP
	private static final String JSP_LOGIN = "/auth_login.jsp";
	private static final String JSP_JOIN = "/auth_join.jsp";

	// COLUMN
	private static final String PARAM_USER_NUM = "userNum";
	private static final String PARAM_EMAIL1 = "email1";
	private static final String PARAM_EMAIL2 = "email2";
	private static final String PARAM_USER_ID = "userId";
	private static final String PARAM_USER_PWD = "userPwd";
	private static final String PARAM_USER_NAME = "userName";
	private static final String PARAM_NICKNAME = "nickname";
	private static final String PARAM_PHONE = "phone";

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
		String uri = req.getRequestURI(); // 접속주소 모든 
//		System.out.println(uri);
		// System.out.println(uri.indexOf("find_id.do"));
			
		if (uri.indexOf(API_LOGIN) != -1) {
			loginForm(req, resp); // 회원로그인
		} else if (uri.indexOf(API_LOGIN_OK) != -1) {
			loginSubmit(req, resp); // 로그인전송
		} else if (uri.indexOf(API_JOIN) != -1) {
			joinForm(req, resp); // 회원가입
		} else if (uri.indexOf(API_JOIN_OK) != -1) {
			joinSubmit(req, resp);
		} else if (uri.indexOf(API_LOGOUT) != -1) {
			logout(req, resp);
		} else if (uri.indexOf("/mypage.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("/mypage_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("/find_id.do")!=-1) {
			findIdForm(req, resp);
		} else if (uri.indexOf("/find_id_ok.do")!=-1) {
			findIdSubmit(req, resp);
		} else if (uri.indexOf("/find_pwd.do")!=-1) {
			findPwdForm(req, resp);
		} else if (uri.indexOf("/find_pwd_ok.do")!=-1) {
			findPwdSubmit(req,resp);
		} else if (uri.indexOf("/find_pwd_ok2.do")!=-1) {
			findPwdForm2(req, resp);
		} 
	}

	// 로그인폼
	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_LOGIN;
		int isWelcome = req.getParameter("isWelcome")!=null?1:0;
		req.setAttribute("isWelcome", isWelcome);
		forward(req, resp, path);
	}
	// 로그인 처리
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuthDAO dao = new AuthDAO();
		try {
			String userId = req.getParameter(PARAM_USER_ID);
			String userPwd = req.getParameter(PARAM_USER_PWD);
			SessionAuthInfo info = null;
			AuthDTO dto = dao.readMember(userId);
//			System.out.println(userId + ", " + userPwd);
//			System.out.println(dto);
			if (dto == null) {
				// 로그인 정보가 존재하지 않아서 로그인 실패
				throw new LoginException("아이디 또는 암호가 맞지 않습니다.");
			}
			if (!dto.getUserId().equals(userId) || !dto.getUserPwd().equals(userPwd)) {
				// 로그인 정보가 하나라도 일치하지 않으면
				throw new LoginException("아이디 또는 암호가 맞지 않습니다.");
			}
			info = new SessionAuthInfo(dto.getUserNum(), dto.getUserId(), dto.getUserName(), dto.getNickname(),
					dto.isAdmin());
			HttpSession session = req.getSession();
			// 세션 유지시간 20분으로 변경
			session.setMaxInactiveInterval(60 * 20);// 단위(초) 60초*20번 => 20분
			session.setAttribute(SESSION_INFO, info);
			resp.sendRedirect(contextPath + MAIN);
		} catch (LoginException e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LOGIN);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LOGIN);
			return;
		}
	}

	// 로그아웃
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionAuthInfo info = getSessionAuthInfo(req);
		HttpSession session = req.getSession();
		if (session != null && info != null) {
			session.invalidate();
			info = null;
		}
		resp.sendRedirect(contextPath + MAIN);
	}

	// 회원가입 폼
	protected void joinForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_JOIN;
		req.setAttribute("mode", "join");
		forward(req, resp, path);
	}
	// 회원가입 처리 
	protected void joinSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuthDAO dao = new AuthDAO();
		try {
			String email1 = req.getParameter(PARAM_EMAIL1);
			String email2 = req.getParameter(PARAM_EMAIL2);
			String userId = req.getParameter(PARAM_USER_ID);
			String userPwd = req.getParameter(PARAM_USER_PWD);
			String userName = req.getParameter(PARAM_USER_NAME);
			String nickname = req.getParameter(PARAM_NICKNAME);
			String phone = req.getParameter(PARAM_PHONE);
			
			AuthDTO dto = new AuthDTO(email1 + "@" + email2, userId, userPwd, userName, nickname, phone);
			/*
			if(dao.readMember(userId) != null) {
				resp.sendRedirect(apiPath+API_JOIN);
				return;
			}
			*/
			int result=dao.insertMember(dto);
			if(result==0) {
				String message = "회원가입에 실패했습니다.";
				
				req.setAttribute("title", "회원가입");
				req.setAttribute("mode", "join");
				req.setAttribute("message", message);
				forward(req, resp,  "/WEB-INF/views/cafe/auth_join.jsp");
				return;
			}
			
//			System.out.println(dto);
		} catch (Exception e) {
			e.printStackTrace();
			
			
		}
		resp.sendRedirect(apiPath + API_LOGIN + "?isWelcome=1");
	}

	// 회원정보수정 폼
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/auth_mypage.jsp";
		req.setAttribute("mode", "update");
		//1. 회원정보 불러오기
		SessionAuthInfo info = getSessionAuthInfo(req);
		AuthDAO dao = new AuthDAO();
		AuthDTO dto = dao.readMember(info.getUserId());
		req.setAttribute("authDTO", dto);
		// req.setAttribute("mode", "update");
		forward(req, resp, path);
	}

	// 회원정보 수정 완료
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionAuthInfo info = getSessionAuthInfo(req);

		try {
			AuthDAO dao = new AuthDAO();
			String email1 = req.getParameter(PARAM_EMAIL1);
			String email2 = req.getParameter(PARAM_EMAIL2);
			String userPwd = req.getParameter(PARAM_USER_PWD);
			String nickname = req.getParameter(PARAM_NICKNAME);
			String phone = req.getParameter(PARAM_PHONE);
			int userNum = Integer.parseInt(req.getParameter(PARAM_USER_NUM));
			String mode=req.getParameter("mode");

			if(mode.equals("update")) {
				AuthDTO dto = new AuthDTO();
				dto.setEmail(email1 + "@" + email2);
				dto.setUserPwd(userPwd);
				dto.setNickname(nickname);
				dto.setPhone(phone);
				dto.setUserNum(userNum);
	
				dao.updateMember(dto);
			} else if(mode.equals("delete")){
				int result=dao.deleteMember(userNum, userPwd);
				if(result==0) {
					String path = "/WEB-INF/views/cafe/auth_mypage.jsp";
					req.setAttribute("mode", "main");
					AuthDTO dto = dao.readMember(info.getUserId());
					req.setAttribute("authDTO", dto);
					forward(req, resp, path);
					return;
				}
				
				HttpSession session = req.getSession();
				session.invalidate();
			}
			
			resp.sendRedirect(contextPath + MAIN);


		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + "/update.do");
			return;
		}

	}

	// 아이디 찾기 폼
	protected void findIdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/auth_find_id.jsp";
		req.setAttribute("mode", "join");
		forward(req, resp, path);
	}

	// 아이디 전송 (이름, 핸드폰번호)
	protected void findIdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String path = "/WEB-INF/views/cafe/auth_find_id2.jsp";
	      try {
	         AuthDAO dao = new AuthDAO();
	         String userName = req.getParameter("userName");
	         String phone = req.getParameter("phone");
	         String userId=dao.findId(userName, phone);
	         System.out.println(userId + "\n" + userName + ".." + phone);
	         
	         if(userId==null) {
	            String s = "해당되는 ID가 없습니다.";
	            req.setAttribute("message", s);
	            forward(req, resp, "/WEB-INF/views/auth/find_id2.jsp");
	            return;
	         }
	         
	         req.setAttribute("userId", userId);
	         forward(req, resp, path);
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	// 비밀번호 찾기 폼
	protected void findPwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/auth_find_pwd.jsp";
		req.setAttribute("mode", "join");
		forward(req, resp, path); 
	}
	// 비밀번호 수정값 전송 (아이디, 핸드폰번호)
	protected void findPwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/auth_find_pwd2.jsp";
		
		try {
			String userId = req.getParameter("userId");
			String phone = req.getParameter("phone");

			req.setAttribute("userId", userId);
			req.setAttribute("phone", phone);

			forward(req, resp, path);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	// 비밀번호 수정완료 폼
	protected void findPwdForm2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/auth_find_pwd3.jsp";
		
		AuthDAO dao = new AuthDAO();
		String userPwd = req.getParameter("userPwd");
		String userId = req.getParameter("userId");
		String phone = req.getParameter("phone");
		
		
//		System.out.println(userPwd +"." + userId + "." + phone);
		dao.findPwd(userPwd, userId, phone);
		
		forward(req, resp, path); 
	}
	// 아이디 중복 검사
	protected int memberIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int result=0;
		return result;
	}
}
