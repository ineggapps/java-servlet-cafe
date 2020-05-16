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
    <link rel="stylesheet" href="<%=cp%>/resource/css/board_notice.css" />
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

            <div class="notice-board">
              <div class="noticelist-img">
                <p class="line1">공지사항</p>
                <span class="line2">ALWAYS BESIDE YOU,</span>
                <span class="line3"> COOKIE&amp;CREAM</span>
              </div>
              <div class="board-list">
                <div class="navigation">
                  <div class="nav-bar">홈 &nbsp;〉 쿠앤크소식 &nbsp;〉 공지사항</div>
                </div>
                <ul class="list-ul">
                  <li>
                    <div class="list-num">9</div>
                    <div class="list-title">
                      <a href="notice_view9.html">쿠앤크 멤버스 이용약관 변경 안내</a>
                      <span>2020-04-21</span>
                    </div>
                  </li>
                  <li>
                    <div class="list-num">8</div>
                    <div class="list-title">
                      <a href="#">COVID-19 확산으로 인한 휴업 안내</a>
                      <span>2020-03-02</span>
                    </div>
                  </li>
                  <li>
                    <div class="list-num">7</div>
                    <div class="list-title">
                      <a href="#">쿠키 앤 크림 홍대점 오픈</a>
                      <span>2020-01-28</span>
                    </div>
                  </li>
                </ul>
                <div class="page">
                  <a class="page-num num1">1</a>
                  <a href="notice_list2.html" class="page-num">2</a>
                  <a href="notice_list3.html" class="page-num">3</a>
                </div>
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