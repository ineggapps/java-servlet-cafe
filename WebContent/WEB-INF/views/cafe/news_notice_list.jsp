<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1.0" /> -->
    <title>COFFEE</title>
    <link rel="stylesheet" href="<%=cp%>/resource/css/reset.css" />
    <link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" />
    <link rel="stylesheet" href="<%=cp%>/resource/css/board_notice.css" />
    
    <script type="text/javascript">
    
    function searchList() {
		var f=document.searchForm;
		f.submit();
	}
	
	function listNum() {
		var f=document.listNumForm;
		f.submit();
	}
    
    </script>
  </head>
  <body>
    <div id="wrap">
      <header id="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
      </header>
      <main id="content">
        <div id="main">
          <article id="main_container">
            <!-- Content영역 -->

         
            <div class = "notice-board">
	            <div class = "noticelist-img">
	            	<p class = "line1">공지사항</p>
	            	<span class = line2>ALWAYS BESIDE YOU,</span>
	            	<span class = "line3"> COOKIE&amp;CREAM</span>
	            </div>
			    <div class = "board-list">
				    <div class="navigation">
				        <div class="nav-bar">홈 &nbsp;〉 쿠앤크소식 &nbsp;〉 공지사항</div>
				    </div>
				    
				    <div>
				    <form name="listNumForm" action="<%=cp%>/news/notice/list.do" method="post">
						<table class = "notice_selectNum">
						   <tr class = "notice_selectNum_tr">
						      <td class = "notice_selectNum_td">
						          <select name="rows" class="notice_selectField" onchange="listNum()">
						          		<option value="5"  ${rows==5 ? "selected='selected' ": "" }>5개</option>
						          		<option value="10" ${rows==10 ? "selected='selected' ": "" }>10개</option>
						          		<option value="20" ${rows==20 ? "selected='selected' ": "" }>20개</option>
						          </select>
						          <input type="hidden" name="page" value="${page}">
						          <input type="hidden" name="condition" value="${condition}">
						          <input type="hidden" name="keyword" value="${keyword}">
						      </td>
						   </tr>
						</table>
					</form>
				    
				    </div>
				    
			    <c:forEach var="dto" items="${list}">
			    	<ul class = "list-ul">
			    		<li>
			    			<div class = list-num>${dto.listNum}</div>
			    			<div class = list-title>
			    				<a href = "${articleUrl}&num=${dto.num}">${dto.subject }</a>
			    				<span class = "viewCount">${dto.views }</span>
			    				<span class = "created_date">${dto.updated_date }</span>
			    			</div>
			    		</li>
			    	</ul>
			    </c:forEach>
			    
			    <div class = "noticelist-btn">
			    	<button type="button" class="re_btn" onclick="javascript:location.href='<%=cp %>/news/notice/list.do';">새로고침</button>
			    	<c:if test="${sessionScope.member.admin == true }">
				    	<button type="button" class="upload_btn" onclick="javascript:location.href='<%=cp %>/news/notice/write.do';">글올리기</button>
				    </c:if>
			    </div>
			   
			   
			   
			<form name="searchForm" action="<%=cp%>/news/notice/list.do" method="post">
			    <table class = "notice_search">
				   <tr height="40">
				      <td align="center">
				              <select name="condition" class="notice_selectField">
				                  <option value="subject"     ${condition=="subject"?"selected='selected'":"" }>제목</option>
				                  <option value="content"     ${condition=="content"?"selected='selected'":"" }>내용</option>
				                  <option value="created"     ${condition=="created"?"selected='selected'":"" }>등록일</option>
				            </select>
				            <input type="text" name="keyword" class="notice_boxTF" value="${keyword}">
				            <input type="hidden" name="rows" value="${rows}">
				            <button type="button" class="noticeSearch_btn" onclick="searchList()">검색</button>
				      </td>
				   </tr>
			    </table>
			</form>
			 
			 
			    
			    <div class = "page">
			    	 ${dataCount == 0 ? "등록된 게시물이 없습니다." : paging }
	   			</div>
			    </div> 
   			</div>
   			
            <!-- Content 영역 끝 -->
          </article>
        </div>
      </main>
      <footer id="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
      </footer>
    </div>
  </body>
</html>