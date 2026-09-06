package com.niet.gatepass.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Ensures the logged-in user's role matches the section they are
 * trying to access (a STUDENT session cannot open /warden/*, etc).
 * Runs after AuthenticationFilter.
 */
@WebFilter(urlPatterns = {"/student/*", "/warden/*", "/chiefwarden/*", "/guard/*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String path = request.getRequestURI().substring(request.getContextPath().length());
        String role = (session != null) ? (String) session.getAttribute("userRole") : null;

        boolean authorized =
                (path.startsWith("/student/") && "STUDENT".equals(role)) ||
                (path.startsWith("/warden/") && "WARDEN".equals(role)) ||
                (path.startsWith("/chiefwarden/") && "CHIEF_WARDEN".equals(role)) ||
                (path.startsWith("/guard/") && "GUARD".equals(role));

        if (!authorized) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=access_denied");
            return;
        }
        chain.doFilter(req, res);
    }
}
