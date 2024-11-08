package controller.client_side.account;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.impl.UserServiceImp;

import java.io.IOException;

@WebServlet("/account-setting")
public class UpdateAccount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserServiceImp userService = new UserServiceImp(req, resp);
        userService.showAccountSetting();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserServiceImp userService = new UserServiceImp(req, resp);
        try {
            userService.settingAccountUser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
