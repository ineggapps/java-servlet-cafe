<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
		<div class="header_inner">
          <div class="header_top">
            <div class="header_row">
              <h1>
                <a href="./index.html"
                  ><img src="<%=cp%>/resource/images/admin/logo_dark.png" alt="COOKIE&amp;CREAM" class="logo" /></a
                >매장 관리
              </h1>
            </div>
          </div>
          <div class="header_middle">
            <ul id="gnb">
              <li>
                <p>주문관리</p>
                <ul class="gnb_sub">
                  <li><a href="#">접수 대기</a></li>
                  <li><a href="#">처리 중</a></li>
                  <li><a href="#">완료</a></li>
                </ul>
              </li>
              <li>
                <p>정산관리</p>
                <ul class="gnb_sub">
                  <li><a href="#">메뉴별 매출</a></li>
                  <li><a href="#">날짜별 매출</a></li>
                </ul>
              </li>
            </ul>
          </div>
          <%--div class="header_bottom">
            <ul id="today_status">
              <li>
                <p class="title">오늘의 매출액</p>
                <p class="desc"><a href="#">999,999원</a></p>
              </li>
              <li>
                <p class="title">오늘의 효자품목</p>
                <p class="desc"><a href="#">아메리카노</a></p>
              </li>
            </ul>
          </div--%>
        </div>