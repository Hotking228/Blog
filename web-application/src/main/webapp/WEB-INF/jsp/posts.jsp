<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <%@include file="header.jsp"%>
        <h1>Posts</h1>
            <c:forEach var="post" items="${requestScope.posts}">
                <a href="${pageContext.request.contextPath}/post?postId=${post.getId()}">
                    ${post.getTitle()}
                </a><br>
            </c:forEach>
    </body>
</html>
