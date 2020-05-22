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
	private static final String[] EXCLUDE_URIS = { "/auth/**", "/main/**", "/resource/**"
			, "/news/notice/list.do", "/news/notice/view.do"
			,"/news/event/list.do", "/news/event/view.do"
			,"/store/**"
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
			if (isExcludeUri(req) == false) {
				// 로그인 확인
				SessionAuthInfo info = (SessionAuthInfo) req.getSession().getAttribute(MainServlet.SESSION_INFO);
				System.out.println(info);
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

	private boolean isExcludeUri(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		uri = uri.substring(cp.length());
		System.out.println(uri + "는?");

		if (uri.length() <= 1 || uri.equals("/")) {
			return true;
		}

		for (String s : EXCLUDE_URIS) {
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
