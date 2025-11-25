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
import java.time.LocalDateTime;

@WebServlet("/post")
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: с сервиса (подтягиваем нужного пользователя из БД)
        int id = Integer.parseInt(req.getParameter("postId"));
        Author author = Author.builder()
                .username("author")
                .email("example@mail.ru")
                .build();
        Post post = Post.builder()
                .title("title")
                .content("content")
                .author(author)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        req.setAttribute("post", post);
        req.getRequestDispatcher(JspHelper.getPath("post"))
                .forward(req, resp);
    }
}
