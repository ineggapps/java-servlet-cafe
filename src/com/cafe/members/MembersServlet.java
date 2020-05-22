package com.cafe.members;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe.auth.SessionAuthInfo;
import com.cafe.menu.MenuDAO;
import com.cafe.menu.MenuDTO;
import com.util.EspressoServlet;
import com.util.Pager;

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
	private static final String API_MODIFY_CARD_NAME = "/modifyCardName.do";
	private static final String API_REGISTER = "/register.do";
	private static final String API_CHARGE = "/charge.do";
	private static final String API_CHARGE_OK = "/charge_ok.do";
	private static final String API_ORDER = "/order.do";
	private static final String API_BUY = "/buy.do";
	private static final String API_BUY_OK = "/buy_ok.do";
	private static final String API_ORDERED_LIST = "/orderedList.do";
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
	private static final String PARAM_MENU_NUM = "menuNum";
	private static final String PARAM_TAB = "tab";
//	private static final String PARAM_TAB_USAGE = "usage";
//	private static final String PARAM_TAB_CHARGE = "charge";
	private static final int PARAM_REGISTER_STEP_1 = 1;
	private static final int PARAM_REGISTER_STEP_2 = 2;
	private static final int PARAM_REGISTER_STEP_3 = 3;

	// ATTRIBUTE
	private static final String ATTRIBUTE_API = "api";
	private static final String ATTRIBUTE_LIST = "list";
	private static final String ATTRIBUTE_ORDER_HISTORY = "orderHistory";
	private static final String ATTRIBUTE_CARD_CHARGE_LIST = "cardChargeList";
	private static final String ATTRIBUTE_CARDS = "cards";
	private static final String ATTRIBUTE_ERROR_MSG = "errorMessage";
	private static final String ATTRIBUTE_CARD_DTO = "cardDTO";
	private static final String ATTRIBUTE_CARD_MODEL_DTO = "modelDTO";
	private static final String ATTRIBUTE_MAX_ITEM_AMOUNT = "maxItemAmount";

	//기본 속성
	private static final int MAX_BALANCE = 550000;
	private static final int MAX_ITEM_AMOUNT = 15; // 최대 구매 가능 개수

	// 장바구니 세션
	private static final String SESSION_CART = "cart";

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
		} else if (uri.indexOf(API_BUY) != -1) {
			buyForm(req, resp, attributes);
		} else if (uri.indexOf(API_BUY_OK) != -1) {
			buySubmit(req, resp, attributes);
		} else if (uri.indexOf(API_ORDERED_LIST) != -1) {
			orderedList(req, resp, attributes);
		} else if (uri.indexOf(API_CLOSE_CARD) != -1) {
			closeForm(req, resp, attributes);
		} else if (uri.indexOf(API_CLOSE_CARD_OK) != -1) {
			closeSubmit(req, resp, attributes);
		} else if (uri.indexOf(API_MODIFY_CARD_NAME)!=-1) {
			updateCardName(req, resp, attributes);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		int rows = 6;
		String path = VIEWS + JSP_LIST;
		try {
			CardDAO dao = new CardDAO();
			SessionAuthInfo info = getSessionAuthInfo(req);
			//페이징 관련 처리
			Pager pager = new Pager();
			String page = req.getParameter(PARAM_PAGE);
			int currentPage = page!=null&&page.length()>0?Integer.parseInt(page):1;
			int dataCount = dao.count(info.getUserNum());
			System.out.println(dataCount+"개 보유");
			int totalPage = pager.pageCount(rows, dataCount);
			int[] pages = pager.paging(rows, currentPage, totalPage);
			//페이징 관련 attributes 삽입
			System.out.println(pager.getOffset(currentPage, rows) + "번부터 시작");
			System.out.println(currentPage + "/" + totalPage + ">" + pager.getOffset(currentPage, rows) );
			setPagerAttributes(dataCount, currentPage, totalPage, pages, apiPath + API_LIST, "", attributes);
			List<CardDTO> list = dao.listCard(info.getUserNum(), pager.getOffset(currentPage, rows),rows);
			attributes.put(ATTRIBUTE_LIST, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, path, attributes);
	}

	protected void detail(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_DETAIL;
		String tab = req.getParameter(PARAM_TAB);
		CardDAO dao = new CardDAO();
		CardChargeDAO chargeDAO = new CardChargeDAO();
		OrderDAO orderDAO = new OrderDAO();
		CardDTO dto;
		SessionAuthInfo info = getSessionAuthInfo(req);
		try {
			int cardNum = Integer.parseInt(req.getParameter(PARAM_CARD_NUM));
			dto = dao.readCard(cardNum, info.getUserNum());
			if (dto == null) {
				throw new Exception("카드가 존재하지 않습니다. cardNum:" + cardNum);
			}
			List<OrderHistoryDTO> historyList; 
			List<CardChargeDTO> chargeList;
			if(tab==null || tab.equalsIgnoreCase("usage")) {
				historyList = orderDAO.listOrderHistoryByCardNum(cardNum, info.getUserNum());
				attributes.put(ATTRIBUTE_ORDER_HISTORY, historyList);
			}else {
				chargeList = chargeDAO.listCardCharge(cardNum, info.getUserNum());
				attributes.put(ATTRIBUTE_CARD_CHARGE_LIST, chargeList);
			}
			attributes.put(PARAM_TAB, tab);
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
		final int rows = 12;
		String path = VIEWS + JSP_REGISTER_STEP1;
		// 카드모델 고르기 페이지
		CardModelDAO dao = new CardModelDAO();
		try {
			//페이징 관련 처리
			Pager pager = new Pager();
			String page = req.getParameter(PARAM_PAGE);
			int currentPage = page!=null&&page.length()>0?Integer.parseInt(page):1;
			int dataCount = dao.count();
			int totalPage = pager.pageCount(rows, dataCount);
			int[] pages = pager.paging(rows, currentPage, totalPage);
			List<CardModelDTO> list = dao.listCardModel(pager.getOffset(currentPage, rows), rows);
			//페이징 관련 attributes 삽입
			setPagerAttributes(dataCount, currentPage, totalPage, pages, apiPath + API_REGISTER, "step=" + PARAM_REGISTER_STEP_1, attributes);
			attributes.put(PARAM_MODE, PARAM_MODE_REGISTER);
			attributes.put(ATTRIBUTE_LIST, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	protected void updateCardName(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		SessionAuthInfo info = getSessionAuthInfo(req);
		String uri = req.getRequestURI();
		String cardNum = req.getParameter(PARAM_CARD_NUM);
		String cardName = req.getParameter(PARAM_CARD_NAME);
		try {
			CardDAO dao = new CardDAO();
			int cNum = Integer.parseInt(cardNum);
			dao.updateCardName(info.getUserNum(), cNum, cardName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(apiPath + API_DETAIL + "?" + PARAM_CARD_NUM + "=" + cardNum);
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
		String menuNum = req.getParameter(PARAM_MENU_NUM);
		attributes.put(ATTRIBUTE_MAX_ITEM_AMOUNT, MAX_ITEM_AMOUNT);
		try {
			SessionCart cart = getCart(req);
			// #1. 메뉴 리스트 뽑기
			MenuDAO menuDAO = new MenuDAO();
			List<MenuDTO> list = menuDAO.listAllMenu(0, 100);
			// #2. 따로 장바구니 페이지를 만들어 두는 게 좋을 듯..
			if(menuNum!=null && menuNum.length()>0) {
				int mNum = Integer.parseInt(menuNum);
				MenuDTO dto = menuDAO.readMenu(mNum); //TODO: 메서드명 수정하기
				if(dto!=null) {
					//TODO: 세션에 주문내역 추가하기
				}
			}
			boolean isAdded = addCart(menuNum, cart, menuDAO);
			if (isAdded == false && menuNum != null && cart.getTotalQuantity()>MAX_ITEM_AMOUNT) {
				attributes.put(ATTRIBUTE_ERROR_MSG, new ErrorMessage("구매 개수를 초과하였습니다.", "주문은 총 수량 15개까지만 구매가 가능합니다."));
			}
			attributes.put(ATTRIBUTE_LIST, list);
			attributes.put(SESSION_CART, cart);
			forward(req, resp, path, attributes);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LIST);
			return;
		}
	}

	private boolean addCart(String menuNum, SessionCart cart, MenuDAO menuDAO) {
		// 카트에 추가되면 true/ 안 되면 false를 반환함
		try {
			if (menuNum != null && menuNum.length() > 0) {
				if (cart.getItems().size() < MAX_ITEM_AMOUNT) {
					// 20개 미만만 카트에 담을 수 있음
					int mNum = Integer.parseInt(menuNum);
					MenuDTO dto = getCartItem(mNum, cart);
					if (dto != null) {
						// 카트에 있으면 카트에서 객체 복사하기
						dto.setQuantity(dto.getQuantity()+1);//수량 더하기
					} else {
						// 카드에 없으면 DB에서 불러오기
						dto = menuDAO.readMenu(mNum); // TODO: 메서드명 수정하기
						if (dto != null) {
							cart.addItem(dto);
						}
						return true;
					}
				} else {// 20개를 초과한 경우 메시지 출력하기
						// TODO: 코드 작성
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected void buyForm(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_ORDER;
		try {
			// #0. 추가되는 파라미터 있으면 받아서 추가 먼저 하기
			SessionAuthInfo info = getSessionAuthInfo(req);
			MenuDAO menuDAO = new MenuDAO();
			CardDAO cardDAO = new CardDAO();
			String menuNum = req.getParameter(PARAM_MENU_NUM);
			if (menuNum != null && menuNum.length() > 0) {
				addCart(menuNum, getCart(req), menuDAO);
			}
			// #1. 메뉴 리스트 뽑기
			List<MenuDTO> list = menuDAO.listAllMenu(0, 100);
			// #2. 결제수단 고르기 위해 카드 목록 뽑기
			List<CardDTO> cards = cardDAO.listCard(info.getUserNum());
			// 대부분의 쇼핑몰이 얼마나 담겼는지는 안 보여주네
			attributes.put(ATTRIBUTE_LIST, list);
			attributes.put(ATTRIBUTE_CARDS, cards);
			forward(req, resp, path, attributes);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LIST);
			return;
		}
	}

	protected void buySubmit(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
//		String path = apiPath + API_ORDER;
		try {
			// TODO: 결제
			// 카트 기반으로 가격 구성
			SessionAuthInfo info = getSessionAuthInfo(req);
			int cardNum = Integer.parseInt(req.getParameter(PARAM_CARD_NUM));
			SessionCart cart = getCart(req);
			OrderDAO dao = new OrderDAO();
			dao.addOrderHistory(cart, info.getUserNum(), cardNum);//storenum 지금 당장은 의미 없음
			//결제가 무사히 완료되었으면 카트를 비운다.
			clearCart(req);
			resp.sendRedirect(apiPath + API_ORDERED_LIST);
		} catch (OrderException e) {
			//주문에 실패한 경우
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_ORDER);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LIST);
			return;
		}
	}

	protected void orderedList(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> attributes)
			throws ServletException, IOException {
		String path = VIEWS + JSP_ORDER;
		final int rows = 10;
		try {
			SessionAuthInfo info = getSessionAuthInfo(req);
			OrderDAO dao = new OrderDAO();
			//페이징 관련 처리
			Pager pager = new Pager();
			String page = req.getParameter(PARAM_PAGE);
			int currentPage = page!=null&&page.length()>0?Integer.parseInt(page):1;
			int dataCount = dao.orderCountByUserNum(info.getUserNum());
			int totalPage = pager.pageCount(rows, dataCount);
			int[] pages = pager.paging(rows, currentPage, totalPage);
			//페이징 관련 attributes 삽입
			setPagerAttributes(dataCount, currentPage, totalPage, pages, apiPath + API_ORDERED_LIST, "", attributes);
			//DB에서 불러오기
			List<OrderHistoryDTO> orderHistory = dao.listOrderHistoryByUserNum(info.getUserNum(),  pager.getOffset(currentPage, rows), rows);
			attributes.put(ATTRIBUTE_ORDER_HISTORY, orderHistory);
			forward(req, resp, path, attributes);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(apiPath + API_LIST);
			return;
		}
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

	private MenuDTO getCartItem(int menuNum, SessionCart cart) throws Exception {
		try {
			return cart.getItems().get(menuNum);
//			List<MenuDTO> items = cart.getItems();
//			for (MenuDTO dto : items) {
//				if (menuNum == dto.getMenuNum()) {
////					MenuDTO newDTO = new MenuDTO();
////					newDTO.setMenuNum(dto.getMenuNum());
////					newDTO.setCategoryNum(dto.getCategoryNum());
////					newDTO.setMenuName(dto.getMenuName());
////					newDTO.setThumbnail(dto.getThumbnail());
////					newDTO.setText(dto.getText());
////					newDTO.setPrice(dto.getPrice());
////					return newDTO;
//					return dto;
//				}
//			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	private SessionCart getCart(HttpServletRequest req) throws Exception {
		try {
			SessionCart cart = (SessionCart) req.getSession().getAttribute(SESSION_CART);
			if (cart == null) {
				cart = new SessionCart();
				req.getSession().setAttribute(SESSION_CART, cart);
			}
			return cart;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private void clearCart(HttpServletRequest req) throws Exception{
		try {
			req.getSession().setAttribute(SESSION_CART, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
