<%@ tag body-content="empty" pageEncoding="UTF-8"%>
<%@ tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="identity" required="true" %>
${fn:substring(identity, 0, 4)}-${fn:substring(identity, 4, 8)}-${fn:substring(identity, 8, 12)}-${fn:substring(identity, 12, 16)}