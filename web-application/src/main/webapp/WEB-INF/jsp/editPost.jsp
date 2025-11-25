<%--
  Created by IntelliJ IDEA.
  User: minyaev_s
  Date: 25.11.2025
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <form action="/editPost" method="post">
            <label for="titleId"> Title:
                <input type="text" name="title" id="titleId" value="${requestScope.post.title}">
            </label><br>
            <label for="contentId"> Content
                <textarea name="content" id="contentId" cols="30" rows="10">${requestScope.post.content}</textarea>
            </label>

            <button type="submit">Submit</button>
        </form>
    </body>
</html>
