<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <style>
            .button-container{
                display: flex;
                gap:10px;
                flex-wrap: wrap;
            }
            .button-container form{
                margin: 0;
            }
        </style>
    </head>

    <body>
        <div class="button-container">
            <form action="/registration" method="get">
                <button type="submit">Registration</button>
            </form>
            <form action="/login" method="get">
                <button type="submit">Login</button>
            </form>
            <form action="/logout" method="get">
                <button type="submit">Logout</button>
            </form>
            <form action="/posts" method="get">
                <button type="submit">Posts</button>
            </form>
            <form action="/createPost" method="get">
                <button type="submit">Create post</button>
            </form>
        </div>
    </body>
</html>
