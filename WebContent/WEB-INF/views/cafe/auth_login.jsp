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
                <h2><span>로그인</span></h2>
                <p class="welcome"><span>Welcome, Cookie&Coffee</span></p>
                <p><span>쿠앤크커피에 오신 것을 환영합니다.</span></p>
              </div>

              <div class="row whitebox">
                <div class="row500">
                  <p class="subtitle"><span>쿠앤크 회원이셨나요?</span></p>
                  <p class="title"><span>쿠앤크 멤버스가 새로워졌습니다.</span></p>
                  <hr />
                  <p class="content_text">
                    회원님께 더 좋은 서비스를 제공해 드리고자 <br />
                    선물하기, 쿠앤크카드, 스마크오더 등의 서비스를 추가하여 더욱 새로워졌습니다.<br />
                    회원님께서 새로운 서비스를 이용하시기 위해서는 본인 인증이 필요합니다. <br />
                    본인 인증은 1회만 진행하며 인증후 더 편리하게 서비스를 이용하실 수 있습니다.
                  </p>
                  <a href="#" class="navy_button">쿠앤크 멤버스 가입정보 찾기</a>
                </div>
              </div>

              <div class="row whitebox">
                <div class="row500">
                  <form>
                    <input class="text_input" type="text" placeholder="아이디 입력" />
                    <input class="text_input" type="password" placeholder="비밀번호 입력" />
                    <div class="checkbox_row">
                      <input type="checkbox" class="checkbox" /> 이메일 저장
                    </div>
                    <button type="submit" class="navy_button">로그인</button>
                    <ul class="login_menu">
                      <li><a href="<%=cp%>/auth/join.do">회원가입</a></li>
                      <li><a href="<%=cp%>/auth/find_email.do">이메일 찾기</a></li>
                      <li><a href="<%=cp%>/auth/find_password.do">비밀번호 찾기</a></li>
                    </ul>
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
