<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <%@include file="header.jsp"%>
        <h1>${requestScope.post.title}</h1>
        <h2>Author</h2>
        ${requestScope.post.author.username} ${requestScope.post.author.email}<br>
        <h2>Content</h2>
        ${requestScope.post.content}<br>
        Created at : ${requestScope.post.createdAt}<br>
        Updated at : ${requestScope.post.updatedAt}<br>
        <form action="/editPost" method="get">
            <input type="hidden" name="postId" value="${param.postId}">
            <button type="submit">Edit</button>
        </form>
    </body>
</html>
