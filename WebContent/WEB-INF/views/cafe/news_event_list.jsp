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
    <link rel="stylesheet" href="<%=cp%>/resource/css/event.css" />

<script type="text/javascript">
function eventList() {

} 


</script>

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
              <h2><span>이벤트</span></h2>
              <div class="visual_text">
                <span
                  >국내 커피 문화를 선도하는<br />쿠앤크 커피에서 준비한 문화 이벤트에
                  참여하세요.</span
                >
              </div>
              <ul class="lnb">
                <li><a href="./notice_list1.html">공지사항</a></li>
                <li class="on"><a href="./event.html">이벤트</a></li>
              </ul>
            </div>
            <div class="row">
  <%--      🎈 이벤트 게시판 코드 작성 공간                --%>
              
            	<div class="eventBox">
					<div class="nav-box">
						<div class="nav">홈 &nbsp;〉 쿠앤크소식 &nbsp;〉이벤트</div>
					</div>
				
				    <div>
            		<form>
            			<table style="width: 100%; margin: 20px auto; border-spacing: 0px;">
              				<tr>
              					<td width="20%">번호</td>
              					<td width="40%"><a href="/">제목</a></td>
              					<td width="20%">관리자</td>
              					<td width="20%">조회수</td>
              				</tr>
              				<c:forEach items="${list}" var="dto">
              				<tr>
              					<td width="20%">${dto.num}</td>
              					<td width="40%"><a href="/">${dto.subject}</a></td>
              					<td width="20%">${dto.userName}</td>
              					<td width="20%">${dto.views}</td>
              				</tr>
              				</c:forEach>
            			</table>
						
						<div>
							<a href="<%=cp%>/news/event/write.do">등록하기</button>
						</div>
            		</form>
				    </div>   
                 </div>
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