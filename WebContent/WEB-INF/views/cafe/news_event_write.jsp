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
    <link rel="stylesheet" href="<%=cp%>/resource/css/event.css" />
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
    
    <script type="text/javascript">
  		function searchEvent() {
  			var f = document.searchFrom;
  			f.submit();
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
          <form action="${mode=='update'?'update_ok.do':'write_ok.do'}" method="post">
            <!-- Content영역 -->
            <div class="notice-board">
              
              <div class="noticelist-img">
                <p class="line1">이벤트</p>
                <span class="line2">ALWAYS BESIDE YOU,</span>
                <span class="line3"> COOKIE&amp;CREAM</span>
              </div>
              
              <div class="eventBox">
                <div class="nav-box">
                  <div class="nav">홈&nbsp; 〉 쿠앤크소식&nbsp; 〉이벤트</div>
                </div>
            	
            	<div>
            		<h3><span style="font-family: Webdings"></span> 이벤트 ${mode=='update'?"수정":"등록"}</h3>
            	</div>
            	
    		
            <div>
            	<table style="margin: 30px auto; width: 100%; border-spacing: 0px;">
            		<tr height="10px">
            			<td>제&nbsp;목 </td>
            			<td width="20%" style="padding: 0px;">
            			 <input type="text" name="subject" maxlength="50" value="${dto.subject}"> </td>           		
					</tr>
					<tr height="10px">
						<td style="vertical-align: middle;">내&nbsp;용 </td>
						<td width="100%;" style="padding: 10px;">
						<textarea name="content" rows="50" cols="100">${dto.content}</textarea>
						</td>
					</tr>
					<tr>
						<td style="padding: 0px;" colspan="2"> 이벤트 기간&nbsp;&nbsp;
						<input type="text" name="start_date" value="2020-05-22" maxlength="10" value="${dto.start_date}">~
						<input type="text" name="end_date" value="2020-05-23" maxlength="10" value="${dto.end_date}">
						</td>
					</tr>
					<tr height="10px">
						<td style="padding: 0px;" colspan="2">
							<c:if test="${mode=='update'}">
							<input type="hidden" name="num" value="${dto.num}" />
							<input type="hidden" name="page" value="${page}"/>
							</c:if>
							<button type="submit">${mode=='update'?"수정":"등록"}</button>
							<button type="button" onclick="javascript:location.href='<%=cp%>/news/event/list.do';">취소</button>
						</td>	
            	</table>
          
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