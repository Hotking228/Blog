package com.hotking.filters;

import com.hotking.entity.Author;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AccessFilter extends HttpFilter {

    private final Set<String> PUBLIC_PATH = Set.of("/posts", "/login", "/registration", "/logout");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var uri = req.getRequestURI();
        if(isPublicPath(uri) || isUserLoggedIn(req)){
            chain.doFilter(req, res);
        } else {
            var prvPage = req.getHeader("referer");
            res.sendRedirect(prvPage == null ? "/login" : prvPage);
        }
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }

    private boolean isUserLoggedIn(HttpServletRequest req) {
        var author = (Author)req.getSession().getAttribute("author");
        return author != null;
    }
}
