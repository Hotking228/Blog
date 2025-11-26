package com.hotking.servlets;

import com.hotking.entity.AuthorDto;
import com.hotking.entity.PostDto;
import com.hotking.service.AuthorService;
import com.hotking.service.PostsService;
import com.hotking.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/author")
public class AuthorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("authorId"));
        List<PostDto> posts = PostsService.getInstance().getPostByAuthorId(id);
        AuthorDto author = AuthorService.getInstance().findAuthorById(id).get();
        req.setAttribute("posts", posts);
        req.setAttribute("author", author);
        req.getRequestDispatcher(JspHelper.getPath("authorPosts"))
                .forward(req, resp);
    }
}
