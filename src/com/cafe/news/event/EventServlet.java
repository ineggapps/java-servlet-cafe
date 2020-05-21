package com.cafe.news.event;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe.auth.SessionAuthInfo;
import com.util.EspressoServlet;
import com.util.MyUtil;

@WebServlet("/news/event/*")
public class EventServlet extends EspressoServlet {

	// PATH
	private static final String API_NAME = "/news/event";
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
	private static final String API_VIEW = "/view.do";
	private static final String API_WRITE = "/write.do";
	private static final String API_WRITE_OK = "/write_ok.do";
	private static final String API_UPDATE = "/update.do";
	private static final String API_UPDATE_OK = "/update_ok.do";
	private static final String API_DELETE = "/delete.do";

	// PARAM (이런 식으로 자주 사용하는 문자열은 상수로 저장하여 활용)
	private static final String PARAM_MODE = "mode";
	private static final String PARAM_NUM = "num";
	private static final String PARAM_PAGE = "page";
	private static final String ATTRIBUTE_MODE = "mode";
	private static final String ATTRIBUTE_MODE_CREATED = "created";
	private static final String ATTRIBUTE_MODE_UPDATE = "update";
	private static final String ATTRIBUTE_SUBJECT = "subject";
	private static final String ATTRIBUTE_CONTENT = "content";
	private static final String ATTRIBUTE_CURRENT_PAGE = "current_page";
	private static final String ATTRIBUTE_TOTAL_PAGE = "total_page";
	private static final String ATTRIBUTE_DATA_COUNT = "dataCount";
	private static final String ATTRIBUTE_IMAGE_PATH = "image_path";
	private static final String ATTRIBUTE_PAGING = "paging";
	private static final String ATTRIBUTE_LIST = "list";
	private static final String ATTRIBUTE_LIST_URL = "listUrl";
	private static final String ATTRIBUTE_ARTICLE_URL = "articleUrl";
	private static final String ATTRIBUTE_DTO = "dto";
	private static final String ATTRIBUTE_QUERY = "query";

	// JSP
	private static final String JSP_LIST = "/news_event_list.jsp";
	private static final String JSP_WRITE = "/news_event_write.jsp";
	private static final String JSP_UPDATE = JSP_WRITE;
	private static final String JSP_VIEW = "/news_event_view.jsp";

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		contextPath = req.getContextPath();
		apiPath = contextPath + API_NAME;
		String uri = req.getRequestURI();
		if (uri.indexOf(API_INDEX) != -1 || uri.indexOf(API_LIST) != -1) {
			list(req, resp);
		} else if (uri.indexOf(API_VIEW) != -1) {
			view(req, resp);
		} else if (uri.indexOf(API_WRITE) != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf(API_WRITE_OK) != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf(API_UPDATE) != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf(API_UPDATE_OK) != -1) {
			updateSubmit(req, resp);
		}
	}

	// 이벤트 목록
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_LIST;
		EventDAO dao = new EventDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page = 1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
		keyword = URLDecoder.decode(keyword, "utf-8");
		}
		
		// 전체 데이터 개수
		int dataCount;
		
		if(keyword.length()!=0) {
			dataCount = dao.dataCount(condition, keyword);
		} else {
			dataCount = dao.dataCount();
		}
		
		// 전체 페이지 수
		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset = (current_page-1)*rows;
		if(offset<0) offset=0;
		
		List<EventDTO> list;
		if(keyword.length()!=0) {
			list = dao.listEvent(offset, rows, condition, keyword);
		} else {
			list = dao.listEvent(offset, rows);
		}
		
		// 리스트 글번호
		int listNum, n=0;
		for(EventDTO dto : list) {
			listNum = dataCount-(offset+n);
			dto.setNum(listNum);
			n++;
		}
		
		String query="";
		if(keyword.length()!=0) {
			query = "condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		// 페이징
		String listUrl = cp+API_LIST;
		String articleUrl = cp+API_NAME + API_VIEW + "?page="+current_page;
		if(query.length()!=0) {
			listUrl+="?"+query;
			articleUrl=articleUrl+"&"+query;
		}
		
		String paging = util.paging(current_page, total_page, listUrl);
		
		// list.jsp 파일에 데이터 넘겨주기
		req.setAttribute(ATTRIBUTE_LIST, list);
		req.setAttribute(ATTRIBUTE_PAGING, paging);
		req.setAttribute(PARAM_PAGE, page);
		req.setAttribute(ATTRIBUTE_TOTAL_PAGE, total_page);
		req.setAttribute(ATTRIBUTE_DATA_COUNT, dataCount);
		req.setAttribute(ATTRIBUTE_ARTICLE_URL, articleUrl);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
			
		forward(req, resp, path);
	}

	// 이벤트 보기
	protected void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_VIEW;
		forward(req, resp, path);
	}

	// 이벤트 작성
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_WRITE;
		forward(req, resp, path);
	}
	
	protected boolean isBlank(String str) {
		if(str==null || str.length()==0) {
			return true;
		}
		return false;
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EventDAO dao = new EventDAO();
		EventDTO dto = new EventDTO();
		try {
			SessionAuthInfo info = getSessionAuthInfo(req);
			//info.isAdmin(); //true 관리자 false 일반 사용자
			String subject = req.getParameter("subject");
			String content = req.getParameter("content");
			String start_date = req.getParameter("start_date");
			String end_date = req.getParameter("end_date");
			
			 System.out.println(subject);
			if(isBlank(subject) || isBlank(content) || isBlank(start_date) || isBlank(end_date)) {
				throw new Exception("게시물 내용이 입력되지 않았음");
			}
			
			dto.setUserNum(info.getUserNum());
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setStart_date(start_date);
			dto.setEnd_date(end_date);
			dao.insertEvent(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(apiPath + API_LIST);
	}

	// 이벤트 수정
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_LIST;
		forward(req, resp, path);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	// 이벤트 지우기
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//	EventDAO dao = new EventDAO();
	//	String path = 
	}
}