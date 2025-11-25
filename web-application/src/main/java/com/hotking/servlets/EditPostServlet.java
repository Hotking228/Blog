package com.hotking.servlets;

import com.hotking.entity.Author;
import com.hotking.entity.Post;
import com.hotking.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/editPost")
public class EditPostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: берем информацию из БД
        Post post = Post.builder()
                        .title("title")
                        .content("content")
                        .build();
        req.setAttribute("post", post);
        req.getRequestDispatcher(JspHelper.getPath("editPost"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Author author = (Author) req.getSession().getAttribute("author");
        //TODO: на сервис(сохраняем в БД)
    }
}
