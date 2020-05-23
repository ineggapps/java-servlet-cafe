package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe.admin.main.MainServlet;
import com.cafe.auth.SessionAuthInfo;

public class LoginFilter implements Filter {

	private FilterConfig filterConfig;
	//무조건 로그인시켜야 하는 페이지 필터링 INCLUDE_URI가 우선순위가 더 높다
	private static final String[] INCLUDE_URIS = {"/auth/mypage.do"};
	private static final String[] EXCLUDE_URIS = { "/main/**", "/resource/**", "/auth/**"
			, "/news/notice/list.do", "/news/notice/view.do"
			,"/news/event/list.do", "/news/event/view.do"
			,"/store/**", "/menu/**"
			, "/admin/**"//관리자 로그인페이지는 AdminAuthFilter에서 필터링
	};

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req;
		HttpServletResponse resp;
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			req = (HttpServletRequest) request;
			resp = (HttpServletResponse) response;
			
			//필수로 로그인이 필요한 페이지나 예외가 아닌 페이지는 기본적으로 로그인을 요구해야 한다.
			if (isIncludeUri(req) || isExcludeUri(req) == false) {
				// 로그인 확인
				SessionAuthInfo info = (SessionAuthInfo) req.getSession().getAttribute(MainServlet.SESSION_INFO);
//				System.out.println(info);
				if (info == null) {
					resp.sendRedirect(req.getContextPath() + "/auth/login.do");
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		filterConfig = null;
	}
	
	private boolean isIncludeUri(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		uri = uri.substring(cp.length());
		if(uri.substring(uri.length()-1).equals("/")) {
			uri = uri.substring(0,-1);
		}

		if (uri.length() <= 1 || uri.equals("/")) {
			return false;
		}

		for (String s : INCLUDE_URIS) {
			if (s.lastIndexOf("/**") != -1) {
				s = s.substring(0, s.lastIndexOf("/**"));
				if (uri.indexOf(s) == 0 || s.contains(uri)) {// ex: s=/auth/** , uri=/auth/abc.do 이면  /auth/가 겹치므로 0이 나온다. 
					return true;
				}
			} else if (uri.equals(s)) {
				return true;
			}
		}
		return false;
	}

	private boolean isExcludeUri(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		uri = uri.substring(cp.length());

		if (uri.length() <= 1 || uri.equals("/")) {
			return true;
		}

		for (String s : EXCLUDE_URIS) {
			if (s.lastIndexOf("/**") != -1) {
				s = s.substring(0, s.lastIndexOf("/**"));
				if (uri.indexOf(s) == 0 || s.contains(uri)) {
					return true;
				}
			} else if (uri.equals(s)) {
				return true;
			}
		}
		return false;
	}

}
