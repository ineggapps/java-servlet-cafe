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
              <h3>현재 주문 상태</h3>
            </div>
            <div class="content_area">
              <ul id="order_process">
                <li ${api=='/orderPayment.do'?"class=\"on\"":""}>
                  <div class="order_item" >
                    <span>결제 완료</span>
                    <a href="<%=cp%>/admin/main/orderPayment.do">${dashBoardStatusDTO.paymentCount}</a>
                  </div>
                </li>
                <li ${api=='/orderBeforeMaking.do'?"class=\"on\"":""}>
                  <div class="order_item">
                    <span>제조 대기</span>
                    <a href="<%=cp%>/admin/main/orderBeforeMaking.do">${dashBoardStatusDTO.beforeMakingCount}</a>
                  </div>
                </li>
                <li ${api=='/orderMaking.do'?"class=\"on\"":""}>
                  <div class="order_item" >
                    <span>제조 중</span>
                    <a href="<%=cp%>/admin/main/orderMaking.do">${dashBoardStatusDTO.makingCount}</a>
                  </div>
                </li>
                <li ${api=='/orderDone.do'?"class=\"on\"":""}>
                  <div class="order_item">
                    <span>제조 완료</span>
                    <a href="<%=cp%>/admin/main/orderDone.do">${dashBoardStatusDTO.doneCount}</a>
                  </div>
                </li>
              </ul>
            </div>
          </div>
          
          <div class="content_box">
            <div class="content_title">
              <h3>${statusName}</h3>
            </div>
            <div class="content_area">
              <table id="receive_list">
                <thead>
                  <tr>
                    <td class="col_no">No</td>
                    <td class="col_nickname">주문자(별명)</td>
                    <td class="col_product">내역</td>
                    <td class="col_date">주문일시</td>
                    <td class="col_status">상태 변경</td>
                  </tr>
                </thead>
                <tbody>
				<c:forEach var="history" items="${orderHistory}" varStatus="status">
                  <tr>
                    <td>1</td>
                    <td>${history.nickname}</td>
                    <td>
                      <ol class="col_product_detail">
                          <c:forEach var="item" items="${history.items}">
                        <li>
                          <p>
                          	${item.menuName} X ${item.quantity}개
                          </p>
                        </li>
                          </c:forEach>
                      </ol>
                    </td>
                    <td>${history.orderDate}</td>
                    <td>
                    	<c:if test="${statusNum==1}">
                    	<a href="<%=cp%>/admin/main/orderStepUp.do?orderNum=${history.orderNum}&amp;api=${api}"class="button">접수</a>
                    	<a href="#"class="button">취소</a>
                    	</c:if>
                    	<c:if test="${statusNum>1 and statusNum < 4}">
                    	<a href="<%=cp%>/admin/main/orderStepUp.do?orderNum=${history.orderNum}&amp;api=${api}"class="button">완료</a>
                    	</c:if>
                    	<c:if test="${statusNum==4}">
                    	<span>제조 완료</span>
                    	</c:if>
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
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
