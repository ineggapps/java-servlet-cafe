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
            <form>
              <div class="row">
                <div class="row_title">
                  <h3>쿠앤크 오더</h3>
                </div>
                <div class="card_container card_container_full">
                  <ul>
                    <li>
                      <figure>
                        <img src="<%=cp%>/resource/images/members/card/card04.png" alt="card" />
                      </figure>
                      <div>
                        <p class="card_title detail">
                          <strong>카드이름</strong><a href="#" class="modify">수정</a>
                        </p>
                        <p class="card_id">(123456)</p>
                        <p class="card_remain">잔액 <strong>999,999,999</strong>원</p>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="row">
                <table id="card_charge">
                  <thead>
                    <tr>
                      <td>항목</td>
                      <td>입력</td>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td class="col_charge_category">충전 금액 선택</td>
                      <td class="col_charge_data">
                        <p class="desc">
                          충전 후 총 카드잔액: <strong class="desc_blue">999,999</strong>원
                        </p>
                        <ul class="charge_option">
                          <li>
                            <label
                              ><input type="radio" value="100000" name="price" /><span
                                >10만원</span
                              ></label
                            >
                          </li>
                          <li>
                            <label
                              ><input type="radio" value="50000" name="price" /><span
                                >5만원</span
                              ></label
                            >
                          </li>
                          <li>
                            <label
                              ><input type="radio" value="30000" name="price" /><span
                                >3만원</span
                              ></label
                            >
                          </li>
                          <li>
                            <label
                              ><input type="radio" value="10000" name="price" /><span
                                >1만원</span
                              ></label
                            >
                          </li>
                        </ul>
                        <p class="desc_red desc">
                          ※쿠앤크 커피 온라인 충전은 1만 원 단위로 최대 55만원까지 가능하며, 충전 후
                          합계 잔액이 55만 원을 초과할 수 없습니다.
                        </p>
                      </td>
                    </tr>
                    <tr>
                      <td class="col_charge_category">결제 시점</td>
                      <td class="col_charge_data"><strong class="desc_blue">즉시</strong> 결제</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </form>
            <div class="row buttons">
              <a href="./members_card_list.html" class="list_button">목록</a>
              <a href="#" class="list_button submit" onclick="submit()">충전하기</a>
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
