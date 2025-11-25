package com.hotking.servlets;

import com.hotking.entity.Author;
import com.hotking.service.AuthorService;
import com.hotking.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/authors")
public class AllAuthorsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Author> authors = AuthorService.getInstance().findAllAuthor();
        req.setAttribute("authors", authors);
        req.getRequestDispatcher(JspHelper.getPath("authors"))
                .forward(req, resp);
    }
}
