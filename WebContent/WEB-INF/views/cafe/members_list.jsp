<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="cd" tagdir="/WEB-INF/tags" %>
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
                  경험해보세요.</span>
              </div>
              <jsp:include page="/WEB-INF/views/layout/members_lnb.jsp"/>
            </div>
            <div class="row">
              <div class="row_title">
                <h3>보유 카드 현황 <span>(보유카드: ${dataCount}장)</span></h3>
              </div>
              <div class="card_container">
                <ul>
                  <c:forEach var="dto" items="${list}">
                  <li>
                    <figure class="transform">
                      <a href="<%=cp%>/members/detail.do?cardNum=${dto.cardNum}">
                        <img src="<%=cp%>/${dto.thumbnail}" alt="card" />
                      </a>
                    </figure>
                    <div class="card_info list">
                      <p class="card_title">
                        <a href="<%=cp%>/members/detail.do?cardNum=${dto.cardNum}"><strong>${dto.cardName}</strong></a
                        ><!--a href="#" class="modify">수정</a-->
                      </p>
                      <p class="card_id"><cd:card identity="${dto.cardIdentity }"/> </p>
                      <p class="card_remain">잔액:&nbsp;<strong><fmt:formatNumber value="${dto.balance}"/></strong>원</p>
                    </div>
                  </li>
                </c:forEach>
                </ul>
              </div>
            </div>
            <c:if test="${fn:length(list)==0}">
            <div class="row border_box"> 
				<p class="title">아직 카드를 만들지 않았습니다.</p>
             	<a href="<%=cp%>/members/register.do" class="single_button">카드 구경하러 가기</a>
             </div>
            </c:if>
            <div class="row">
            	<p:pager pages="${pages}" data_count="${dataCount}" total_page="${totalPage}" uri="${uri}" query="${query}" current_page="${currentPage}"/>
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
