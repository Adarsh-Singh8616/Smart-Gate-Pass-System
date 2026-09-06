package com.niet.gatepass.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.LocalDateTime;

/** Logs every incoming request's method, URI, and timestamp to the server console/log. */
@WebFilter(urlPatterns = {"/*"})
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("[" + LocalDateTime.now() + "] " +
                request.getMethod() + " " + request.getRequestURI());
        chain.doFilter(req, res);
    }
}
