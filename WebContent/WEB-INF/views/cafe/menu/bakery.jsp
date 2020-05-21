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
                  <li><a href="<%=cp%>/menu/coffee.do">커피</a></li>
                  <li><a href="<%=cp%>/menu/ade.do">에이드</a></li>
                  <li><a href="<%=cp%>/menu/bakery.do">베이커리</a></li>
                </ul>
              </div>
            </div>
            <div class="centercontent">
              <div class="sidefont"><a>베이커리</a>&nbsp;<a>></a>&nbsp;<a>메뉴</a></div>
              <br /><br />
              <div>
                <a>베이커리 메뉴</a>
              </div>
              <hr />
              <br /><br />
              <div class="picture">
                <ul>
                <c:forEach var="dto" items="${list}" varStatus="status">
                  <li>
                    <div>
                      <a href="#"><img class="menuimg" alt="" src="<%=cp%>/${dto.thumbnail}" /></a>
                      <p>${dto.menuName}</p>
                      <div class="detail">
                        <ul>
                          <li style="width: 100%; padding-top: 20px; padding-bottom: 20px;">
                            <p style="text-align: center;"><span>${dto.menuName}</span></p>
                          </li>
                          <li style="border-top: 2px solid black; padding-top: 20px;">
                            ${dto.text}
                          </li>
                        </ul>
						<br>                        
                        <ul>
                          <li>
                          	<button type="button" onclick="updateMenu();">수정</button>
                          	<button type="button">삭제</button>
                          </li>
                        </ul>
                      </div>
                    </div>
                  </li>
                  </c:forEach>
                </ul>
              </div>
              <br />
              <hr />
              <div class="menu_controller">
                	<button type="button" onclick="javascript:location.href='<%=cp%>/menu/createdMenu.do';">추가</button>
              </div>
              <br /><br />
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
