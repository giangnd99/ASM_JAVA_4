package servlet.handler.session;


import entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import service.UserService;
import service.impl.UserServiceImp;
import util.JwtUtil;

@Getter
public class SessionHandler {

    private UserService userService;
    private HttpSession session;
    private JwtUtil jwtUtil;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public SessionHandler(HttpServletRequest req, HttpServletResponse resp) {
        this.session = req.getSession();
        this.jwtUtil = new JwtUtil();
        this.userService = new UserServiceImp();
        this.request = req;
        this.response = resp;
    }

    public void setSessionAttribute(Object attribute) {
        String attributeName = "loggedUser";
        session.setAttribute(attributeName, attribute);
    }

    public void setUserLogged() {
        User user = getLoggedUser();
        if (user != null) {
            setSessionAttribute(user);
        }
    }

    public User getLoggedUser() {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            token = extractTokenFromCookie();
        }
        if (token == null) {
            return null;
        }
        String email = jwtUtil.extractEmail(token);
        return userService.findByEmail(email);
    }

    public String checkAdminLogged() {
        User user = getLoggedUser();
        if (user == null) {
            return null;
        }
        return user.isAdmin() ? "admin" : "user";
    }

    public boolean isAdminLogged() {
        String role = checkAdminLogged();
        if (role.equals("admin")) {
            return true;
        }
        return false;
    }

    String extractTokenFromCookie() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
