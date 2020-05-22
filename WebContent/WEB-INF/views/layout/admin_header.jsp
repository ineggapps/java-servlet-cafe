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
                <a href="<%=cp %>"
                  ><img src="<%=cp%>/resource/images/admin/logo_dark.png" alt="COOKIE&amp;CREAM" class="logo" /></a
                >매장 관리
              </h1>
            </div>
          </div>
          <div class="header_middle">
            <ul id="gnb">
              <li>
                <p>개요</p>
                <ul class="gnb_sub">
                  <li><a href="<%=cp%>/admin/main/index.do">대시보드</a></li>
                </ul>
              </li>
              <li>
                <p>주문관리</p>
                <ul class="gnb_sub">
                  <li><a href="<%=cp%>/admin/main/orderPayment.do">결제 완료</a></li>
                  <li><a href="<%=cp%>/admin/main/orderBeforeMaking.do">제조 대기</a></li>
                  <li><a href="<%=cp%>/admin/main/orderMaking.do">제조 중</a></li>
                  <li><a href="<%=cp%>/admin/main/orderDone.do">제조 완료</a></li>
                  <li><a href="<%=cp%>/admin/main/orderCancelList.do">결제 취소</a></li>
                </ul>
              </li>
              <%--li>
                <p>정산관리</p>
                <ul class="gnb_sub">
                  <li><a href="<%=cp%>/admin/main/salesByMenu.do">메뉴별 매출</a></li>
                  <li><a href="<%=cp%>/admin/main/salesByDate.do">날짜별 매출</a></li>
                </ul>
              </li--%>
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