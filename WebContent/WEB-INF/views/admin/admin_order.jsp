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
              <h3>결제 완료</h3>
            </div>
            <div class="content_area">
              <table id="receive_list">
                <thead>
                  <tr>
                    <td class="col_no">No</td>
                    <td class="col_nickname">주문자(별명)</td>
                    <td class="col_product">내역</td>
                    <td class="col_date">주문일시</td>
                    <td class="col_status">접수</td>
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
                    <td><a href="#"class="button">승인</a><a href="#"class="button">거절</a></td>
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
