package com.hotking.servlets;

import com.hotking.entity.AuthorDto;
import com.hotking.entity.PostDto;
import com.hotking.service.PostsService;
import com.hotking.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@WebServlet("/post")
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("postId"));
        Optional<PostDto> post = PostsService.getInstance().findPostById(id);
        req.setAttribute("post", post.get());
        req.getRequestDispatcher(JspHelper.getPath("post"))
                .forward(req, resp);
    }
}
