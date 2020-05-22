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
    <link rel="stylesheet" href="<%=cp%>/resource/css/authentication.css" />
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
            <div class="row_full">
              <div class="row login">
                <h2><span>회원탈퇴</span></h2>
                <p class="welcome"><span>Cookie & Coffee</span></p>
              </div>

              <div class="row whitebox">
                <div class="row500">
                <table>
               
                <tr>
                	<td>회원탈퇴가 완료되었습니다.</td>
                </tr>
                
                <tr>
                    <td><button type="submit" class="navy_button">확인</button> </td>
                </tr>
                
                    <tr>
                   	<td colspan="2" align="center">
                    <ul class="login_menu">
                    	<li><a href="<%=cp%>/auth/join.do">회원가입</a></li>
                    	<li><a href="<%=cp%>/auth/login.do">로그인</a></li>
                    </ul>
                    </td>
                  </tr>
                </table>
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





