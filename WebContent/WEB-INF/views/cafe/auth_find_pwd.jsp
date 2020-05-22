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
      	<form action="<%=cp%>/auth/find_pwd_ok.do" method="post">
        <div id="main">
          <article id="main_container">
            <!-- Content영역 -->
            <div class="row_full">
              <div class="row login">
                <h2><span>비밀번호 찾기</span></h2>
                <p class="welcome"><span>Cookie & Coffee</span></p>
                <p><span>쿠앤크커피에 오신 것을 환영합니다.</span></p>
              </div>

              <div class="row whitebox">
                <div class="row500">
                <table>
               
                <tr>
                	<td> <input name="userId" class="text_input" type="text" placeholder="아이디 입력" /> </td>
                </tr>
               
                <tr>
                     <td><input name="phone" class="text_input" type="text" placeholder="핸드폰 번호 입력" /></td>
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
      	</form>
      </main>
      <footer id="footer">
      	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
      </footer>
    </div>
  </body>
</html>



