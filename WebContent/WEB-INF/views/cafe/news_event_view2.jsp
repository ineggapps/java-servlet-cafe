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
    <style type="text/css">
      .notice-board {
        min-height: 1000px;
      }

      .view-title {
        height: 65px;
        line-height: 65px;
        border-top: 2px solid #233e83;
        margin-top: 50px;
        text-align: center;
        border-bottom: 1px solid #d9d9d9;
      }

      .s1 {
        width: 80%;
        display: inline-block;
        font-size: 20px;
      }

      .s2 {
        width: 20%;
        text-align: center;
        margin: 0px auto;
      }

      .view-con {
        min-height: 250px;
        padding: 30px;
        border-bottom: 2px solid #233e83;
      }

      .view-page {
        min-height: 80px;
      }

      .vp li {
        display: inline-block;
      }

      .vp {
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid #d9d9d9;
      }
    </style>
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
                <p class="line1">이벤트</p>
                <span class="line2">ALWAYS BESIDE YOU,</span>
                <span class="line3"> COOKIE&amp;CREAM</span>
              </div>
              <div class="board-list">
                <div class="navigation">
                  <div class="nav-bar">홈&nbsp; 〉 쿠앤크소식&nbsp; 〉 이벤트</div>
                </div>
                <div class="view-title">
                  <div class="s1">쿠앤크 멤버스 이용약관 변경 안내</div>
                  <span class="s2">2020-04-21</span>
                </div>
                <div class="view-con">
                  <p>안녕하세요. 쿠키 앤 크림입니다.</p>
                  <p>쿠키 앤 크림을 이용해주시는 고객님들께 감사드리며,</p>
                  <p>
                    쿠앤크 멤버스 약관이 개정되어 변경내용에 대해 안내드리니 이용에 참고하여 주시기
                    바랍니다.
                  </p>
                </div>
                <div class="view-page">
                  <ul class="vp">
                    <li class="pp">이전글</li>
                    <li class="pt"><a href="#">제목2</a></li>
                  </ul>
                  <ul class="vp">
                    <li class="pp">다음글</li>
                    <li class="pt">다음글이 없습니다.</li>
                    <li><a href="notice_list1.html">목록으로</a></li>
                  </ul>
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