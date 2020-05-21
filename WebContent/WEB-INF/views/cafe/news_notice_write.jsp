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
     .notice_writeTB {
     	width: 100%; 
     	margin: 20px auto 0px; 
     	border-spacing: 0px; 
     	border-collapse: collapse;
     }
     .notice_writeTB tr {
     	border-bottom: 1px solid #cccccc;"
     }
     
     .notice_writeTitle {
     	height : 50px;
     	border-top: 2px solid #233e83;
     	line-height: 50px;
     }
     
     .n_wt {
     	width : 100px;
     	text-align : center;
     }
     
     
     
     .n_wtTF {
     	padding-left:10px;
     }
     
     .n_wtTF [type=text] {
     	width: 95%;
     }
     
     
     .notice_writeContent {
     	height: 200px ;
     	border-bottom: 2px solid #233e83;
     	
     }
     
     .n_ct {
     	vertical-align: middle;
     	width: 100px;
     	height : 100%; 
     	text-align: center; 
     }
     
     .n_ctTF {
     	width: 80%;
     	padding : 20px 0 20px 10px;
     	height: 100%;
     
     }
     
     .n_ctTF textarea {
     	width : 95%;
     }
     
     .notice_writeBtn {
     	width : 100%; 
     	border-spacing: 0px;"
     }
     
     .n_wBTR {
     	height : 60px;
     }
     
     .n_wBTD {
     	text-align: center;
     	padding: 20px 0;
     }
     
     .n_wBTD button {
		width: 60px;
		height: 30px;
		margin: 0 10px;
	}
     
    </style>
    
    <script type="text/javascript">
    function sendOk() {
        var f = document.noticeForm;

    	var str = f.subject.value;
        if(!str) {
            alert("제목을 입력하세요. ");
            f.subject.focus();
            return;
        }

    	str = f.content.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.content.focus();
            return;
        }

   		f.action="<%=cp%>/news/notice/${mode}_ok.do";

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
					<form name="noticeForm" method="post">
					  <table class = "notice_writeTB">
					  <tr class = "notice_writeTitle"> 
					      <td class = "n_wt">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
					      <td class = "n_wtTF"> 
					        <input type="text" name="subject" maxlength="100" value="${dto.subject}">
					      </td>
					  </tr>
					
					  <tr class = "notice_writeContent"> 
					      <td class = "n_ct">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
					      <td class = "n_ctTF"> 
					        <textarea name="content" rows="15" >${dto.content}</textarea>
					      </td>
					  </tr>
					  </table>
					
					  <table class  = "notice_writeBtn">
					     <tr class = "n_wBTR"> 
					      <td class = "n_wBTD" >
					         <c:if test="${mode=='update'}">
					         	 <input type="hidden" name="num" value="${dto.num}">
					        	 <input type="hidden" name="page" value="${page}">
					        	 <input type="hidden" name="condition" value="${condition}">
			        			 <input type="hidden" name="keyword" value="${keyword}">
					        </c:if>
					        	 <input type="hidden" name="rows" value="${rows}">
					        <button type="button" class="notice_submitBtn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
					        <button type="reset" class="notice_reBtn">다시입력</button>
					        <button type="button" class="notice_cancelBtn" onclick="javascript:location.href='<%=cp%>/news/notice/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
					      </td>
					    </tr>
					  </table>
					</form>
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