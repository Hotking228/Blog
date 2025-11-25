<%--
  Created by IntelliJ IDEA.
  User: minyaev_s
  Date: 25.11.2025
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <%@include file="header.jsp"%>
        <form action="/login" method="post">
            <label for="usernameOrEmailId">Username or email:
                <input type="text" name="usernameOrEmail" id="usernameOrEmailId">
            </label><br>

            <label for="passwordId">Password:
                <input type="password" name="password" id="passwordId">
            </label><br>

            <button type="submit">Login</button>
        </form>
    </body>
</html>
