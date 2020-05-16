package com.cafe.menu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.EspressoServlet;

@WebServlet("/menu/*")
public class MenuServlet extends EspressoServlet {

	// PATH
	private static final String API_NAME = "/menu";
	private static final String CAFE = "cafe";
	private static final String VIEW = "/WEB-INF/views";
	private static final String VIEWS = VIEW + "/" + CAFE;
	private static final String SESSION_INFO = "member";
	// PATH(dynamic)
	private static String contextPath;
	private static String apiPath;

	// API
	private static final String API_INDEX = "/index.do";
	private static final String API_COFFEE = "/coffee.do";
	private static final String API_ADE = "/ade.do";
	private static final String API_BAKERY = "/bakery.do";

	// PARAM
	private static final String PARAM_MENU = "menu";
	private static final String PARAM_COFFEE = "coffee";
	private static final String PARAM_ADE = "ade";
	private static final String PARAM_BAKERY = "bakery";

	// JSP
	private static final String JSP_MENU = "/menu.jsp";

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		contextPath = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();

		if (uri.indexOf(API_COFFEE) != -1 || uri.indexOf(API_INDEX) != -1) {
			beverage(req, resp);
		} else if (uri.indexOf(API_ADE) != -1) {
			ade(req, resp);
		} else if (uri.indexOf(API_BAKERY) != -1) {
			bakery(req, resp);
		}

	}

	protected void beverage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_MENU;
		// 포워딩
		req.setAttribute(PARAM_MENU, PARAM_COFFEE);
		forward(req, resp, path);
	}

	protected void ade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_MENU;
		// 포워딩
		req.setAttribute(PARAM_MENU, PARAM_ADE);
		forward(req, resp, path);
	}

	protected void bakery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_MENU;
		// 포워딩
		req.setAttribute(PARAM_MENU, PARAM_BAKERY);
		forward(req, resp, path);
	}

}
