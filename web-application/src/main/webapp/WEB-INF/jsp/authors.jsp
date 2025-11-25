<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <h1>Authors:</h1>
        <c:forEach var="author" items="authors">
            <a href="/author?authorId=${author.id}">
                ${author.username}
            </a><br>
        </c:forEach>
    </body>
</html>
