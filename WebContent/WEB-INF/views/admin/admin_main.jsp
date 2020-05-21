<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>점주의 방</title>
    <link rel="stylesheet" href="<%=cp%>/resource/css/reset.css" />
    <link rel="stylesheet" href="<%=cp%>/resource/css/admin_layout.css" />
    <link rel="stylesheet" href="<%=cp%>/resource/css/admin_main.css" />
  </head>
  <body>
    <div id="wrap">
      <header>
            <jsp:include page="/WEB-INF/views/layout/admin_header.jsp"/>
      </header>
      <main>
        <article id="main_container">
          <div class="content_box">
            <div class="content_title">
              <h3>오늘의 판매현황</h3>
            </div>
            <div class="content_area">
              <ul id="today_status">
                <li>
                  <div class="order_item deep">
                    <span>베스트 셀러</span> 
                    <figure>
                    	<img src="<%=cp %>/${todayStatus.thumbnail}" alt="${todayStatus.todayMenuName}" />
                    </figure>
                    <a href="#">${todayStatus.todayMenuName}</a>
                  </div>
                </li>
                <li>
                  <div class="order_item">
                    <span>매출 총액</span>
					<figure>
                    	<img src="<%=cp %>/resource/images/admin/item_money.png" alt="매출 총액" />
                    </figure>
                    <a href="#"><fmt:formatNumber value="${todayStatus.todayTotalSales}"/>원</a>
                  </div>
                </li>
              </ul>
            </div>
          </div>
          
          <div class="content_box">
            <div class="content_title">
              <h3>오늘의 대시보드</h3>
            </div>
            <div class="content_area">
              <ul id="order_process">
                <li>
                  <div class="order_item">
                    <span>결제 완료</span>
                    <a href="#">${dashBoardStatusDTO.paymentCount}</a>
                  </div>
                </li>
                <li>
                  <div class="order_item">
                    <span>제조 대기</span>
                    <a href="#">${dashBoardStatusDTO.beforeMakingCount}</a>
                  </div>
                </li>
                <li>
                  <div class="order_item">
                    <span>제조 중</span>
                    <a href="#">${dashBoardStatusDTO.makingCount}</a>
                  </div>
                </li>
                <li>
                  <div class="order_item">
                    <span>제조 완료</span>
                    <a href="#">${dashBoardStatusDTO.doneCount}</a>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </article>
      </main>
      <footer>
        <jsp:include page="/WEB-INF/views/layout/admin_footer.jsp"/>
      </footer>
    </div>
  </body>
</html>
