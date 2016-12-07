<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.root" var="root"/>
<fmt:setBundle basename="i18n.profile" var="profile"/>
<fmt:message var="title" bundle="${profile}" key="profile.registration"/>
<jsp:useBean id="credsValidation" class="training.tasks.unit10.web.servlet.model.FormValidation" scope="request"/>
<jsp:useBean id="userValidation" class="training.tasks.unit10.web.servlet.model.FormValidation" scope="request"/>
<jsp:useBean id="creds" type="training.tasks.unit10.model.Credentials" scope="request"/>
<jsp:useBean id="user" type="training.tasks.unit10.model.User" scope="request"/>

<tags:mainPart title="${title}">

    <c:if test="${userValidation.errors.registration_failed}">
        <div class="alert alert-danger">
            <strong>Registration failed!</strong> Please try again.
        </div>
    </c:if>

    <form id="tab" class="form-horizontal" action="registration" method="POST">
        <div
                <c:choose>
                    <c:when test="${not empty credsValidation.fields.login}">
                        class="form-group has-error"
                    </c:when>
                    <c:otherwise>
                        class="form-group"
                    </c:otherwise>
                </c:choose>
        >

            <label for="loginName" class="control-label">
                <fmt:message bundle="${profile}" key="profile.username"/>
            </label>
            <input id="loginName" class="form-control" type="text" name="login" value="${creds.login}"
                   class="form-control"/>
            <c:if test="${credsValidation.fields.login.emptyField}">
                <span class="help-block">
                    <fmt:message bundle="${profile}" key="profile.login.checkNotEmpty"/>
                </span>
            </c:if>

        </div>

        <div
                <c:choose>
                    <c:when test="${not empty userValidation.fields.firstName}">
                        class="form-group has-error"
                    </c:when>
                    <c:otherwise>
                        class="form-group"
                    </c:otherwise>
                </c:choose>
        >
            <label for="regFirstname" class="control-label">
                <fmt:message bundle="${profile}" key="profile.firstname"/>
            </label>
            <input id="regFirstname" type="text" name="firstName" value="${user.firstName}"
                   class="form-control"/>
            <c:if test="${userValidation.fields.firstName.emptyField}">
                <span class="help-block">
                    <fmt:message bundle="${profile}" key="profile.firstName.checkNotEmpty"/>
                </span>
            </c:if>
        </div>

        <div
                <c:choose>
                    <c:when test="${not empty userValidation.fields.lastName}">
                        class="form-group has-error"
                    </c:when>
                    <c:otherwise>
                        class="form-group"
                    </c:otherwise>
                </c:choose>
        >
            <label for="regLastName" class="control-label">
                <fmt:message bundle="${profile}" key="profile.lastname"/>
            </label>
            <input id="regLastName" type="text" name="lastName" value="${user.lastName}"
                   class="form-control"/>
            <c:if test="${userValidation.fields.lastName.emptyField}">
                <span class="help-block">
                    <fmt:message bundle="${profile}" key="profile.lastName.checkNotEmpty"/>
                </span>
            </c:if>
        </div>

        <div
                <c:choose>
                    <c:when test="${not empty userValidation.fields.email}">
                        class="form-group has-error"
                    </c:when>
                    <c:otherwise>
                        class="form-group"
                    </c:otherwise>
                </c:choose>
        >
            <label for="regEmail" class="control-label">
                <fmt:message bundle="${profile}" key="profile.email"/>
            </label>
            <input id="regEmail" type="text" name="email" value="${user.email}" class="form-control"/>
            <c:if test="${userValidation.fields.email.emptyField}">
                <span class="help-block">
                    <fmt:message bundle="${profile}" key="profile.email.checkNotEmpty"/>
                </span>
            </c:if>
        </div>

        <div
                <c:choose>
                    <c:when test="${not empty credsValidation.fields.password}">
                        class="form-group has-error"
                    </c:when>
                    <c:otherwise>
                        class="form-group"
                    </c:otherwise>
                </c:choose>
        >
            <label for="regPwd" class="control-label">
                <fmt:message bundle="${profile}" key="profile.password"/>
            </label>
            <input id="regPwd" type="password" name="password" value="${creds.password}"
                   class="form-control"/>
            <c:if test="${credsValidation.fields.password.emptyField}">
                <span class="help-block">
                    <fmt:message bundle="${profile}" key="profile.password.checkNotEmpty"/>
                </span>
            </c:if>
        </div>

        <div>
            <button class="btn btn-primary" type="submit">
                <fmt:message bundle="${profile}" key="profile.create.account"/>
            </button>
        </div>
    </form>


</tags:mainPart>

