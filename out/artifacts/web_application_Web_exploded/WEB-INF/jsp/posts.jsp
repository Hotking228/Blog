<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <%@include file="header.jsp"%>
        <h1>Posts</h1>
        <form action="/posts" method="post">
            <label>Type title:
                <input type="text" name="title" id="titleId">
            </label>

            <button type="submit">Search</button>
        </form>

            <c:forEach var="post" items="${requestScope.posts}">
                <a href="/post?postId=${post.getId()}">
                    ${post.getTitle()}
                </a><br>
            </c:forEach>
    </body>
</html>
