package com.cafe.news.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyUploadServlet;
import com.util.MyUtil;

@WebServlet("/news/notice/*")
@MultipartConfig
public class NoticeServlet extends MyUploadServlet {
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
		
		
		forward(req, resp, path);
	}

	// 공지사항 보기
	protected void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_VIEW;
		forward(req, resp, path);
	}

	// 공지사항 작성
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_LIST;
		forward(req, resp, path);
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	// 공지사항 수정
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + JSP_LIST;
		forward(req, resp, path);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	// 공지사항 지우기
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
