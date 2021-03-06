<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags" %>
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
    <link rel="stylesheet" href="<%=cp%>/resource/css/teamenu.css" />
  </head>
  <body>
    <div id="wrap">
      <header id="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
      </header>
      <main id="content">
        <div id="main">
          <article id="main_container">
            <div class="box backgroundcoffee">
              <div class="menufont">음료</div>
              <br /><br /><br />
              <div class="menu">
                <ul>
                  <li ${api=='/menu/coffee.do'?"class='on'":""}><a href="<%=cp%>/menu/coffee.do">커피</a></li>
                  <li ${api=='/menu/ade.do'?"class='on'":""}><a href="<%=cp%>/menu/ade.do">에이드</a></li>
                  <li ${api=='/menu/bakery.do'?"class='on'":""}><a href="<%=cp%>/menu/bakery.do">베이커리</a></li>
                </ul>
              </div>
            </div>
            <div class="centercontent">
              <div class="sidefont"><a>커피</a>&nbsp;<a>></a>&nbsp;<a>메뉴</a></div>
              <br /><br />
              <div>
                <a>커피 메뉴</a>
              </div>
              <hr />
              <br /><br />
              <div class="picture">
                <ul class="menus">
                <c:forEach var="dto" items="${list}" varStatus="status">
                  <li>
                    <div class="detail1">
                      <a href="#"><img class="menuimg" alt="" src="<%=cp%>${dto.thumbnail}" /></a>
                      <p>${dto.menuName}</p>
                     </div>
                      <div class="detail">
                        <ul>
                          <li style="width: 100%; padding-top: 20px; padding-bottom: 20px;">
                            <p style="text-align: center;"><span>${dto.menuName}</span></p>
                          </li>
                          <li style="border-top: 2px solid black; padding-top: 20px; width: 100%">
                            &nbsp;&nbsp;&nbsp;&nbsp;${dto.text}
                          </li>
                        </ul>
                        <ul>
                        	<li class="detail_price" >가격 : <fmt:formatNumber value="${dto.price}"/>원</li>
                        </ul>
                        <br><br><br>
                        <ul>
                          <li class="detail_button">
                          <c:if test="${sessionScope.member.userId=='hello'}">
                          	<button type="button" onclick="javascript:location.href='<%=cp%>/menu/update.do?menuNum=${dto.menuNum}';">수정</button>
                          </c:if>
                          <c:if test="${sessionScope.member.userId=='hello'}">
                          	<button type="button" onclick="javascript:location.href='<%=cp%>/menu/delete.do?menuNum=${dto.menuNum}';">삭제</button>
                          </c:if>
                          </li>
                        </ul>
                      </div>
                  </li>
                  </c:forEach>
                </ul>
              </div>
              <br />
              <hr />
              <div>
            	<p:pager pages="${pages}" data_count="${dataCount}" total_page="${total_page}" uri="${uri}" query="${query}" current_page="${current_page}"/>
              </div>
              <div class="menu_controller" style="margin-bottom: 10px;">
              	<c:if test="${sessionScope.member.userId=='hello'}">
                	<button type="button" onclick="javascript:location.href='<%=cp%>/menu/createdMenu.do';">추가</button>
              	</c:if>
              </div>
              <br /><br />
              <ul><li>&nbsp;</li></ul>
            </div>
          </article>
        </div>
      </main>
      <footer id="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
      </footer>
    </div>
  </body>
</html>
