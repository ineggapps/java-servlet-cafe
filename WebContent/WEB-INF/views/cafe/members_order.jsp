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
    <link rel="stylesheet" href="<%=cp%>/resource/css/members.css" />  </head>
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
			<div class="row_full alert">
              <div class="row">
              	<div class="box">
              		<figure class="img">
              			<img src="<%=cp %>/resource/images/assets/alert-240.png" alt="alert" />
              		</figure>
              		<div class="msg_content">
						<div class="title">구매 개수를 초과하였습니다.</div>              		
					  	<p class="message">커피, 베이커리, 에이드는 합쳐서 20잔(개)까지만 구매가 가능합니다.<br/>보다 맛있는 음식으로 보답하는 쿠앤크 커피가 되겠습니다.</p>
              		</div>
              	</div>
			  </div>
            </div>
            <form>
              <div class="row">
                <div class="row_title">
                  <h3>쿠앤크 오더</h3>
                </div>
              <div class="row">
                  <ul class="tab">
                    <li ${api=="/order.do"?"class=\"on\"":""}><a href="<%=cp%>/members/order.do">음식 고르기</a></li>
                    <li ${api=="/buy.do"?"class=\"on\"":""}><a href="<%=cp%>/members/buy.do">주문 및 결제</a></li>
                    <li ${api=="/orderedList.do"?"class=\"on\"":""}><a href="<%=cp%>/members/orderedList.do">주문 내역</a></li>
                  </ul>
                </div>
              </div>
              <c:if test="${api=='/order.do'}">
              <div class="row">
                <ul class="shop">
                	<c:forEach var="dto" items="${list}"> 
                		<li>
                			<div class="menu_item">
                				<figure class="menu_figure">
                					<img src="<%=cp %>/${dto.thumbnail}" alt="${dto.menuName}" />
                				</figure>
                		 		<div class="menu_content">
                		 			<p>${dto.menuName}</p>
                		 			<div class="menu_item_controller">
						              <a href="<%=cp%>/members/order.do?menuNum=${dto.menuNum}" class="item_button">담기</a>
						              <a href="#" class="item_button submit" onclick="submit()">주문</a>
						            </div>
                		 		</div>
                			</div>
                		</li>
                	</c:forEach>
                </ul>
              </div>
              </c:if>
              <c:if test="${api=='/order.do'}">
              <div class="row">
                
              </div>
              </c:if>
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
