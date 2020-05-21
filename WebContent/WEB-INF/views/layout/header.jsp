<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
		<div id="header_container">
          <div class="header_row">
            <ul id="right_side">
            <c:if test="${empty sessionScope.member}">
              <li><a href="<%=cp%>/auth/login.do">로그인</a></li>
              <li><a href="<%=cp%>/auth/join.do">회원가입</a></li>
            </c:if>
            <c:if test="${not empty sessionScope.member and sessionScope.member.admin == false}">
            	<li>${sessionScope.member.nickname}님</li>
            </c:if>
            <c:if test="${not empty sessionScope.member and sessionScope.member.admin}">
            	<li>[관리자]</li>
            </c:if>
            <c:if test="${not empty sessionScope.member}">
              <li><a href="<%=cp%>/auth/mypage.do">마이 페이지</a></li>
              <li><a href="<%=cp %>/members/orderedList.do">주문내역 조회</a></li>
              <li><a href="<%=cp%>/auth/logout.do">로그아웃</a></li>
            </c:if>
            </ul>
          </div>
          <div id="gnb_area">
            <h1 id="logo"><a href="<%=cp%>/main">COOKIE&amp;CREAM</a></h1>
            <ul id="gnb">
              <li class="main_menu">
                <a href="<%=cp%>/menu/index.do"><span>메뉴</span></a>
                <ul class="sub_gnb">
                  <li class="sub_menu"><a href="<%=cp%>/menu/coffee.do">커피</a></li>
                  <li class="sub_menu"><a href="<%=cp%>/menu/ade.do">에이드</a></li>
                  <li class="sub_menu"><a href="<%=cp%>/menu/bakery.do">베이커리</a></li>
                </ul>
              </li>
              <li class="main_menu">
                <a href="<%=cp%>/members/index.do"><span>쿠앤크 멤버스</span></a>
                <ul class="sub_gnb">
                  <li class="sub_menu"><a href="<%=cp%>/members/list.do">카드 목록</a></li>
                  <li class="sub_menu">
                    <a href="<%=cp%>/members/register.do">카드 등록</a>
                  </li>
                  <li class="sub_menu"><a href="<%=cp%>/members/charge.do">카드 충전</a></li>
                  <li class="sub_menu"><a href="<%=cp%>/members/order.do">쿠앤크 오더</a></li>
                </ul>
              </li>
              <li class="main_menu">
                <a href="<%=cp%>/news/notice/list.do"><span>쿠앤크 소식</span></a>
                <ul class="sub_gnb">
                  <li class="sub_menu"><a href="<%=cp%>/news/notice/list.do">공지사항</a></li>
                  <li class="sub_menu"><a href="<%=cp%>/news/event/list.do">이벤트</a></li>
                </ul>
              </li>
              <li class="main_menu">
                <a href="<%=cp%>/store/stores.do"><span>매장찾기</span></a>
                <ul class="sub_gnb">
                  <li class="sub_menu"><a href="<%=cp%>/store/stores.do">매장찾기</a></li>
                </ul>
              </li>
            </ul>
          </div>
        </div>