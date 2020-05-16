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
        <div class="header_inner">
          <div class="header_top">
            <div class="header_row">
              <h1>
                <a href="./index.html"
                  ><img src="<%=cp%>/resource/images/admin/logo_dark.png" alt="COOKIE&amp;CREAM" class="logo" /></a
                >매장 관리
              </h1>
            </div>
          </div>
          <div class="header_middle">
            <ul id="gnb">
              <li>
                <p>주문관리</p>
                <ul class="gnb_sub">
                  <li><a href="#">접수 대기</a></li>
                  <li><a href="#">처리 중</a></li>
                  <li><a href="#">완료</a></li>
                </ul>
              </li>
              <li>
                <p>메뉴관리</p>
                <ul class="gnb_sub">
                  <li><a href="#">커피</a></li>
                  <li><a href="#">에이드</a></li>
                  <li><a href="#">베이커리</a></li>
                </ul>
              </li>
              <li>
                <p>정산관리</p>
                <ul class="gnb_sub">
                  <li><a href="#">메뉴별 매출</a></li>
                  <li><a href="#">날짜별 매출</a></li>
                </ul>
              </li>
            </ul>
          </div>
          <div class="header_bottom">
            <ul id="today_status">
              <li>
                <p class="title">오늘의 매출액</p>
                <p class="desc"><a href="#">999,999원</a></p>
              </li>
              <li>
                <p class="title">오늘의 효자품목</p>
                <p class="desc"><a href="#">아메리카노</a></p>
              </li>
            </ul>
          </div>
        </div>
      </header>
      <main>
        <article id="main_container">
          <div class="content_box">
            <div class="content_title">
              <h3>대시보드</h3>
            </div>
            <div class="content_area">
              <ul id="order_process">
                <li>
                  <div class="order_item">
                    <span>결제 완료</span>
                    <a href="#">0</a>
                  </div>
                </li>
                <li>
                  <div class="order_item">
                    <span>제조 대기</span>
                    <a href="#">0</a>
                  </div>
                </li>
                <li>
                  <div class="order_item">
                    <span>제조 중</span>
                    <a href="#">0</a>
                  </div>
                </li>
                <li>
                  <div class="order_item">
                    <span>제조 완료</span>
                    <a href="#">0</a>
                  </div>
                </li>
              </ul>
            </div>
          </div>

          <div class="content_box">
            <div class="content_title">
              <h3>접수 대기</h3>
            </div>
            <div class="content_area">
              <table id="receive_list">
                <thead>
                  <tr>
                    <td class="col_no">No</td>
                    <td class="col_nickname">별명</td>
                    <td class="col_product">내역</td>
                    <td class="col_date">주문일시</td>
                    <td class="col_status">접수</td>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>1</td>
                    <td>맛나게먹자</td>
                    <td>
                      <ol class="col_product_detail">
                        <li>
                          <p>아메리카노</p>
                          <ul>
                            <li>Regular</li>
                            <li>Hot</li>
                          </ul>
                        </li>
                        <li>
                          <p>카페라떼</p>
                          <ul>
                            <li>Extra</li>
                            <li>ICE</li>
                            <li>휘핑크림 X 9회</li>
                            <li>초콜릿 칩X 3회</li>
                            <li>시럽(캐러멜) X 9회</li>
                            <li>시럽(바닐라) X 2회</li>
                          </ul>
                        </li>
                        <li>
                          <p>화이트 초콜릿 모카</p>
                          <ul>
                            <li>Extra</li>
                            <li>ICE</li>
                          </ul>
                        </li>
                      </ol>
                    </td>
                    <td>2020-04-21 08:20:20</td>
                    <td><a href="#"class="button">승인</a><a href="#"class="button">거절</a></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </article>
      </main>
      <footer>
        <!-- <p class="copyright">
          <span>&copy; 2020 COOKIE&CREAM COFFEE COMPANY. ALL RIGHTS RESERVED.</span>
        </p> -->
      </footer>
    </div>
  </body>
</html>
