package com.cafe.menu;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cafe.auth.SessionAuthInfo;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@WebServlet("/menu/*")
@MultipartConfig(						
		maxFileSize = 1024*1024*5,		
		maxRequestSize = 1024*1024*5*5	
)
public class MenuServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		// 이미지 저장 경로
		if (uri.indexOf("/coffee.do") != -1 || uri.indexOf("/index.do") != -1) {
			coffee(req, resp);
		} else if (uri.indexOf("/ade.do") != -1) {
			ade(req, resp);
		} else if (uri.indexOf("/bakery.do") != -1) {
			bakery(req, resp);
		} else if (uri.indexOf("/createdMenu.do") != -1) {
			createdMenu(req, resp);
		}

	}
	
	protected SessionAuthInfo getSessionAuthInfo(HttpServletRequest req) {
		if(req==null) {
			return null;
		}
		HttpSession session = req.getSession();
		SessionAuthInfo info = (SessionAuthInfo) session.getAttribute("member");
		return info;
	}

	protected void coffee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/menu/coffee.jsp";
		String cp = req.getContextPath();
		MenuDAO dao = new MenuDAO();
		MyUtil util = new MyUtil();
		
		String page = req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page = Integer.parseInt(page);
		}
		
		int dataCount  = dao.dataCount();
		
		int rows = 6;
		int total_page = util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page = total_page;
		}
		
		int offset = (current_page-1)*rows;
		
		int categoryNum = 1;
		List<MenuDTO> list = dao.listMenu(offset, rows, categoryNum);
		
		String listUrl = cp+"/menu/coffee.do";
		String createdUrl = cp + "/menu/create.do?page="+current_page;
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("createdUrl", createdUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		
		
		// 포워딩
		forward(req, resp, path);
	}

	protected void ade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/menu/ade.jsp";
		String cp = req.getContextPath();
		MenuDAO dao = new MenuDAO();
		MyUtil util = new MyUtil();
		
		String page = req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page = Integer.parseInt(page);
		}
		
		int dataCount  = dao.dataCount();
		
		int rows = 6;
		int total_page = util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page = total_page;
		}
		
		int offset = (current_page-1)*rows;
		
		int categoryNum = 2;
		List<MenuDTO> list = dao.listMenu(offset, rows, categoryNum);
		
		String listUrl = cp+"/menu/ade.do";
		String createdUrl = cp + "/menu/create.do?page="+current_page;
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("createdUrl", createdUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		// 포워딩
		forward(req, resp, path);
	}

	protected void bakery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/menu/bakery.jsp";
		String cp = req.getContextPath();
		MenuDAO dao = new MenuDAO();
		MyUtil util = new MyUtil();
		
		String page = req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page = Integer.parseInt(page);
		}
		
		int dataCount  = dao.dataCount();
		
		int rows = 6;
		int total_page = util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page = total_page;
		}
		
		int offset = (current_page-1)*rows;
		
		int categoryNum = 3;
		List<MenuDTO> list = dao.listMenu(offset, rows, categoryNum);
		
		String listUrl = cp+"/menu/bakery.do";
		String createdUrl = cp + "/menu/create.do?page="+current_page;
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("createdUrl", createdUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		// 포워딩
		forward(req, resp, path);
	}
	
	protected void createdMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/menu/created.jsp";
		String cp = req.getContextPath();
		
		SessionAuthInfo info = getSessionAuthInfo(req);
		
		if(info == null) { 
			resp.sendRedirect(cp+"/cafe/login.do");
			return;
		}
		
		if(! info.isAdmin()) {
			
		}

		
		
		
		// 포워딩
		forward(req, resp, path);
	}

}
