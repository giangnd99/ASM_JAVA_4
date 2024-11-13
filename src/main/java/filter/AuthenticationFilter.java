package filter;

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

        // Kiểm tra người dùng đang đăng nhập thông qua JWT
        User loggedUser = authenticationHandler.loggedUser();

        // Các trang yêu cầu đăng nhập
        List<String> protectedUrls = Arrays.asList("/admin", "/account-setting");
        String requestPath = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Kiểm tra nếu request đang truy cập vào trang yêu cầu đăng nhập
        boolean isProtectedPage = protectedUrls.stream().anyMatch(requestPath::startsWith);

        if (isProtectedPage && !isLoggedIn()) {
            // Nếu là trang bảo vệ và người dùng chưa đăng nhập -> chuyển hướng về login
            String contextPath = httpRequest.getContextPath();
            authenticationHandler.setMessage(MessageType.INFO, "Bạn phải đăng nhập để tiếp tục");
            httpResponse.sendRedirect(contextPath + "/login");
        } else {
            // Nếu người dùng đã đăng nhập hoặc không cần đăng nhập cho trang này -> tiếp tục request
            chain.doFilter(request, response);
        }
    }

    boolean isLoggedIn() {
        return authenticationHandler.loggedUser() != null;
    }
}
