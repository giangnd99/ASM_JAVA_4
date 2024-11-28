package servlet.controller.client_side.account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.UserHandler;

import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserHandler userHandler;

    public ForgotPassword() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forgotPage = "/views/common/forgot-password.jsp";
        req.getRequestDispatcher(forgotPage).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userHandler = getUserHandler(req, resp);
        userHandler.sendEmailPassword();
        doGet(req, resp);
    }

    UserHandler getUserHandler(HttpServletRequest req, HttpServletResponse resp) {
        if (userHandler == null) {
            userHandler = new UserHandler(req, resp);
        }
        return userHandler;
    }
}
