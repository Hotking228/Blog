package com.hotking.servlets;

import com.hotking.entity.Author;
import com.hotking.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: validate user
        Optional<Author>author = Optional.of(Author.builder()
                .email("example@mail.ru")
                .username("username")
                .build());
        //----------------------
        req.getSession().setAttribute("author", author.get());
        resp.sendRedirect("/posts");
    }
}
