package com.cafe.news.notice;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.EspressoServlet;
import com.util.MyUtil;

@WebServlet("/news/notice/*")
public class NoticeServlet extends EspressoServlet {
	private static final long serialVersionUID = 1L;
	
	// PATH
	private static final String API_NAME = "/news/notice";
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
	private static final String JSP_LIST = "/news_notice_list.jsp";
	private static final String JSP_WRITE = "/news_notice_write.jsp";
	private static final String JSP_UPDATE = JSP_WRITE;
	private static final String JSP_VIEW = "/news_notice_view.jsp";
	

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

	// 공지사항 목록
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_LIST;
		NoticeDAO dao = new NoticeDAO();
		MyUtil util = new MyUtil();
		
		String page = req.getParameter(PARAM_PAGE);
		int current_page = 1;
		if(page != null) {
			current_page = Integer.parseInt(page);
		}
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		
		if(condition == null) {	
			condition = "subject";
			keyword = "";	
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}
		
		int dataCount;
		
		if(keyword.length()==0) {
			dataCount = dao.dataCount();
		} else {			
			dataCount = dao.dataCount(condition, keyword);
		}
		
		int rows = 10;
		String numPerPage = req.getParameter("rows");
		if(numPerPage != null) {
			rows = Integer.parseInt(numPerPage);
		}
		
		int total_page = util.pageCount(rows, dataCount);
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		int start = (current_page-1)*rows+1;
		int end = current_page*rows;
		
		List<NoticeDTO> list;
		
		if(keyword.length()==0) {
			list = dao.listNotice(start, end);
		} else {
			list = dao.listNotice(start, end, condition, keyword);
		}
		
		int listNum, n = 0;
		
		Iterator<NoticeDTO> it = list.iterator();
		
		while(it.hasNext()) {
			NoticeDTO dto = it.next();
			listNum = dataCount-(start+n-1);
			dto.setListNum(listNum);
			n++;
		}
		
		String query = "";
		
		if(keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword,"UTF-8"); 
		}
		
		String listUrl = contextPath + API_NAME + API_LIST;		// contextPath + "/news/notice/list.do";
		String articleUrl = contextPath + API_NAME + API_VIEW + "?page=" + current_page;	// contextPath + "/news/notice/view.do?page=" + current_page;
		
		if(query.length()!=0) {
			listUrl+= "?" + query;
			articleUrl += "&"+query;
		}
		
		String paging = util.paging(current_page, total_page, listUrl);
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("rows", rows);
		
		forward(req, resp, path);
	}

	// 공지사항 보기
	protected void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_VIEW;
		forward(req, resp, path);
	}

	// 공지사항 작성
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_WRITE;	
		forward(req, resp, path);
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	// 공지사항 수정
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + API_WRITE;
		forward(req, resp, path);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	// 공지사항 지우기
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
