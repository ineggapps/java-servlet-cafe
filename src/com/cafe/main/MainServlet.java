package com.cafe.main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.Pager;

@WebServlet("/main/*")
public class MainServlet extends HttpServlet {
	//페이징
	private static final String ATTRIBUTE_DATA_COUNT = "dataCount";
	private static final String ATTRIBUTE_TOTAL_PAGE = "totalPage";
	private static final String ATTRIBUTE_CURRENT_PAGE = "currentPage";
	private static final String ATTRIBUTE_PAGES = "pages";
	private static final String ATTRIBUTE_URI = "uri";
	private static final String ATTRIBUTE_QUERY = "query";

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	public static final String VIEWS = "/WEB-INF/views/cafe";
	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = VIEWS + "/main.jsp";
		RequestDispatcher disp = req.getRequestDispatcher(path);
		
		//페이징 관련 처리 연습 코드
//		Pager pager = new Pager();
//		String page = req.getParameter("page");
//		int currentPage = page!=null&&page.length()>0?Integer.parseInt(page):1;
//		int dataCount = 55;
//		int totalPage = pager.pageCount(1, dataCount);
//		int[] pages = pager.paging(35, currentPage, dataCount);
//		for(int i=0;i<pages.length;i++) {
//			System.out.print(pages[i]);
//		}
//		System.out.println();
//		req.setAttribute(ATTRIBUTE_DATA_COUNT, dataCount);
//		req.setAttribute(ATTRIBUTE_TOTAL_PAGE, totalPage);
//		req.setAttribute(ATTRIBUTE_CURRENT_PAGE, currentPage);
//		req.setAttribute(ATTRIBUTE_PAGES, pages);
//		req.setAttribute(ATTRIBUTE_URI, req.getContextPath() + "/main");
//		req.setAttribute(ATTRIBUTE_QUERY, "?");
		
		disp.forward(req, resp);
	}
}
