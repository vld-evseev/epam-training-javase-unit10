<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.profile" var="profile"/>
<fmt:message var="title" bundle="${profile}" key="profile.login"/>
<jsp:useBean id="validation" class="training.tasks.unit10.web.servlet.model.FormValidation" scope="request"/>
<jsp:useBean id="data" type="training.tasks.unit10.model.Credentials" scope="request"/>

<tags:mainPart title="${title}">

    <c:if test="${validation.errors.INVALID_CREDENTIALS}">
        <div class="alert alert-danger">
            <fmt:message bundle="${profile}" key="profile.invalidCredentials"/>
        </div>
    </c:if>

    <div class="" id="loginModal">
        <div class="modal-body">
            <div class="well">
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active in" id="login">
                            <%--<c:url var="loginUrl" value="/login"/>--%>
                        <form class="form-horizontal" action="login" method="POST">
                            <div
                                    <c:choose>
                                        <c:when test="${not empty validation.fields.login}">
                                            class="form-group has-error"
                                        </c:when>
                                        <c:otherwise>
                                            class="form-group"
                                        </c:otherwise>
                                    </c:choose>
                            >
                                <label for="name" class="control-label"><fmt:message bundle="${profile}"
                                                                                     key="profile.username"/></label>
                                <input id="name" class="form-control" type="text" name="login" value="${data.login}"/>
                                <c:if test="${validation.fields.login.emptyField}">
                                    <span class="help-block">
                                        <fmt:message bundle="${profile}" key="profile.login.checkNotEmpty"/>
                                    </span>
                                </c:if>
                            </div>

                            <div
                                    <c:choose>
                                        <c:when test="${not empty validation.fields.password}">
                                            class="form-group has-error"
                                        </c:when>
                                        <c:otherwise>
                                            class="form-group"
                                        </c:otherwise>
                                    </c:choose>
                            >
                                <label for="pwd" class="control-label"><fmt:message bundle="${profile}"
                                                                                    key="profile.password"/></label>
                                <input id="pwd" class="form-control" type="password" name="password"
                                       value="${data.password}"/>
                                <c:if test="${validation.fields.password.emptyField}">
                                <span class="help-block">
                                    <fmt:message bundle="${profile}" key="profile.password.checkNotEmpty"/>
                                </span>
                                </c:if>
                            </div>

                            <div class="container">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <button class="btn btn-success" type="submit">
                                            <fmt:message bundle="${profile}" key="profile.log.in"/>
                                        </button>
                                    </div>
                                    <div class="col-sm-6">
                                        <c:url var="registrationUrl" value="/registration"/>
                                        <a href="${registrationUrl}">
                                            <fmt:message bundle="${profile}" key="profile.registration"/>
                                        </a>
                                    </div>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</tags:mainPart>
