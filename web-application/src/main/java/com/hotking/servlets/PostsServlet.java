package com.hotking.servlets;


import com.hotking.entity.PostToShowAllPosts;
import com.hotking.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class PostsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PostToShowAllPosts> list = List.of(PostToShowAllPosts.builder().id(7).title("Мой первый пост").build(),
                PostToShowAllPosts.builder().id(8).title("Java для начинающих").build(),
                PostToShowAllPosts.builder().id(9).title("Рефлексия в Java").build(),
                PostToShowAllPosts.builder().id(10).title("Maven и управление зависимостями").build(),
                PostToShowAllPosts.builder().id(11).title("JDBC и работа с БД").build(),
                PostToShowAllPosts.builder().id(12).title("Тестирование с JUnit 5").build()
                );
        req.setAttribute("posts", list);
        req.getRequestDispatcher(JspHelper.getPath("/posts"))
                .forward(req, resp);
    }
}
