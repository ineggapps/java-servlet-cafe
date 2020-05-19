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
	private static final String API_CLOSE_CARD = "/close.do";
	private static final String API_CLOSE_CARD_OK = "/close_ok.do";

	// JSP
	private static final String JSP_LIST = "/members_list.jsp";
	private static final String JSP_DETAIL = "/members_detail.jsp";
	private static final String JSP_REGISTER_STEP1 = "/members_register_step1.jsp";
	private static final String JSP_CHARGE = "/members_charge.jsp";
	private static final String JSP_ORDER = "/members_order.jsp";

	// PARAM
	private static final String PARAM_MODE = "mode";
	private static final String PARAM_MODEL_NUM = "modelNum";
	private static final String PARAM_CARD_NUM = "cardNum";
	private static final String PARAM_TARGET_CARD_NUM = "targetCardNum";
	private static final String PARAM_MODE_REGISTER = "register";
	private static final String PARAM_MODE_CHARGE = "charge";
	private static final String PARAM_MODE_CLOSE = "close";
	private static final String PARAM_REGISTER_STEP = "register_step";
	private static final String PARAM_CARD_NAME = "cardName";
	private static final String PARAM_PRICE = "price";
	private static final int PARAM_REGISTER_STEP_1 = 1;
	private static final int PARAM_REGISTER_STEP_2 = 2;
	private static final int PARAM_REGISTER_STEP_3 = 3;

	// ATTRIBUTE
	private static final String ATTRIBUTE_API = "api";
	private static final String ATTRIBUTE_LIST = "list";
	private static final String ATTRIBUTE_CARD_DTO = "cardDTO";
	private static final String ATTRIBUTE_CARD_MODEL_DTO = "modelDTO";
	private static final int MAX_BALANCE = 550000;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		contextPath = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(ATTRIBUTE_API, uri.substring(uri.lastIndexOf("/")));
		SessionAuthInfo info = getSessionAuthInfo(req);
		if (info == null) {
			goToLogin(resp);
			return;
		}

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
		} else if (uri.indexOf(API_CLOSE_CARD) != -1) {
			closeForm(req, resp, attributes);
		} else if (uri.indexOf(API_CLOSE_CARD_OK) != -1) {
			closeSubmit(req, resp, attributes);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_LIST;
		CardDAO dao = new CardDAO();
		SessionAuthInfo info = getSessionAuthInfo(req);
		List<CardDTO> list = dao.listCard(info.getUserNum());
		attributes.put(ATTRIBUTE_LIST, list);
		forward(req, resp, path, attributes);
	}

	protected void detail(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_DETAIL;
		CardDAO dao = new CardDAO();
		CardDTO dto;
		SessionAuthInfo info = getSessionAuthInfo(req);
		try {
			int cardNum = Integer.parseInt(req.getParameter(PARAM_CARD_NUM));
			dto = dao.readCard(cardNum, info.getUserNum());
			if (dto == null) {
				throw new Exception("카드가 존재하지 않습니다. cardNum:" + cardNum);
			}
			attributes.put(ATTRIBUTE_CARD_DTO, dto);
			forward(req, resp, path, attributes);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LIST);
			return;
		}
	}

	protected void register(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String paramStep = req.getParameter(PARAM_REGISTER_STEP);
		try {
			int step = 1;
			// 무조건 로그인 인증하기
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
			CardDAO cardDAO = new CardDAO();
			CardChargeDAO chargeDAO = new CardChargeDAO();
			// 카드 정보 파라미터에서 받아오기
			String cardName = req.getParameter(PARAM_CARD_NAME);
			int price = Integer.parseInt(req.getParameter(PARAM_PRICE));
			int modelNum = Integer.parseInt(req.getParameter(PARAM_MODEL_NUM));
			// 카드 신규 등록
			CardDTO cardDTO = new CardDTO(cardName, info.getUserNum(), modelNum);
			int cardNum = cardDAO.insertCard(cardDTO);
			cardDTO = cardDAO.readCard(cardNum, info.getUserNum());
			// 신규 등록한 카드에 충전하기
			CardChargeDTO chargeDTO = new CardChargeDTO(cardNum, price);
			chargeDAO.insertCardCharge(chargeDTO);
			// 충전이 완료되면 목록으로 돌아가기
			resp.sendRedirect(apiPath + API_LIST);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_REGISTER);
		}
	}

	protected void chargeForm(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		SessionAuthInfo info = getSessionAuthInfo(req);
		String path = VIEWS + JSP_CHARGE;
		attributes.put(PARAM_MODE, PARAM_MODE_CHARGE);
		CardDAO dao = new CardDAO();
		CardDTO dto = null;
		try {
			String cardNum = req.getParameter(PARAM_CARD_NUM);
			if (cardNum == null) {
				dto = dao.readRecentCard(info.getUserNum());
			} else {
				dto = dao.readCard(Integer.parseInt(cardNum), info.getUserNum());
			}
			if (dto == null) {
				throw new Exception("카드가 존재하지 않습니다.");
			}
			attributes.put(ATTRIBUTE_CARD_DTO, dto);
			forward(req, resp, path, attributes);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_DETAIL);
		}
	}

	protected void chargeSubmit(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes) {
		// 충전 절차 진행
		try {
			SessionAuthInfo info = getSessionAuthInfo(req);
			int cardNum = Integer.parseInt(req.getParameter(PARAM_CARD_NUM));
			int price = Integer.parseInt(req.getParameter(PARAM_PRICE));
			CardDAO cardDAO = new CardDAO();
			CardDTO cardDTO = cardDAO.readCard(cardNum, info.getUserNum());
			if (cardDTO == null) {
				throw new Exception("카드가 존재하지 않습니다.");
			}
			if (cardDTO.getBalance() + price > MAX_BALANCE) {
				throw new ChargeException("카드 충전금액이 550,000원을 넘길 수 없습니다.");
			}
			CardChargeDAO chargeDAO = new CardChargeDAO();
			chargeDAO.insertCardCharge(new CardChargeDTO(cardNum, price));
			resp.sendRedirect(apiPath + API_DETAIL + "?" + PARAM_CARD_NUM + "=" + cardNum);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resp.sendRedirect(apiPath + API_LIST);
			} catch (IOException e1) {
			}
		}
	}

	protected void closeForm(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		SessionAuthInfo info = getSessionAuthInfo(req);
		String path = VIEWS + JSP_CHARGE;
		attributes.put(PARAM_MODE, PARAM_MODE_CLOSE);
		String cardNum = req.getParameter(PARAM_CARD_NUM);
		CardDAO dao = new CardDAO();
		try {
			// 현재 카드 정보 싣기
			attributes.put(ATTRIBUTE_CARD_DTO, dao.readCard(Integer.parseInt(cardNum), info.getUserNum()));
			// 이체할 카드 정보 싣기
			List<CardDTO> list = dao.listCard(info.getUserNum());
			for (int i = 0; i < list.size(); i++) {
				// 현재 카드 정보 빼기
				if (list.get(i).getCardNum() == Integer.parseInt(cardNum)) {
					list.remove(i);
					break;
				}
			}
			attributes.put(ATTRIBUTE_LIST, list);
			forward(req, resp, path, attributes);
		} catch (Exception e) {
			resp.sendRedirect(apiPath + API_DETAIL + "?" + PARAM_CARD_NUM + "=" + cardNum);
			return;
		}
	}

	protected void closeSubmit(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		// 해지할 카드번호(cardNum), 이체할 카드(targetCardNum)
		// 해지를 위해서 이체하는 경우에만 55만원을 초과하는 것을 허락하자!
		System.out.println("메서드 진입");
		CardDAO dao = new CardDAO();
		String closeCardNum = req.getParameter(PARAM_CARD_NUM);
		String targetCardNum = req.getParameter(PARAM_TARGET_CARD_NUM);
		try {
			System.out.println(closeCardNum + ", " + targetCardNum + "시도");
			int closeNum = Integer.parseInt(closeCardNum);
			int targetNum = Integer.parseInt(targetCardNum);
			dao.closeCard(closeNum, targetNum);
			resp.sendRedirect(apiPath + API_LIST);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_DETAIL + "?" + PARAM_CARD_NUM + "=" + closeCardNum);
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
