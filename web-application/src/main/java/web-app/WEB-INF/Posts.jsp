<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <c:forEach var="post" items="${requestScope.posts}">
            <a href="/post?postId=${post.id}">
                ${post.title}
            </a>
        </c:forEach>
    </body>
</html>
