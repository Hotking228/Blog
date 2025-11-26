<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <%@include file="header.jsp"%>
        <form action="/registration" method="post">
            <label for="usernameId">Username:
                <input type="text" name="username" id="usernameId">
            </label><br>

            <label for="emailId">Email:
                <input type="text" name="email" id="emailId">
            </label><br>

            <label for="passwordId">Password:
                <input type="password" name="password" id="passwordId">
            </label><br>

            <button type="submit">Submit</button>
        </form>
    </body>
</html>
