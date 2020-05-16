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
    <link rel="stylesheet" href="<%=cp%>/resource/css/store.css" />
  </head>
  <body>
    <div id="wrap">
       <header id="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
      </header>
      <main id="content">
        <div id="main">
          <article id="main_container">
            <div class="banner_visual">
              <h2><span>매장찾기</span></h2>
              <div class="visual_text">
                <span>대한민국 대표 브랜드 쿠앤크커피 매장을 찾아보세요.</span>
              </div>
            </div>
            <div class="row">
              <div class="contents_header">
                <h3>매장 목록</h3>
                <form id="search">
                  <input type="text" name="search_text" class="input_text_search" />
                  <button type="button">🔍</button>
                </form>
              </div>
            </div>
            <div class="row">
              <table id="stores">
                <tbody>
                  <tr class="on">
                    <td class="store_name">흥덕지구점</td>
                    <td>031-000-0000</td>
                    <td>경기도 용인시 기흥구 흥덕9로 99번길 9, 999호</td>
                  </tr>
                  <tr>
                    <td class="store_name">흥덕지구점</td>
                    <td>031-000-0000</td>
                    <td>경기도 용인시 기흥구 흥덕9로 99번길 9, 999호</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </article>
        </div>
      </main>
      <footer id="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
      </footer>
    </div>
  </body>
</html>
