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

        boolean isProtectedPage = getUrlProtected().stream().anyMatch(getCurrentRequestPath(httpRequest)::startsWith);

        if (isProtectedPage & !isLoggedIn(httpRequest)) {
            authenticationHandler.setMessage(MessageType.INFO, "Bạn phải đăng nhập để tiếp tục");
            httpRequest.getRequestDispatcher("/login").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    String getCurrentRequestPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    List<String> getUrlProtected() {
        return Arrays.asList("/admin", "/account-setting", "/change-password", "/reset-password", "/video-detail/like", "/video-detail/share");
    }

    boolean isLoggedIn(HttpServletRequest request) {
        User user = authenticationHandler.getLoggedUser();
        if (user != null) {
            request.getSession().setAttribute("loggedUser", user);
            return true;
        }
        return false;
    }
}
