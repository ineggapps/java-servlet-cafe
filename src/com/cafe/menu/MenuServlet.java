package com.cafe.menu;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.cafe.auth.SessionAuthInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;
import com.util.Pager;

@WebServlet("/menu/*")
@MultipartConfig(						
		maxFileSize = 1024*1024*5,		
		maxRequestSize = 1024*1024*5*5	
)
public class MenuServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	private String pathname;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri=req.getRequestURI();
		// 이미지 저장 경로
		HttpSession session=req.getSession();
		String root=session.getServletContext().getRealPath("/");
		pathname=root+File.separator+"thumbnail"+File.separator+"menu";
		
		if (uri.indexOf("/coffee.do") != -1 || uri.indexOf("/index.do") != -1 || uri.indexOf("/ade.do") != -1 || uri.indexOf("/bakery.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("/createdMenu.do") != -1) {
			createdMenu(req, resp);
		} else if(uri.indexOf("/createdMenu_ok.do")!=-1) {
			createdMenuSubmit(req, resp);
		} else if(uri.indexOf("/update.do") != -1) {
			updateMenu(req, resp);
		} else if(uri.indexOf("/update_ok.do") != -1) {
			updateMenuSubmit(req, resp);
		} else if(uri.indexOf("/delete.do") != -1) {
			deleteMenu(req, resp);
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

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	// 페이징 처리 리스트 마다 한 것 
		String uri=req.getRequestURI();
		String view="coffee";	// 커피일 경우에
		int categoryNum = 1;
		if(uri.indexOf("/ade.do") != -1) {	// 에이드일 경우에
			view="ade";
			categoryNum = 2;
		} else if(uri.indexOf("/bakery.do") != -1) {  // 베이커리일 경우에
			view="bakery";
			categoryNum = 3;
		}
		
		String path = "/WEB-INF/views/cafe/menu/"+view+".jsp";	// view를 따로 나누어서 path 주소를 설정
		try {
			
			String cp = req.getContextPath();
			MenuDAO dao = new MenuDAO();
			MyUtil util = new MyUtil();
			
			String page = req.getParameter("page");
			int current_page=1;
			if(page!=null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount  = dao.dataCount(categoryNum);
			
			int rows = 6;
			int total_page = util.pageCount(rows, dataCount);
			if(current_page>total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page-1)*rows;
			
			List<MenuDTO> list = dao.listMenu(offset, rows, categoryNum);
			//페이징 관련 처리
			Pager pager = new Pager();
			int[] pages = pager.paging(rows, current_page, total_page);

			
			String listUrl = cp+"/menu/"+view+".do";
			String createdUrl = cp + "/menu/create.do?page="+current_page;
//			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("createdUrl", createdUrl);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
//			req.setAttribute("paging", paging);
			req.setAttribute("pages", pages);
			req.setAttribute("listUrl", listUrl);
			req.setAttribute("current_page", current_page);
			req.setAttribute("api", req.getRequestURI().substring(cp.length()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 포워딩
		forward(req, resp, path);
	}
	
	
	protected void createdMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/menu/created.jsp";
		
		req.setAttribute("mode", "created");
		
		forward(req, resp, path);
	}

	protected void createdMenuSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		MenuDAO dao = new MenuDAO();
		MenuDTO dto = new MenuDTO();
		
		dto.setCategoryNum(Integer.parseInt(req.getParameter("categoryNum")));
		dto.setMenuName(req.getParameter("menuName"));
		dto.setText(req.getParameter("text"));	
		dto.setPrice(Integer.parseInt(req.getParameter("price")));
		
		String thumbnail = null;
		Part p = req.getPart("thumbnail");
		Map<String, String> map = doFileUpload(p, pathname);
		if(map != null) {
			thumbnail = map.get("saveFilename");
		}
		
		if(thumbnail != null) {
			dto.setThumbnail("/thumbnail/menu/"+thumbnail);
			dao.insertMenu(dto);
		}
		
		resp.sendRedirect(cp+"/menu/coffee.do");
	}
	
	protected void updateMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/menu/created.jsp";
		String cp = req.getContextPath();
		HttpSession session=req.getSession();
		SessionAuthInfo info=(SessionAuthInfo) session.getAttribute("member");
		
		MenuDAO dao = new MenuDAO();
		
		int menuNum = Integer.parseInt(req.getParameter("menuNum")); 
		MenuDTO dto = dao.readMenu(menuNum);
		
		if(info==null || ! info.getUserId().equals("hello")) {
			resp.sendRedirect(cp+"/menu/coffee.do");
			return;
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("mode", "update");
		
		forward(req, resp, path);
	}
	
	protected void updateMenuSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		MenuDAO dao = new MenuDAO();
		MenuDTO dto = new MenuDTO();
		
		String thumbnail = req.getParameter("saveFilename");
		dto.setMenuNum(Integer.parseInt(req.getParameter("menuNum")));
		dto.setCategoryNum(Integer.parseInt(req.getParameter("categoryNum")));
		dto.setMenuName(req.getParameter("menuName"));
		dto.setText(req.getParameter("text"));	
		dto.setPrice(Integer.parseInt(req.getParameter("price")));
		
		Part p = req.getPart("thumbnail");
		Map<String, String> map = doFileUpload(p, pathname);
		if(map != null) {
			String filename = map.get("saveFilename");
			FileManager.doFiledelete(pathname,thumbnail);
			dto.setThumbnail( "/thumbnail/menu/" + filename);
		} else {
			dto.setThumbnail(thumbnail);
		}
		
		dao.updateMenu(dto);
		
		resp.sendRedirect(cp+"/menu/coffee.do");
	}
	
	protected void deleteMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		SessionAuthInfo info = (SessionAuthInfo)session.getAttribute("member");
		
		MenuDAO dao = new MenuDAO();
		
		int menuNum = Integer.parseInt(req.getParameter("menuNum"));

		MenuDTO dto = dao.readMenu(menuNum);
		if(dto == null) {
			resp.sendRedirect(cp+"/menu/coffee.do");
			return;
		}
		
		if(info == null || ! info.getUserId().equals("hello")) {
			resp.sendRedirect(cp+"/menu/coffee.do");
			return;
		}
		
		FileManager.doFiledelete(pathname, dto.getThumbnail());
		
		dao.deleteMenu(menuNum);
		
		resp.sendRedirect(cp+"/menu/coffee.do");
	}
}