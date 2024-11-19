package servlet.filter;

import constant.MessageType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.session.SessionHandler;

import java.io.IOException;

@WebFilter("/admin")
public class AuthorizationFilter implements Filter {

    private SessionHandler sessionHandler;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        sessionHandler = new SessionHandler(req, res);
        boolean isAdmin = sessionHandler.isAdminLogged();
        if (isAdmin) {
            chain.doFilter(request, response);
        } else {
            req.setAttribute(MessageType.WARNING.getValue(), "Bạn không có quyền truy cập trang admin");
            req.getRequestDispatcher("/home").forward(request, response);
        }
    }


}
