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

          <form name = "noticeListForm" action="<%=cp%>/notice/list.do" method="post" >
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
			    <c:forEach var="dto" items="${listNotice}">
			    	<ul class = "list-ul">
			    		<li>
			    			<div class = list-num>${dto.num }</div>
			    			<div class = list-title>
			    				<a href = "${articleUrl}&num=${dto.num}">${dto.subject }</a>
			    				<span>${dto.created_date }</span>
			    			</div>
			    		</li>
			    	</ul>
			    	<div class = "btn">
			    		<button type="button" class="re_btn" onclick="javascript:location.href='<%=cp %>/notice/list.do';">새로고침</button>
			    		<c:if test="${sessionScope.member.userId == 'admin' }">
				          <button type="button" class="upload_btn" onclick="javascript:location.href='<%=cp %>/notice/created.do';">글올리기</button>
				        </c:if>
			    	</div>
			    </c:forEach>
			    <div class = "page">
			    	 ${dataCount == 0 ? "등록된 게시물이 없습니다." : paging }
	   			</div>
			    </div>
			    
   			</div>
   		</form>
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