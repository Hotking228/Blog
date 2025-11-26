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
import java.util.Optional;

@WebServlet("/editPost")
public class EditPostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorDto author = (AuthorDto) req.getSession().getAttribute("author");
        int postId = Integer.parseInt(req.getParameter("postId"));
        if(PostsService.getInstance().tryEditPost(author, postId)) {
            Optional<PostDto> post = PostsService.getInstance().findPostById(postId);
            req.setAttribute("post", post.get());
            req.getRequestDispatcher(JspHelper.getPath("editPost"))
                    .forward(req, resp);
        }else{
            var prvPage = req.getHeader("referer");
            resp.sendRedirect(prvPage == null ? "/" : prvPage);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("postId"));
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        PostsService.getInstance().editPost(id, title, content);
    }
}
