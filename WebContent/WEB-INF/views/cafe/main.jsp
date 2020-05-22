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
    <link rel="stylesheet" href="<%=cp %>/resource/css/reset.css" />
    <link rel="stylesheet" href="<%=cp %>/resource/css/layout.css" />
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
            <p style="text-align: center;"> 
              프로젝트 시작
            </p>
            <div class="row">
	             <p>
	             ${fn:length(pages)}페이지 / ${dataCount}건 / ${totalPage }까지 / ${uri }
	             <c:forEach var="i" begin="0" end="${fn:length(pages)}">
	             	<a>${pages[i] }</a>
	             </c:forEach>
	             </p> 
					<p:pager pages="${pages}" data_count="${dataCount}" total_page="${totalPage}" uri="${uri}" query="${query}" current_page="${currentPage}"/>
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
