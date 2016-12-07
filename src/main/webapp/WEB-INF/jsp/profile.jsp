<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.root" var="root"/>
<fmt:setBundle basename="i18n.profile" var="profile"/>
<fmt:message var="title" bundle="${profile}" key="profile.title"/>
<jsp:useBean id="user" type="training.tasks.unit10.model.User" scope="session"/>
<%--<jsp:useBean id="authorised" class="java.lang.Boolean" scope="session"/>
<jsp:setProperty name="authorised" property="*"/>--%>


<tags:mainPart title="${title}">
    <h2>${title}</h2>

    <div>
        <fmt:message bundle="${profile}" key="profile.login"/> : <c:out value="${user.userName}"/><br/>
        <fmt:message bundle="${profile}" key="profile.firstname"/> : <c:out value="${user.firstName}"/><br/>
        <fmt:message bundle="${profile}" key="profile.lastname"/> : <c:out value="${user.lastName}"/><br/>
        <fmt:message bundle="${profile}" key="profile.email"/> : <c:out value="${user.email}"/>
    </div>

</tags:mainPart>
