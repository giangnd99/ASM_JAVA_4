package servlet.filter;

import constant.MessageType;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.AuthenticationHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private AuthenticationHandler authenticationHandler;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        authenticationHandler = new AuthenticationHandler(httpRequest, httpResponse);
        httpRequest.setCharacterEncoding("UTF-8");
        httpResponse.setCharacterEncoding("UTF-8");
        List<String> protectedUrls = Arrays.asList("/admin", "/account-setting");
        String requestPath = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        boolean isProtectedPage = protectedUrls.stream().anyMatch(requestPath::startsWith);

        if (isProtectedPage & !isLoggedIn(httpRequest)) {

            String contextPath = httpRequest.getContextPath();
            authenticationHandler.setMessage(MessageType.INFO, "Bạn phải đăng nhập để tiếp tục");
            httpResponse.sendRedirect(contextPath + "/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    boolean isLoggedIn(HttpServletRequest request) {
        User user = authenticationHandler.loggedUser();
        if (user != null) {
            request.getSession().setAttribute("loggedUser", user);
            return true;
        }
        return false;
    }
}
