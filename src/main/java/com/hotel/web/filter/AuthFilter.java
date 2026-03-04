package com.hotel.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = validateSession(session);
        String servletPath = httpRequest.getServletPath();
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        boolean isApiRequest = requestURI.startsWith(contextPath + "/api/");

        boolean isPublicResource = requestURI.endsWith("/login")
                || requestURI.endsWith("/login.jsp")
                || requestURI.contains("/css/")
                || requestURI.contains("/js/");

        if (!isLoggedIn && !isPublicResource) {
            if (isApiRequest) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json");
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.getWriter().print("{\"error\":\"Authentication required\"}");
                return;
            }
            httpResponse.sendRedirect(contextPath + "/login");
            return;
        }

        if (isLoggedIn) {
            String role = String.valueOf(session.getAttribute("role"));

            if ("/report".equals(servletPath) && !canAccessReports(role)) {
                session.setAttribute("error", "Access denied. Manager or admin role required for reports.");
                httpResponse.sendRedirect(contextPath + "/dashboard");
                return;
            }

            if (isManager(role) && isRestrictedForManager(httpRequest)) {
                if (isApiRequest) {
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpResponse.setContentType("application/json");
                    httpResponse.setCharacterEncoding("UTF-8");
                    httpResponse.getWriter().print("{\"error\":\"Access denied. Manager role is read-only for reservations.\"}");
                } else {
                    session.setAttribute("error", "Access denied. Manager role can view reservations and reports only.");
                    httpResponse.sendRedirect(contextPath + "/dashboard");
                }
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public boolean validateSession(HttpSession session) {
        return session != null && session.getAttribute("user") != null;
    }

    private boolean canAccessReports(String role) {
        return "ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role);
    }

    private boolean isManager(String role) {
        return "MANAGER".equalsIgnoreCase(role);
    }

    private boolean isRestrictedForManager(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String method = request.getMethod();

        if ("/bill".equals(servletPath)) {
            return true;
        }

        if ("/reservation".equals(servletPath)) {
            if ("/add".equals(pathInfo) || "/update".equals(pathInfo) || "/delete".equals(pathInfo)) {
                return true;
            }
            return "POST".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method);
        }

        String requestURI = request.getRequestURI();
        if (requestURI != null && requestURI.contains("/api/")) {
            return "POST".equalsIgnoreCase(method)
                    || "PUT".equalsIgnoreCase(method)
                    || "DELETE".equalsIgnoreCase(method);
        }

        return false;
    }

    @Override
    public void destroy() {
    }
}
