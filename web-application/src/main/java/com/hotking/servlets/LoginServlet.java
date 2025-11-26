package com.hotking.servlets;

import com.hotking.entity.AuthorDto;
import com.hotking.service.AuthorService;
import com.hotking.service.PostsService;
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

        String username = req.getParameter("usernameOrEmail");
        String email = req.getParameter("usernameOrEmail");
        String password = req.getParameter("password");
        Optional<AuthorDto>  author = AuthorService.getInstance().findByUsernameOrEmailAndPassword(username, email, password);
        System.out.println(author.isPresent());
        author.ifPresentOrElse(authorDto -> onLoginSuccess(req, authorDto),
                () -> onLoginFail(resp));
    }

    void onLoginSuccess(HttpServletRequest req, AuthorDto author){
        req.getSession().setAttribute("author", author);
    }

    void onLoginFail(HttpServletResponse resp) {
        try {
            resp.sendRedirect("/login?error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
