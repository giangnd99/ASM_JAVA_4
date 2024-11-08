package filter;

import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.impl.AuthenticationServiceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements Filter {
    private AuthenticationServiceImpl authenticationService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        this.authenticationService = new AuthenticationServiceImpl(
                (HttpServletRequest) context.getAttribute("httpRequest"),
                (HttpServletResponse) context.getAttribute("httpResponse")
        );
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Kiểm tra người dùng đang đăng nhập thông qua JWT
        User loggedUser = authenticationService.loggedUser();

        // Các trang yêu cầu đăng nhập
        List<String> protectedUrls = Arrays.asList("/admin", "/profile");
        String requestPath = httpRequest.getRequestURI();

        // Kiểm tra nếu request đang truy cập vào trang yêu cầu đăng nhập
        boolean isProtectedPage = protectedUrls.stream().anyMatch(requestPath::startsWith);

        if (isProtectedPage && loggedUser == null) {
            // Nếu là trang bảo vệ và người dùng chưa đăng nhập -> chuyển hướng về login
            String contextPath = httpRequest.getContextPath();
            httpResponse.sendRedirect(contextPath + "/login");
        } else {
            // Nếu người dùng đã đăng nhập hoặc không cần đăng nhập cho trang này -> tiếp tục request
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Dọn dẹp tài nguyên nếu cần
    }
}
