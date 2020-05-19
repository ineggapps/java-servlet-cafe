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
    <link rel="stylesheet" href="<%=cp%>/resource/css/members.css" />
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
            <div class="banner_visual">
              <h2><span>쿠앤크 멤버스</span></h2>
              <div class="visual_text">
                <span
                  >쉽고 빠른 주문, 편리한 결제와 적립은 기본<br />친구에게 선물까지, 쿠앤크멤버스를
                  경험해보세요.</span
                >
              </div>
              <jsp:include page="/WEB-INF/views/layout/members_lnb.jsp"/>
            </div>
            <div class="row">
              <div class="row_title">
                <h3>카드 등록 <span>마음에 드는 카드를 한 장 고르세요</span></h3>
              </div>
              <div class="card_container card_register">
                <ul>
                  <c:forEach var="dto" items="${list}">
                  <li>
                    <figure>
                      <a href="<%=cp%>/members/register.do?register_step=2&amp;modelNum=${dto.modelNum}">
                        <img src="<%=cp%>${dto.thumbnail}" alt="card" />
                      </a>
                    </figure>
                    <div class="next_line">
                      <p class="card_title">
                        <a href="./members_card_register_step2.html"><strong>${dto.modelName}</strong></a>
                      </p>
                    </div>
                  </li>
                  </c:forEach>
                </ul>
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
