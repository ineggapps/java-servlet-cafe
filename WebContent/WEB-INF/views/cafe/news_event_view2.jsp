<%@page import="java.net.URLEncoder"%>
<%@page import="com.cafe.news.event.EventDTO"%>
<%@page import="com.cafe.news.event.EventDAO"%>
<%@page import="java.net.URLDecoder"%>
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
    <link rel="stylesheet" href="<%=cp%>/resource/css/event.css" />
    
    <style type="text/css">
      .notice-board {
        min-height: 1000px;
      }

      .view-title {
        height: 65px;
        line-height: 65px;
        border-top: 2px solid #233e83;
        margin-top: 50px;
        text-align: center;
        border-bottom: 1px solid #d9d9d9;
      }

      .s1 {
        width: 80%;
        display: inline-block;
        font-size: 20px;
      }

      .s2 {
        width: 20%;
        text-align: center;
        margin: 0px auto;
      }

      .view-con {
        min-height: 250px;
        padding: 30px;
        border-bottom: 2px solid #233e83;
      }

      .view-page {
        min-height: 80px;
      }

      .vp li {
        display: inline-block;
      }

      .vp {
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid #d9d9d9;
      }
        
    </style>  
    
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
                <p class="line1">이벤트</p>
                <span class="line2">ALWAYS BESIDE YOU,</span>
                <span class="line3"> COOKIE&amp;CREAM</span>
              </div>
              <div class="board-list">
                <div class="navigation">
                  <div class="nav-bar">홈&nbsp; 〉 쿠앤크소식&nbsp; 〉 이벤트</div>
                </div>
                <div class="view-title">
                  <div class="s1">${dto.subject}</div>
                  <span class="s2">${dto.created_date }</span>
                </div>
                <div class="view-con">
                  ${dto.content}
                </div>
                <div class="view-page">
                  <ul class="vp">
                    <li class="pp">이전글</li>
                    <c:if test="${not empty preReadDto}">
                    <li class="pt"><a href="<%=cp%>/news/event/view.do?${query}&num=${preReadDto.num}">${preReadDto.subject}</a></li>
					</c:if>
                    <c:if test="${empty preReadDto}">
						<li class="pt"> 이전 글이 없습니다.</li>
                    </c:if>
                  </ul>
                  <ul class="vp">
                    <li class="pp">다음글</li>
                    <c:if test="${not empty nextReadDto}">
                    <li class="pt"><a href="<%=cp%>/news/event/view.do?${query}&num=${nextReadDto.num}">${nextReadDto.subject}</a></li>
					</c:if>
                    <c:if test="${empty nextReadDto}">
                    <li class="pt"> 다음글이 없습니다.</li>
                    <li><a href="<%=cp%>/news/event/list.do">목록으로</a></li>
                    </c:if>
                  </ul>
                  <table>
                  	<tr align="center">
                  	<c:if test="${sessionScope.member.admin==true}">
                  		<td><button type="button" onclick="javascript:location.href='<%=cp%>/news/event/update.do?num=${dto.num}&${query}';">수정</button></td>
                  		<td><button type="button" onclick="javascript:location.href='<%=cp%>/news/event/delete.do?num=${dto.num}&${query}';">삭제</button></td>
              		</c:if>
                  		<td><button type="button" onclick="javascript:location.href='<%=cp%>/news/event/list.do';">목록</button></td>
                  	</tr>
                  </table>
                 
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