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
          <form action="<%=cp%>/news/event/write_ok.do" method="post">
            <!-- Content영역 -->
            <div class="notice-board">
              <div class="noticelist-img">
                <p class="line1">공지사항</p>
                <span class="line2">ALWAYS BESIDE YOU,</span>
                <span class="line3"> COOKIE&amp;CREAM</span>
              </div>
              <div class="board-list">
                <div class="navigation">
                  <div class="nav-bar">홈&nbsp; 〉 쿠앤크소식&nbsp; 〉 공지사항</div>
                </div>
            	
            <div>
            	<input type="text" name="subject" value="제목">
				<textarea name="content">내용</textarea>
				<input type="text" name="start_date" value="2020-05-21">
				<input type="text" name="end_date" value="2020-05-21">
				<button type="submit">등록</button>
            </div>	
            	
            	
            	
            	
              </div>
            </div>
            <!-- Content 영역 끝 -->
          </form>
          </article>
        </div>
      </main>
      <footer id="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
      </footer>
    </div>
  </body>
</html>