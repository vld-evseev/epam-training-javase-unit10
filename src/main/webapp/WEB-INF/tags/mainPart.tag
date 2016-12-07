<%@ attribute name="title" rtexprvalue="true" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.profile" var="profile"/>
<fmt:setBundle basename="i18n.root" var="root"/>
<fmt:setBundle basename="i18n.locale" var="loc"/>

<html>
<head>
    <c:url var="bootstrapMain" value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${bootstrapMain}"/>
    <c:url var="customCss" value="/static/css/custom.css"/>
    <link rel="stylesheet" type="text/css" href="${customCss}"/>
    <c:url var="flagsCss" value="/webjars/flag-icon-css/2.4.0/css/flag-icon.min.css"/>
    <link rel="stylesheet" type="text/css" href="${flagsCss}"/>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/custom.css"/>--%>
    <c:url var="jqueryJs" value="/webjars/jquery/1.11.3/jquery.min.js"/>
    <script src="${jqueryJs}"></script>
    <c:url var="bootstrapJs" value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>
    <script src="${bootstrapJs}"></script>
    <title>${title}</title>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <c:url var="rootUrl" value="/"/>
            <a class="navbar-brand" href="${rootUrl}"><fmt:message bundle="${root}" key="root.title"/></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <c:url var="loginUrl" value="/login"/>
                    <a href="${loginUrl}">
                        <fmt:message bundle="${profile}" key="profile.log.in"/>
                    </a>
                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">
                        <i class="glyphicon glyphicon-globe"></i>
                        <fmt:message bundle="${loc}" key="locale.lang"/>
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:url var="localeUrl" value="/locale"/>
                        <c:set var="currentUrl"
                               value="${requestScope['javax.servlet.forward.request_uri']}?${pageContext.request.queryString}"/>
                        <li>
                            <form class="navbar-form" action="${localeUrl}" method="post">
                                <input type="hidden" name="redirect_to" value="${currentUrl}"/>
                                <input type="hidden" name="locale" value="en">
                                <fmt:message var="enTitle" bundle="${loc}" key="locale.en"/>
                                <button class="btn btn-sm btn-link"
                                        <%--role="link" --%>type="submit" title="${enTitle}">
                                    <i class="flag-icon flag-icon-us"></i> ${enTitle}
                                </button>
                            </form>
                        </li>

                        <li>
                            <form class="navbar-form" action="${localeUrl}" method="post">
                                <input type="hidden" name="locale" value="ru">
                                <input type="hidden" name="redirect_to" value="${currentUrl}">
                                <fmt:message var="ruTitle" bundle="${loc}" key="locale.ru"/>
                                <button class="btn btn-sm btn-link"
                                        <%--role="link" --%>type="submit" title="${ruTitle}">
                                    <i class="flag-icon flag-icon-ru"></i> ${ruTitle}
                                </button>
                            </form>
                        </li>
                    </ul>

                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div class="container">
    <jsp:doBody/>
</div>


</body>
</html>
