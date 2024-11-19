package servlet.filter;

import entity.Log;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.LogService;
import service.impl.LogServiceImpl;
import servlet.handler.session.SessionHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/admin/*")
public class LoggedFilter implements Filter {

    private SessionHandler sessionHandler;
    private LogService logService;
    private Set<String> usersLogged = new HashSet<String>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (sessionHandler == null) {
            sessionHandler = new SessionHandler(req, res);
        }
        logService = new LogServiceImpl();
        String emailCurrentUser = sessionHandler.getLoggedUser().getEmail();
        if (!usersLogged.contains(emailCurrentUser)) {
            Log log = new Log();
            String url = req.getRequestURI();
            log.setUrl(url);
            log.setUsername(emailCurrentUser);
            logService.add(log);
            usersLogged.add(emailCurrentUser);
        }
        chain.doFilter(request, response);
    }
}
