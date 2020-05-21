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
	function deleteNotice(num) {
	    var query = "num="+num+"&${query}";
	    var url = "<%=cp%>/news/notice/delete.do?" + query;
	
	    if(confirm("위 자료를 삭제 하시 겠습니까 ? "))
	    	location.href=url;
	}
	
	function updateNotice(num) {
	    var page = "${page}";
	    var query = "num="+num+"&page="+page;
	    var url = "<%=cp%>/news/notice/update.do?" + query;
	
	    location.href=url;
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
            <div class="notice-board">
              <div class="noticelist-img">
                <p class="line1">공지사항</p>
                <span class="line2">ALWAYS BESIDE YOU,</span>
                <span class="line3"> COOKIE&amp;CREAM</span>
              </div>
              <div class="board-list">
                <div class="navigation">
                  <div class="nav-bar">홈&nbsp; 〉 쿠앤크소식&nbsp; 〉 공지사항</div>
                </div>
                <div class = "notice_viewBtn">
                	<c:if test="${sessionScope.member.admin == true}">				    
				        <button type="button" class="notice_updateBtn" onclick="updateNotice('${dto.num}');">수정</button>
				        <button type="button" class="notice_deleteBtn" onclick="deleteNotice('${dto.num}');">삭제</button>
				    </c:if>
                </div>
                <div class="view-title">
                  <div class="s1">${dto.subject}</div>
                  <span class="s2_1">${dto.updated_date }</span>
                  <span class="s2_2">${dto.views }</span>
                </div>
                <div class="view-con">
                  ${dto.content}
                </div>
                <div class="view-page">
                  <ul class="vp">
                    <li class="pp">이전글</li>
                    <li class="pt"><c:if test="${not empty preNoticeDto}">
			              <a href="<%=cp%>/news/notice/view.do?${query}&num=${preNoticeDto.num}">${preNoticeDto.subject}</a>
			        </c:if></li>
                  </ul>
                  <ul class="vp">
                    <li class="pp">다음글</li>
                    <li class="pt"><c:if test="${not empty nextNoticeDto}">
			              <a href="<%=cp%>/news/notice/view.do?${query}&num=${nextNoticeDto.num}">${nextNoticeDto.subject}</a>
			        </c:if></li>
                  </ul>
                  <ul class = "go-ul">
                    <li class = "go-li"><input type = "button" class = "go-listBtn" onclick="javascript:location.href='<%=cp%>/news/notice/list.do?${query}';" value="목록으로"></li>
                  </ul>
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