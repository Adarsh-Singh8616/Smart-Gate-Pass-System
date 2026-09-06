package com.niet.gatepass.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Ensures a logged-in session exists before allowing access to any
 * protected area (/student/*, /warden/*, /chiefwarden/*, /guard/*).
 * NOTE: if your container is Servlet 4 / javax.servlet, change the
 * "jakarta.servlet" imports above to "javax.servlet".
 */
@WebFilter(urlPatterns = {"/student/*", "/warden/*", "/chiefwarden/*", "/guard/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("userRole") != null);

        if (!loggedIn) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=session_expired");
            return;
        }
        chain.doFilter(req, res);
    }
}
