<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <form action="/createPost" method="post">
            <label for="titleId"> Title:
                <input type="text" name="title" id="titleId">
            </label><br>
            <label for="contentId"> Content
                <textarea name="content" id="contentId" cols="30" rows="10"></textarea>
            </label>

            <button type="submit">Submit</button>
        </form>
    </body>
</html>
