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
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="<%=cp%>/resource/css/reset.css" />
    <link rel="stylesheet" href="<%=cp%>/resource/css/admin_login.css" />
    <title>회원 로그인</title>
  </head>
  <body>
    <div id="login_wrap">
      <div class="background_wrapper"></div>
      <div class="background_overlay"></div>
      <div id="login_header">
        <h1>
          <a href="<%=cp%>"><span>쿠앤크<strong>관리자</strong></span></a>
        </h1>
      </div>
      <div id="login_content">
        <div id="login_form">
          <form action="<%=cp%>/admin/auth/login_ok.do" method="post">
            <fieldset>
              <legend><strong>사장님</strong> 로그인</legend>
              <div class="login_item">
                <input
                  class="text_form"
                  type="text"
                  name="userId"
                  id="user_id"
                  placeholder="관리자 아이디"
                />
              </div>
              <div class="login_item">
                <input
                  class="text_form"
                  type="password"
                  name="userPwd"
                  id="user_password"
                  placeholder="비밀번호"
                />
              </div>
              <div class="login_item login_item_deep">
                <%--input type="checkbox" name="check_account" id="check_account" /><label
                  for="check_account"
                  >로그인 상태 유지</label
                > --%>
                <c:if test="${not empty errorMessage}">
                <div id="errorMessage">
                	<p class="message">${errorMessage.content}</p>
                </div>
                </c:if>
              </div>
              <div class="login_item">
                <!--링크만 걸었음-->
                <button class="login_button" type="submit">로그인</button>
              </div>
            </fieldset>
          </form>
          <!-- <div class="login_item" id="join_box">
            <a href="#" class="join_user">회원가입</a>
            <ul>
              <li><a href="#">아이디 찾기</a></li>
              <li><a href="#">비밀번호 찾기</a></li>
            </ul>
          </div> -->
        </div>
      </div>
      <div id="login_footer">
        <hr id="border" />
        <!-- <div class="fnb">
          <a href="#">이용약관</a>
          <a href="#">개인정보 처리방침</a>
          <a href="#">운영정책</a>
          <a href="#">고객센터</a>
          <a href="#">공지사항</a>
        </div> -->
        <small>Copyright &copy;<a href="#">COOKIE&amp;CREAM COFFEE.</a> All rights reserved.</small>
      </div>
    </div>
  </body>
</html>
