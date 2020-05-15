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
    <link rel="stylesheet" href="<%=cp %>/resource/css/reset.css" />
    <link rel="stylesheet" href="<%=cp %>/resource/css/layout.css" />
  </head>
  <body>
    <div id="wrap">
      <header id="header">
        <div id="header_container">
          <div class="header_row">
            <ul id="right_side">
              <li><a href="./login.html">로그인</a></li>
              <li><a href="./join.html">회원가입</a></li>
            </ul>
          </div>
          <div id="gnb_area">
            <h1 id="logo"><a href="./index.html">COOKIE&amp;CREAM</a></h1>
            <ul id="gnb">
              <li class="main_menu">
                <a href="./teamenu.html"><span>메뉴</span></a>
                <ul class="sub_gnb">
                  <li class="sub_menu"><a href="./teamenu.html">음료</a></li>
                  <li class="sub_menu"><a href="./teamenu.html">에이드</a></li>
                  <li class="sub_menu"><a href="./teamenu.html">베이커리</a></li>
                </ul>
              </li>
              <li class="main_menu">
                <a href="./members_card_list.html"><span>쿠앤크 멤버스</span></a>
                <ul class="sub_gnb">
                  <li class="sub_menu"><a href="./members_card_list.html">카드 목록</a></li>
                  <li class="sub_menu">
                    <a href="./members_card_register_step1.html">카드 등록</a>
                  </li>
                  <li class="sub_menu"><a href="./members_card_charge.html">카드 충전</a></li>
                  <li class="sub_menu"><a href="#">쿠앤크오더</a></li>
                </ul>
              </li>
              <li class="main_menu">
                <a href="./notice_list1.html"><span>쿠앤크 소식</span></a>
                <ul class="sub_gnb">
                  <li class="sub_menu"><a href="./notice_list1.html">공지사항</a></li>
                  <li class="sub_menu"><a href="./event.html">이벤트</a></li>
                </ul>
              </li>
              <li class="main_menu">
                <a href="./store.html"><span>매장찾기</span></a>
                <ul class="sub_gnb">
                  <li class="sub_menu"><a href="./store.html">매장찾기</a></li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
      </header>
      <main id="content">
        <div id="main">
          <article id="main_container">
            <!-- Content영역 -->
            <p style="text-align: center;">
              이곳에 content가 들어갈 영역입니다. 이 태그를 지우고 웹 문서를 작성하세요.
            </p>
            <!-- Content 영역 끝 -->
          </article>
        </div>
      </main>
      <footer id="footer">
        <div class="bottom_util">
          <ul id="fnb">
            <li><a href="#" class="active">개인정보 처리방침</a></li>
            <li><a href="#">멤버스 이용약관</a></li>
            <li><a href="#">가맹 안내</a></li>
            <li><a href="#">대량쿠폰 구매</a></li>
            <li><a href="#">채용 안내</a></li>
            <li><a href="#">고객의소리</a></li>
            <li><a href="#">Sitemap</a></li>
            <li><a href="./admin_login.html">점주의 방</a></li>
          </ul>
        </div>
        <div class="bottom_logo_container"><span class="bottom_logo">COOKIE&amp;CREAM</span></div>
        <p class="copyright">&copy; 2020 COOKIE&amp;CREAM COFFEE COMPANY. ALL RIGHTS RESERVED.</p>
      </footer>
    </div>
  </body>
</html>
