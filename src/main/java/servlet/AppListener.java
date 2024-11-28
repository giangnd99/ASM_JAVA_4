package servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.HashSet;
import java.util.Set;


@WebListener
public class AppListener implements ServletContextListener, HttpSessionListener {

    int countVisitors = 0;
    private final Set<String> sessionIds = new HashSet<>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        String sessionId = se.getSession().getId();
        synchronized (this) {
            if (!sessionIds.contains(sessionId)) {
                countVisitors++;
                setVisitors(countVisitors, context);
            }
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        sce.getServletContext().setRequestCharacterEncoding("UTF-8");
        sce.getServletContext().setResponseCharacterEncoding("UTF-8");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }

    void setVisitors(int visitors, ServletContext context) {
        context.setAttribute("visitors", visitors);
    }
}
