package servlet.controller.client_side.account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.AuthenticationHandler;

import java.io.IOException;

@WebServlet("/logout")
public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AuthenticationHandler authenticationHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        authenticationHandler = new AuthenticationHandler(req,resp);
        authenticationHandler.doLogout();
    }
}
