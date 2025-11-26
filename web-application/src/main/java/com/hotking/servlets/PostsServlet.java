package com.hotking.servlets;


import com.hotking.entity.PostDto;
import com.hotking.service.PostsService;
import com.hotking.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

@WebServlet("/")
public class PostsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PostDto> list = PostsService.getInstance().getAllPosts();
        req.setAttribute("posts", list);
        req.getRequestDispatcher(JspHelper.getPath("/posts"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        List<PostDto> list = PostsService.getInstance().getPostByTitleLevenshtein(title);
        req.setAttribute("posts", list);
        req.getRequestDispatcher(JspHelper.getPath("/posts"))
                .forward(req, resp);
    }
}
