package servlet.handler.request;

import constant.MessageType;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.UserService;
import service.impl.UserServiceImp;
import servlet.handler.AbstractHandler;
import util.JwtUtil;

import java.io.IOException;

public class AuthenticationHandler extends AbstractHandler<User> {

    private UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public AuthenticationHandler(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.userService = new UserServiceImp();
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = new JwtUtil();
        this.userService = new UserServiceImp();
    }

    public void doLogin() throws IOException {
        boolean loginSuccess = checkLogin();
        if (loginSuccess) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            showLogin();
        }
    }

    private boolean checkLogin() {
        String key = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("rememberMe");

        User userExists = userService.finByUsernameOrEmail(key);

        if (checkUserLogging(userExists, password)) {
            setJwtWithConditions(remember, userExists);
            return true;
        }
        return false;
    }

    public void logout() throws IOException {
        request.getSession().invalidate();
        // Xóa cookie nếu tồn tại
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Xóa cookie ngay lập tức
        response.addCookie(jwtCookie);
        redirectToHome();
    }

    public void createTokenForUser(User user) {
        String token = jwtUtil.generateToken(user.getEmail(), user.isAdmin());

        request.getSession().setAttribute("token", token);
        request.getSession().setAttribute("loggedUser", user);
    }

    public User loggedUser() {
        String token = (String) request.getSession().getAttribute("token");
        if (token == null || token.isEmpty()) {
            token = super.extractTokenFromCookies();
        }

        if (token == null || token.isEmpty()) {
            return null;
        }

        String email = jwtUtil.extractEmail(token);
        return email != null ? userService.findByEmail(email) : null;
    }



    public void showLogin() {
        String loginPage = "/views/user/login.jsp";
        try {
            super.forward(loginPage);
        } catch (ServletException | IOException e) {
            throw new RuntimeException("Error displaying login page", e);
        }
    }


    private void setJwtWithConditions(String remember, User user) {
        if ("on".equals(remember)) {
            jwtUtil.setExpirationTime(7);
            String token = jwtUtil.generateToken(user.getEmail(), user.isAdmin());
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(7 * 24 * 60 * 60); // Token lưu trong 7 ngày
            response.addCookie(jwtCookie);
        } else {
            String token = jwtUtil.generateToken(user.getEmail(), user.isAdmin());
            request.getSession().setAttribute("token", token);
        }
        request.getSession().setAttribute("loggedUser", user);
    }


    boolean checkUserLogging(User user, String password) {
        if (user == null) {
            message = "Không tồn tại người dùng này";
            setMessage(MessageType.ERROR, message);
            return false;
        } else {
            return checkPasswordUser(password, user);
        }
    }

    boolean checkPasswordUser(String password, User user) {
        boolean result = passwordEncoder.matches(password, user.getPassword());
        if (result) {
            return true;
        }
        message = "Mật khẩu không đúng";
        setMessage(MessageType.ERROR, message);
        return false;
    }
}
