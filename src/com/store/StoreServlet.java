package com.store;

import java.io.IOException;

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

	// JSP
	private static final String JSP_LIST = "/store_list.jsp";
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		contextPath  = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();
		if(uri.indexOf(API_INDEX) !=-1 || uri.indexOf(API_STORES) != -1) {
			list(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS  + JSP_LIST;
		forward(req, resp, path);
	}	
	
	
}
