package servlet.controller.admin_side.history;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.HistoryHandler;

import java.io.IOException;

@WebServlet("/admin/manage-history")
public class ManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HistoryHandler historyHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userManage = "/views/admin/manage-history.jsp";
        req.getRequestDispatcher(userManage).forward(req, resp);
    }
}
