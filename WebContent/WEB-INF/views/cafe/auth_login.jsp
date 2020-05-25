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
                <p class="welcome"><span>Welcome, Cookie&amp;Coffee</span></p>
                <p><span>쿠앤크커피에 오신 것을 환영합니다.</span></p>
              </div>

			<c:if test="${isWelcome==1}">
              <div class="row whitebox">
                <div class="row500">
                  <p class="title"><span>쿠앤크 가입을 환영합니다.</span></p>
                  <p class="subtitle"><span>로그인해 보세요</span></p>
                  <hr />
                  <p class="content_text">
                    회원님께 더 좋은 서비스를 제공해 드리고자 <br />
                    쿠앤크카드, 스마크오더 등의 서비스를 추가하여 더욱 새로워졌습니다.<br />
                    가입하신 아이디와 비밀번호로 로그인이 가능합니다.
                  </p>
                  <%--a href="#" class="navy_button">쿠앤크 멤버스 가입정보 찾기</a--%>
                </div>
              </div>
			</c:if>

              <div class="row whitebox">
                <div class="row500">
                  <form action="<%=cp%>/auth/login_ok.do" method="post">
                    <input class="text_input" type="text" placeholder="아이디 입력" name="userId"/>
                    <input class="text_input" type="password" placeholder="비밀번호 입력" name="userPwd" />
                    <div class="checkbox_row">
                      <%--input type="checkbox" class="checkbox" /> 이메일 저장
                    --%></div>
                    <button type="submit" class="navy_button">로그인</button>
                    <ul class="login_menu">
                      <li><a href="<%=cp%>/auth/join.do">회원가입</a></li>
                      <li><a href="<%=cp%>/auth/find_id.do">아이디 찾기</a></li>
                      <li><a href="<%=cp%>/auth/find_pwd.do">비밀번호 찾기</a></li>
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



<!-- 

	// 아이디 찾기 폼
	protected void findIdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/cafe/auth_find_id.jsp";
		req.setAttribute("mode", "join");
		forward(req, resp, path);
	}
 -->