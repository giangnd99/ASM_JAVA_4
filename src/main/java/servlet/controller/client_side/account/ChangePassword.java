package servlet.controller.client_side.account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.UserHandler;

import java.io.IOException;

@WebServlet("/change-password")
public class ChangePassword extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserHandler userHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forgotPage = "/views/common/change-password.jsp";
        req.getRequestDispatcher(forgotPage).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userHandler = new UserHandler(req, resp);
        userHandler.sendEmailChangePassword();
        doGet(req, resp);
    }

}