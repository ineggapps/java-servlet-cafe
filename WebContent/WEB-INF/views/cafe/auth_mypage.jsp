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
            <div class="banner_visual join">
              <div class="row join">
                <h2><span>회원 정보 수정</span></h2>
              </div>
            </div>
            <div class="row">
              <form action="<%=cp%>/auth/mypage_ok.do" method="post">
                <div class="joinbox">
                  <div class="component_wrap">
                    <div class="agreement_box">
                    </div>
                  </div>

                  <div class="join_form">
                    <div class="component_wrap">
                      <div class="join_item_title">
                        <h3>회원정보입력</h3>
                      </div>
                      <div class="join_item"><strong>아이디</strong> <input type="text" value="${authDTO.userId}" readonly="readonly" name="userId"/></div>
                      <div class="join_item"><strong>이름</strong> <input type="text"  value="${authDTO.userName }" readonly="readonly" name="userName"/></div>
                      <div class="join_item">
                        <strong>별명</strong> <input type="text" value="${authDTO.nickname}" name="nickname" />
                        <p class="desc">※ 욕설 등 부적절한 단어는 제한을 받습니다.</p>
                      </div>
                      <div class="join_item"><strong>휴대폰</strong> <input type="text" value="${authDTO.phone}" name="phone"/></div>
                      <div class="join_item">
                        <strong>비밀번호</strong> <input type="password" name="userPwd"/>
                        <p class="desc">
                          	※ 안전한 비밀번호를 위해 숫자, 문자 조합하여 10~16자 이상으로
                         	 입력해주세요.
                        </p>
                      </div>
                      <div class="join_item">
                        <strong>비밀번호 확인</strong> <input type="password" name="userPwdConfirm"/>
                        <p class="desc">
                        	비밀번호를 한 번 더 입력하세요
                        </p>
                      </div>
                      
                      <div>
                         <button type="submit" name="btn">회원탈퇴</button>
                      </div>
                      
                    </div>
                    <div class="join_item_title submit">
                      <input type="hidden" name="userNum" value="${authDTO.userNum}">
                      <button type="submit" name="btn" class="navy_button">확인</button>
                    </div>
                  </div>
                </div>
              </form>
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
