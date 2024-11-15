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
            super.message = "Đăng nhập thất bại";
            super.messageType = MessageType.ERROR;
            setMessage(messageType, message);
            showLogin();
        }
    }

    private boolean checkLogin() {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("rememberMe");

        User user = userService.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return false; // Email không tồn tại hoặc mật khẩu sai
        }

        // Thiết lập session và lưu JWT
        request.getSession().setAttribute("useremail", email);
        request.getSession().setAttribute("loggedUser", user);

        // Tạo JWT token và lưu vào cookie nếu cần "Remember Me"
        String token = jwtUtil.generateToken(email);
        request.getSession().setAttribute("token", token);

        if ("on".equals(remember)) {
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(7 * 24 * 60 * 60); // Token lưu trong 7 ngày
            response.addCookie(jwtCookie);
        }

        return true;
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
        // Tạo JWT token cho người dùng
        String token = jwtUtil.generateToken(user.getEmail());

        // Lưu token vào session
        request.getSession().setAttribute("token", token);
        request.getSession().setAttribute("loggedUser", user);

        // Lưu token vào cookie nếu cần
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/"); // Áp dụng cho tất cả các URL
        jwtCookie.setMaxAge(7 * 24 * 60 * 60); // Token lưu trong 7 ngày
        response.addCookie(jwtCookie);
    }

    public User loggedUser() {
        String token = (String) request.getSession().getAttribute("token");
        if (token == null || token.isEmpty()) {
            token = extractTokenFromCookies();
        }

        if (token == null || token.isEmpty()) {
            return null;
        }

        // Trích xuất email từ token và tìm người dùng từ email
        String email = jwtUtil.extractUsername(token);
        return email != null ? userService.findByEmail(email) : null;
    }

    private String extractTokenFromCookies() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void showLogin() {
        String loginPage = "/views/user/login.jsp";
        try {
            super.forward(loginPage);
        } catch (ServletException | IOException e) {
            throw new RuntimeException("Error displaying login page", e);
        }
    }
}
