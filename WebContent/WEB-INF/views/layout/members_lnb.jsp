<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<ul class="lnb">
  <li ${api=="/index.do" or api=="/list.do"?"class=\"on\"":""}><a href="<%=cp %>/members/list.do">카드 목록</a></li>
  <li ${api=="/register.do"?"class=\"on\"":""}><a href="<%=cp %>/members/register.do">카드 등록</a></li>
  <li ${api=="/charge.do"?"class=\"on\"":""}><a href="<%=cp %>/members/charge.do">카드 충전</a></li>
  <li ${api=="/order.do"?"class=\"on\"":""}><a href="<%=cp %>/members/order.do">쿠앤크 오더</a></li>
</ul>