package com.cafe.admin.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe.members.OrderHistoryDTO;
import com.util.EspressoServlet;

@WebServlet("/admin/main/*")
public class MainServlet extends EspressoServlet {

	// PATH
	private static final String API_NAME = "/admin/main";
	private static final String ADMIN = "admin";
	private static final String VIEW = "/WEB-INF/views";
	private static final String VIEWS = VIEW + "/" + ADMIN;
	private static final String SESSION_INFO = "member";

	// PATH(dynamic)
	private static String contextPath;
	private static String apiPath;

	// API
	private static final String API_INDEX = "/index.do";
	private static final String API_ORDER_PAYMENT = "/orderPayment.do";
	private static final String API_ORDER_BEFORE_MAKING = "/orderBeforeMaking.do";
	private static final String API_ORDER_MAKING = "/orderMaking.do";
	private static final String API_ORDER_DONE = "/orderDone.do";
	private static final String API_SALES_BY_MENU = "/salesByMenu.do";
	private static final String API_SALES_BY_DATE = "/salesByDate.do";

	// JSP
	private static final String JSP_MAIN = "/admin_main.jsp";
	private static final String JSP_ORDER = "/admin_order.jsp";
	private static final String JSP_SALES = "/admin_sales.jsp";

	// ATTRIBUTE
	private static final String ATTRIBUTE_API = "api";
	private static final String ATTRIBUTE_ORDER_HISTORY = "orderHistory";
	private static final String ATTRIBUTE_DASHBOARD_STATUS_DTO = "dashBoardStatusDTO";
	private static final String ATTRIBUTE_TODAY_STATUS = "todayStatus";

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		contextPath = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(ATTRIBUTE_API, uri.substring(uri.lastIndexOf("/")));

		if (uri.indexOf(API_INDEX) != -1) {
			main(req, resp, attributes);
		} else if (uri.indexOf(API_ORDER_PAYMENT) != -1) {
			orderPayment(req, resp, attributes);
		} else if (uri.indexOf(API_ORDER_BEFORE_MAKING) != -1) {
			orderBeforeMaking(req, resp, attributes);
		} else if (uri.indexOf(API_ORDER_MAKING) != -1) {
			orderMaking(req, resp, attributes);
		} else if (uri.indexOf(API_ORDER_DONE) != -1) {
			orderDone(req, resp, attributes);
		} else if (uri.indexOf(API_SALES_BY_MENU) != -1) {
			salesByMenu(req, resp, attributes);
		} else if (uri.indexOf(API_SALES_BY_DATE) != -1) {
			salesByDate(req, resp, attributes);
		}
	}

	protected void main(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_MAIN;
		try {
			AdminOrderDAO dao = new AdminOrderDAO();
			DashBoardStatusDTO dashboardDTO = dao.getTodayDashBoardStatus();
			TodayStatusDTO todayStatus = dao.getTodayStatus();
			attributes.put(ATTRIBUTE_DASHBOARD_STATUS_DTO, dashboardDTO);
			attributes.put(ATTRIBUTE_TODAY_STATUS, todayStatus);
			forward(req, resp, path, attributes);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_INDEX);
			return;
		}
	}

	// 주문 관련
	protected void orderPayment(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_ORDER;
		AdminOrderDAO dao = new AdminOrderDAO();
		System.out.println("주문 관련");
		List<OrderHistoryDTO> list = dao.listOrderHistoryByUserNum(AdminOrderDAO.STATUS_PAYMENT);
		System.out.println(list.size());
		for(OrderHistoryDTO dto : list) {
			System.out.println(dto);
		}
		attributes.put(ATTRIBUTE_ORDER_HISTORY, list);
		
		forward(req, resp, path, attributes);
	}

	protected void orderBeforeMaking(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_ORDER;
		forward(req, resp, path, attributes);
	}

	protected void orderMaking(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_ORDER;
		forward(req, resp, path, attributes);
	}

	protected void orderDone(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_ORDER;
		forward(req, resp, path, attributes);
	}

	// 판매 관련
	protected void salesByMenu(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_SALES;
		forward(req, resp, path, attributes);
	}

	protected void salesByDate(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_SALES;
		forward(req, resp, path, attributes);
	}

	///////////////////////////////////////////////
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
//			특정 항목 인코딩
//			if(value != null && req.getMethod().equalsIgnoreCase("GET") && value instanceof String) {
//				if(key.equals("특정항목")){
//					try {
//						value = (Object)URLDecoder.decode(((String)value),"utf-8");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
			req.setAttribute(key, value);
		}
	}

}
