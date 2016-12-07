<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.root" var="root"/>
<fmt:message var="title" bundle="${root}" key="root.title"/>

<tags:mainPart title="${title}">
    <div>
        <c:choose>
            <c:when test="${sessionScope.authorised}">
                <fmt:message bundle="${root}" key="root.welcome"/>
            </c:when>
            <c:otherwise>
                <fmt:message bundle="${root}" key="root.pleaseLogIn"/>
            </c:otherwise>
        </c:choose>


    </div>

</tags:mainPart>