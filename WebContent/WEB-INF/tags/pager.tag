<%@ tag body-content="empty" pageEncoding="UTF-8"%>
<%@ tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="uri" required="true"%>
<%@ attribute name="query"%>
<%@ attribute name="pages" required="true" type="Integer[]"%>
<%@ attribute name="data_count" required="true" type="Integer"%>
<%@ attribute name="current_page" required="true" type="Integer"%>
<%@ attribute name="total_page" required="true" type="Integer"%>

<%--paging --%>
<c:if test="${dataCount>0}">
	<ul class="paging">
		<c:if test="${pages[0]>1}">
			<li><a href="${uri}${query}page=1">처음</a></li>
		</c:if>
		<c:if test="${pages[0]>2 and pages[0]-1 != 1}">
			<li><a href="${uri}${query}page=<%=pages[0] - 1%>">이전</a></li>
		</c:if>
		<c:forEach items="${pages}" var="page">
			<c:if test="${current_page==page}">
				<li class="on"><span>${page}</span></li>
			</c:if> 
			<c:if test="${current_page!=page}">
				<li><a href="${uri}${query}page=${page}">${page}</a></li>
			</c:if>
		</c:forEach>
		<c:if test="${pages[fn:length(pages)- 1] < total_page and pages[fn:length(pages)- 1]+1 != total_page}">
			<li><a href="${uri}${query}page=${pages[fn:length(pages) - 1] + 1}">다음</a></li>
		</c:if>
		<c:if test="${pages[fn:length(pages) - 1] < total_page }">
			<li><a href="${uri}${query}page=${total_page}">끝</a></li>
		</c:if>
	</ul>
</c:if>
<c:if test="${dataCount<=0}">
	<p class="alert">등록된 게시물이 없습니다.</p>
</c:if>