package com.cafe.members;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe.auth.SessionAuthInfo;
import com.util.EspressoServlet;

@WebServlet("/members/*")
public class MembersServlet extends EspressoServlet {

	// PATH
	private static final String API_NAME = "/members";
	private static final String CAFE = "cafe";
	private static final String VIEW = "/WEB-INF/views";
	private static final String VIEWS = VIEW + "/" + CAFE;
	private static final String SESSION_INFO = "member";

	// PATH(dynamic)
	private static String contextPath;
	private static String apiPath;

	// API
	private static final String API_INDEX = "/index.do";
	private static final String API_LIST = "/list.do";
	private static final String API_DETAIL = "/detail.do";
	private static final String API_REGISTER = "/register.do";
	private static final String API_CHARGE = "/charge.do";
	private static final String API_CHARGE_OK = "/charge_ok.do";
	private static final String API_ORDER = "/order.do";

	// JSP
	private static final String JSP_LIST = "/members_list.jsp";
	private static final String JSP_DETAIL = "/members_detail.jsp";
	private static final String JSP_REGISTER_STEP1 = "/members_register_step1.jsp";
	private static final String JSP_CHARGE = "/members_charge.jsp";
	private static final String JSP_ORDER = "/members_order.jsp";

	// PARAM
	private static final String PARAM_MODE = "mode";
	private static final String PARAM_MODEL_NUM = "modelNum";
	private static final String PARAM_MODE_REGISTER = "register";
	private static final String PARAM_MODE_CHARGE = "charge";
	private static final String PARAM_REGISTER_STEP = "register_step";
	private static final String PARAM_CARD_NAME = "cardName";
	private static final String PARAM_PRICE = "price";
	private static final int PARAM_REGISTER_STEP_1 = 1;
	private static final int PARAM_REGISTER_STEP_2 = 2;
	private static final int PARAM_REGISTER_STEP_3 = 3;

	// ATTRIBUTE
	private static final String ATTRIBUTE_LIST = "list";
	private static final String ATTRIBUTE_CARD_DTO = "cardDTO";
	private static final String ATTRIBUTE_CARD_MODEL_DTO = "modelDTO";

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		contextPath = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();
		Map<String, Object> attributes = new HashMap<>();

		if (uri.indexOf(API_INDEX) != -1 || uri.indexOf(API_LIST) != -1) {
			list(req, resp, attributes);
		} else if (uri.indexOf(API_DETAIL) != -1) {
			detail(req, resp, attributes);
		} else if (uri.indexOf(API_REGISTER) != -1) {
			register(req, resp, attributes);
		} else if (uri.indexOf(API_CHARGE) != -1) {
			chargeForm(req, resp, attributes);
		} else if (uri.indexOf(API_CHARGE_OK) != -1) {
			chargeSubmit(req, resp, attributes);
		} else if (uri.indexOf(API_ORDER) != -1) {
			order(req, resp, attributes);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_LIST;
		CardDAO dao = new CardDAO();
		SessionAuthInfo info = getSessionAuthInfo(req);
		if(info==null) {
			goToLogin(resp);
			return;
		}
		List<CardDTO> list = dao.listCard(info.getUserNum());
		attributes.put(ATTRIBUTE_LIST, list);
		forward(req, resp, path, attributes);
	}

	protected void detail(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_DETAIL;
		forward(req, resp, path, attributes);
	}

	protected void register(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String paramStep = req.getParameter(PARAM_REGISTER_STEP);
		try {
			int step = 1;
			//무조건 로그인 인증하기
			SessionAuthInfo info = getSessionAuthInfo(req);
			if (info == null) {
				goToLogin(resp);
				return;
			}
			if (paramStep != null) {
				step = Integer.parseInt(paramStep);
			}
			switch (step) {
			default:
			case PARAM_REGISTER_STEP_1:
				registerStep1(req, resp, attributes);
				break;
			case PARAM_REGISTER_STEP_2:
				registerStep2(req, resp, attributes);
				break;
			case PARAM_REGISTER_STEP_3:
				registerSubmit(req, resp, attributes);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			registerStep1(req, resp, attributes);
			return;
		}
	}

	protected void registerStep1(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		System.out.println("step1");
		String path = VIEWS + JSP_REGISTER_STEP1;
		// 카드모델 고르기 페이지
		CardModelDAO dao = new CardModelDAO();
		List<CardModelDTO> list = dao.listCardModel();
		attributes.put(PARAM_MODE, PARAM_MODE_REGISTER);
		attributes.put(ATTRIBUTE_LIST, list);
		forward(req, resp, path, attributes);
	}

	protected void registerStep2(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_CHARGE;
		try {
			int modelNum = Integer.parseInt(req.getParameter(PARAM_MODEL_NUM));
			CardModelDAO modelDAO = new CardModelDAO();
			CardDTO cardDTO = new CardDTO();
			cardDTO.setModelNum(modelNum);
			cardDTO.setCardName("");
			CardModelDTO modelDTO = modelDAO.readCardModel(modelNum);
			attributes.put(PARAM_MODE, PARAM_MODE_REGISTER);
			attributes.put(ATTRIBUTE_CARD_DTO, cardDTO);
			attributes.put(ATTRIBUTE_CARD_MODEL_DTO, modelDTO);
			forward(req, resp, path, attributes);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_REGISTER);
			return;
		}
	}

	protected void registerSubmit(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		// 카드 등록하기
		try {
			SessionAuthInfo info = getSessionAuthInfo(req);
			CardDAO dao = new CardDAO();
			String cardName = req.getParameter(PARAM_CARD_NAME);
			int price = Integer.parseInt(req.getParameter(PARAM_PRICE));
			int modelNum = Integer.parseInt(req.getParameter(PARAM_MODEL_NUM));
			CardDTO dto = new CardDTO(cardName, info.getUserNum(), modelNum, price);
			dao.insertCard(dto);
			resp.sendRedirect(apiPath + API_LIST);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_REGISTER);
		}
	}

	protected void chargeForm(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_CHARGE;
		attributes.put(PARAM_MODE, PARAM_MODE_CHARGE);
		forward(req, resp, path, attributes);
	}

	protected void chargeSubmit(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes) {
		// 충전 절차 진행
		try {
			resp.sendRedirect(apiPath + API_LIST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void order(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_ORDER;
		forward(req, resp, path, attributes);
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

	private void goToLogin(HttpServletResponse resp) {
		try {
			resp.sendRedirect(contextPath + "/auth/login.do");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
