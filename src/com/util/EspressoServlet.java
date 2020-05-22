package com.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe.auth.SessionAuthInfo;

public abstract class EspressoServlet extends HttpServlet {
	public static final String SESSION_INFO = "member";

	// ∆‰¿Ã¬°
	protected static final String PARAM_PAGE = "page";
	protected static final String ATTRIBUTE_DATA_COUNT = "dataCount";
	protected static final String ATTRIBUTE_TOTAL_PAGE = "totalPage";
	protected static final String ATTRIBUTE_CURRENT_PAGE = "currentPage";
	protected static final String ATTRIBUTE_PAGES = "pages";
	protected static final String ATTRIBUTE_URI = "uri";
	protected static final String ATTRIBUTE_QUERY = "query";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher(path);
		dispatcher.forward(req, resp);
	}

	protected SessionAuthInfo getSessionAuthInfo(HttpServletRequest req) {
		if (req == null) {
			return null;
		}
		HttpSession session = req.getSession();
		SessionAuthInfo info = (SessionAuthInfo) session.getAttribute(SESSION_INFO);
		return info;
	}

	protected void setPagerAttributes(int dataCount, int currentPage, int totalPage, int[] pages, String uri, String query,
			Map<String, Object> attributes) throws Exception {
		try {
			attributes.put(ATTRIBUTE_DATA_COUNT, dataCount);
			attributes.put(ATTRIBUTE_TOTAL_PAGE, totalPage);
			attributes.put(ATTRIBUTE_CURRENT_PAGE, currentPage);
			attributes.put(ATTRIBUTE_PAGES, pages);
			attributes.put(ATTRIBUTE_URI, uri);
			if(query != null && query.length()>0 ) {
				query += "&";
			}
			attributes.put(ATTRIBUTE_QUERY, query);
		} catch (Exception e) {
			throw new Exception(e);
		}
		attributes.put(ATTRIBUTE_QUERY, query);
	}

	protected abstract void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;
}
