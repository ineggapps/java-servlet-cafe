<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags" %>
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
                  <c:forEach var="dto" items="${list}">
                  <tr class="on">
                    <td class="store_name">${dto.storeName}</td>
                    <td>${dto.tel}</td>
                    <td>${dto.storeAddress}</td>
                  </tr>
                  <tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
			<div class="row">
            	<p:pager pages="${pages}" data_count="${dataCount}" total_page="${totalPage}" uri="${uri}" query="${query}" current_page="${currentPage}"/>
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
