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
    private BCryptPasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;


    public AuthenticationHandler(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    BCryptPasswordEncoder getPasswordEncoder() {
        if (passwordEncoder == null) {
            this.passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }

    JwtUtil getJwtUtil() {
        if (jwtUtil == null) {
            this.jwtUtil = new JwtUtil();
        }
        return jwtUtil;
    }

    UserService getUserService() {
        if (userService == null) {
            this.userService = new UserServiceImp();
        }
        return userService;
    }

    public void showLogin() {
        String loginPage = "/views/user/login.jsp";
        try {
            super.forward(loginPage);
        } catch (ServletException | IOException e) {
            throw new RuntimeException("Error displaying login page", e);
        }
    }

    public void doLogout() throws IOException {
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

        User userExists = getUserService().finByUsernameOrEmail(key);

        if (checkUserLogging(userExists, password)) {
            setJwtWithConditions(remember, userExists);
            return true;
        }
        return false;
    }


    public void createTokenForUser(User user) {
        String token = getJwtUtil().generateToken(user.getEmail(), user.isAdmin());

        request.getSession().setAttribute("token", token);
        request.getSession().setAttribute("loggedUser", user);
    }

    public User getLoggedUser() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            String email = getEmailFromCookie();
            if (email != null) {
                currentUser = getUserService().findByEmail(email);
            }
        }
        return currentUser;
    }


    public void setJwtWithConditions(String remember, User user) {
        if ("on".equals(remember)) {
            setJwtCookieOnClickRemember(user);
        } else {
            setJwtSession(user);
        }
        setUserInSession(user);
    }

    public void setJwtCookieOnClickRemember(User user) {
        getJwtUtil().setExpirationTime(7);
        String token = getJwtUtil().generateToken(user.getEmail(), user.isAdmin());
        response.addCookie(getJwtCookieConfigRemember(token));
    }

    public void setJwtSession(User user) {
        String token = getJwtUtil().generateToken(user.getEmail(), user.isAdmin());
        request.getSession().setAttribute("token", token);
    }

    public void setUserInSession(User user) {
        request.getSession().setAttribute("loggedUser", user);
    }

    public Cookie getJwtCookieConfigRemember(String token) {
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60);
        return jwtCookie;
    }

    boolean checkUserLogging(User user, String password) {
        if (user == null) {
            message = "Không tồn tại người dùng này";
            setMessage(MessageType.ERROR, message);
            return false;
        } else if (!user.isActive()) {
            message = "Người dùng này bị cấm hoạt động";
            setMessage(MessageType.ERROR, message);
            return false;
        } else {
            return checkPasswordUser(password, user);
        }
    }

    boolean checkPasswordUser(String password, User user) {
        boolean result = getPasswordEncoder().matches(password, user.getPassword());
        if (result) {
            return true;
        }
        message = "Mật khẩu không đúng";
        setMessage(MessageType.ERROR, message);
        return false;
    }
}
