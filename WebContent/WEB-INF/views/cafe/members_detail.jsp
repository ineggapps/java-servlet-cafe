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
                <h3><strong class="strong">${sessionScope.member.nickname}</strong>님의 카드 상세정보</h3>
              </div>
              <div class="card_container card_container_full">
                <ul>
                  <li>
                    <figure>
                      <img src="<%=cp %>${cardDTO.thumbnail}" alt="card" />
                    </figure>
                    <div class="detail">
                      <p class="card_title detail">
                        <strong>${cardDTO.cardName}</strong><a href="#" class="modify">수정</a>
                      </p>
                      <p class="card_id">(123456)</p>
                      <p class="card_remain">잔액:&nbsp;<strong><fmt:formatNumber value="${cardDTO.balance}"/></strong>원</p>
                      <ul class="card_control">
                        <li>
                          <a href="<%=cp %>/members/charge.do?cardNum=${cardDTO.cardNum}" class="card_button">충전하기</a>
                        </li>
                        <li><a href="#" class="card_button">잔액이전</a></li>
                      </ul>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
            <div class="row">
              <table id="card_history">
                <thead>
                  <tr>
                    <td class="col_no">No</td>
                    <td class="col_category">구분</td>
                    <td class="col_content">내역</td>
                    <td class="col_store">이용매장</td>
                    <td class="col_amount">금액</td>
                    <td class="col_date">날짜</td>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>1</td>
                    <td>사용</td>
                    <td>상품구매</td>
                    <td>동교</td>
                    <td>-2,600</td>
                    <td>2020-04-21 08:20:20</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="row">
              <a href="<%=cp %>/members/list.do" class="list_button">목록</a>
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
