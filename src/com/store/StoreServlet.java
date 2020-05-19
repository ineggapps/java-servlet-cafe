package com.store;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.EspressoServlet;

@WebServlet("/store/*")
public class StoreServlet extends EspressoServlet {

	private static final long serialVersionUID = 1L;
	// PATH
	private static final String API_NAME = "/store";
	private static final String CAFE = "cafe";
	private static final String VIEW = "/WEB-INF/views";
	private static final String VIEWS = VIEW + "/" + CAFE;
	private static final String SESSION_INFO = "member";

	// PATH(dynamic)
	private static String contextPath;
	private static String apiPath;

	// API
	private static final String API_INDEX = "/index.do";
	private static final String API_STORES = "/stores.do";

	// PARAM

	// ATTRIBUTe
	private static final String ATTRIBUTE_LIST = "list";
	private static final String ATTRIBUTE_API = "api";

	// JSP
	private static final String JSP_LIST = "/store_list.jsp";

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		contextPath = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(ATTRIBUTE_API, uri.substring(uri.lastIndexOf("/")));
		if (uri.indexOf(API_INDEX) != -1 || uri.indexOf(API_STORES) != -1) {
			list(req, resp, attributes);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes) throws ServletException, IOException {
		String path = VIEWS + JSP_LIST;
		StoreDAO dao = new StoreDAO();
		List<StoreDTO> list = dao.listStore();
		attributes.put(ATTRIBUTE_LIST, list);
		forward(req, resp, path,attributes);
	}

	//////////////////////
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path,
			Map<String, Object> attributes) throws ServletException, IOException {
		setAttributes(req, attributes);
		super.forward(req, resp, path);
	}

	private void setAttributes(HttpServletRequest req, Map<String, Object> attributes) {
		if (req == null || attributes == null) {
			return;
		}
		for (String key : attributes.keySet()) {
			Object value = attributes.getOrDefault(key, "");
			//특정 항목 인코딩
			//if(value != null && req.getMethod().equalsIgnoreCase("GET") && value instanceof String) {
			//if(key.equals("특정항목")){
			//	try {
			//		value = (Object)URLDecoder.decode(((String)value),"utf-8");
			//	} catch (Exception e) {
			//		e.printStackTrace();
			//	}
			//}
			//}
			req.setAttribute(key, value);
		}
	}

	private void goToLogin(HttpServletResponse resp) {
		try {
			resp.sendRedirect(contextPath + "/auth/login.do");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
