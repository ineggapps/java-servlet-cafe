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
    <link rel="stylesheet" href="<%=cp %>/resource/css/index.css" />
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
			<div class="row_full intro_pic_bg overlay no-margin">
				<div class="overlay"></div>
				<div class="row">
					<div class="pic_your_coffee text_hidden">PICK YOUR COFFEE!</div>
					<div class="pic_your_coffee_desc text_hidden">쿠앤크커피입니다.</div>
					<div class="button_link">
						<a href="<%=cp%>/menu/index.do">자세히 보기</a>
					</div>
				</div>
			</div>
			<div class="row_full intro_shop_bg overlay no-margin">
				<div class="row">
					<div class="logoWrap">
						<div class="front">
							<div class="description">
								<p class="title">쿠앤크 매장</p>
								<p class="desc">최상의 서비스를 제공하는<br/>쿠앤크 매장을 확인하려면<br/><strong>매장보기</strong>를 눌러주세요.</p>
							</div>
							<div class="button_link black-color">
								<a href="<%=cp%>/store/stores.do">매장 보기</a>
							</div>
						</div>
						<div class="logo back"></div>
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
