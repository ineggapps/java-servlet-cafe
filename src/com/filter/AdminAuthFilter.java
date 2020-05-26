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

public class AdminAuthFilter implements Filter {

	private FilterConfig filterConfig;
	private static final String[] INCLUDE_URIS = { 
			"/admin/main/**", 
			"/menu/createdMenu.do", "/menu/createdMenu_ok.do",
			"/menu/update.do", "/menu/createdMenu_ok.do",
			"/menu/delete.do",			
			"/news/notice/update.do", "/news/notice/update_ok.do",
			"/news/notice/delete.do",
			"/news/event/write.do", "/news/event/write_ok.do",
			"/news/event/update.do", "/news/event/update_ok.do",
			"/news/event/delete.do"
//			"/news/notice/write.do", "/news/notice/write_ok.do", "/news/notice/update.do", "/news/notice/update_ok.do", "/news/notice/delete.do" 
//			,"/news/event/write.do", "/news/event/write_ok.do", "/news/event/update.do", "/news/event/update_ok.do", "/news/event/delete.do" 
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
			if (isIncludeUri(req) == true) {
				// 로그인 확인
				SessionAuthInfo info = (SessionAuthInfo) req.getSession().getAttribute(MainServlet.SESSION_INFO);
				if (info == null || info.isAdmin() == false) {
					resp.sendRedirect(req.getContextPath() + "/admin/auth/login.do");
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

		if (uri.length() <= 1 || uri.equals("/")) {
			return false;
		}

		for (String s : INCLUDE_URIS) {
			if (s.lastIndexOf("/**") != -1) {
				s = s.substring(0, s.lastIndexOf("**"));
				if (uri.indexOf(s) == 0) {
					return true;
				}
			} else if (uri.equals(s)) {
				return true;
			}
		}
		return false;
	}

}
