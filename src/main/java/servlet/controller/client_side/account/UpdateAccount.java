package servlet.controller.client_side.account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.UserHandler;

import java.io.IOException;

@WebServlet("/account-setting")
public class UpdateAccount extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserHandler userHandler;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userHandler = new UserHandler(req, resp);
        userHandler.showAccountSetting();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userHandler = new UserHandler(req, resp);
        try {
            userHandler.settingAccountUser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
