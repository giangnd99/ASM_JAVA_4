package servlet.handler.request;

import constant.MessageType;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.EmailService;
import service.UserService;
import service.impl.EmailServiceImpl;
import service.impl.UserServiceImp;
import servlet.handler.AbstractHandler;
import servlet.handler.session.SessionHandler;
import util.EmailUtil;

import java.io.IOException;

public class UserHandler extends AbstractHandler<User> {
    final UserService userService;
    final AuthenticationHandler authenticationHandler;
    final EmailService emailService;
    final SessionHandler sessionHandler;

    public UserHandler(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
        this.userService = new UserServiceImp();
        this.authenticationHandler = new AuthenticationHandler(req, resp);
        this.emailService = new EmailServiceImpl();
        this.sessionHandler = new SessionHandler(req);
    }

    public void loadListToPage() throws Exception {
        String managePage = "/views/amin/manage_user.jsp";
        message = message == null ? "" : message;
        super.setMessage(messageType, message);
        super.forward(managePage);
    }


    public void addEntityFormPage() throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean existingUser = userService.isExistEmail(email);

        if (existingUser) {
            message = "Đã tồn tại user với email: " + email;
            messageType = MessageType.INFO;
            loadListToPage();
            return;
        }
        User newUser = new User();
        super.readFields(newUser);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        newUser.setPassword(encodedPassword);
        result = userService.save(newUser) != null;
        if (result) {
            messageType = MessageType.SUCCESS;
            message = "Thêm user thành công";
        } else {
            messageType = MessageType.ERROR;
            message = "Không thể thêm ";
        }
        loadListToPage();
    }


    public void editEntityFormPage() throws Exception {
        User user = new User();
        super.readFields(user);
        String encodePassword = userService.findByEmail(user.getEmail()).getPassword();
        Integer id = userService.findByEmail(user.getEmail()).getId();

        user.setId(id);
        user.setPassword(encodePassword);

        result = userService.update(user) != null;
        if (result) {
            messageType = MessageType.SUCCESS;
            message = "Cập nhật thành công";
        } else {
            messageType = MessageType.ERROR;
            message = "Không thể cập nhật";
        }
        loadListToPage();
    }


    public void deleteEntityFromPage() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        result = userService.delete(id);
        if (result) {
            messageType = MessageType.SUCCESS;
            message = "Xóa thành công";
        } else {
            messageType = MessageType.ERROR;
            message = "Không thể xóa";
        }
    }


    public void loadEditFormPage() throws Exception {
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        User user = userService.findById(Integer.parseInt(id));
        super.setObject(user);
    }


    public void loadCreateFormPage() throws Exception {
        String createPage = "/views/amin/create_user.jsp";
        super.forward(createPage);
    }

    public void showRegister() throws ServletException, IOException {
        String registerPage = "/views/user/register.jsp";
        super.forward(registerPage);
    }

    public void registerUser() throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Kiểm tra xem người dùng có tồn tại chưa
        boolean existingUser = userService.isExistEmail(email);

        if (existingUser) {
            message = "Đã tồn tại user với email: " + email;
            messageType = MessageType.INFO;
            super.setMessage(messageType, message);
            showRegister();
            return;
        }

        // Tạo đối tượng người dùng mới và mã hóa mật khẩu
        User newUser = new User();
        super.readFields(newUser);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        newUser.setPassword(encodedPassword);

        // Lưu người dùng vào cơ sở dữ liệu
        result = userService.save(newUser) != null;

        if (result) {
            messageType = MessageType.SUCCESS;
            message = "Thêm user thành công";
            authenticationHandler.createTokenForUser(newUser);
            redirectToHome();
        } else {
            messageType = MessageType.ERROR;
            message = "Không thể thêm người dùng";
            super.setMessage(messageType, message);
            showRegister();
        }
    }

    public void settingAccountUser() throws ServletException, IOException {
        User userUpdated = new User();
        super.readFields(userUpdated);
        String encodePassword = userService.findByEmail(userUpdated.getEmail()).getPassword();
        Integer id = userService.findByEmail(userUpdated.getEmail()).getId();

        userUpdated.setId(id);
        userUpdated.setPassword(encodePassword);
        userUpdated = userService.update(userUpdated);
        result = userUpdated != null;

        if (result) {
                authenticationHandler.setJwtWithConditions(null, userUpdated);
            messageType = MessageType.SUCCESS;
            message = "Cập nhật thành công";
        } else {
            messageType = MessageType.ERROR;
            message = "Không thể cập nhật";
        }
        super.setMessage(messageType, message);
        showAccountSetting();
    }

    public void showAccountSetting() throws ServletException, IOException {
        String accountSettingPage = "/views/user/account_setting.jsp";
        super.forward(accountSettingPage);
    }

    public boolean checkValidChangePasswordForm(String email, String currentPassword, String newPassword, String confirmPassword) {
        User userValid = validUser(email);
        if (userValid == null) {
            return false;
        } else if (!authenticationHandler.checkPasswordUser(currentPassword, userValid)) {
            setMessage(MessageType.ERROR, "Mật khẩu cũ không đúng");
            return false;
        } else if (!confirmPassword.equals(newPassword)) {
            setMessage(MessageType.ERROR, "Mật khẩu xác nhận không khớp");
            return false;
        }
        return true;
    }

    User validUser(String email) {
        User existsUser = userService.findByEmail(email);
        if (existsUser == null) {
            setMessage(MessageType.ERROR, "Không tìm thấy user này");
            return null;
        }
        String currentEmail = CURRENT_USER.getEmail();
        if (!email.equals(currentEmail)) {
            setMessage(MessageType.ERROR, "Email không đúng với người dùng hiện tại");
            return null;
        }
        return existsUser;
    }

    public void sendEmailChangePassword() {
        String currentPassword = request.getParameter("currentPassword");
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmNewPassword");
        if (!checkValidChangePasswordForm(email, currentPassword, newPassword, confirmPassword)) {
            return;
        }
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.setRecipients(new String[]{email});
        emailUtil.setMessageBody("Bạn đã đổi mật khẩu thành công \n Mật khẩu mới của bạn là: " + confirmPassword + " -Vui lòng không để lộ mật khẩu ra ngoài");
        if (emailService.sendEmailChangePassword(emailUtil)) {
            setMessage(MessageType.SUCCESS, "Yêu cầu đổi mật khẩu của bạn thành công \n Vui lòng kiểm tra email!!!");
        } else {
            setMessage(MessageType.ERROR, "Không thể thực hiện yêu cầu");
        }
    }


    public void sendEmailPassword() {
        EmailUtil emailUtil = new EmailUtil();
        User userForgot = getUserForgot();
        String[] email = new String[1];
        email[0] = userForgot.getEmail();
        emailUtil.setRecipients(email);
        emailUtil.setMessageBody("Mật khẩu của bạn là :" + userForgot.getPassword() + " -Vui lòng không chia sẽ cho ai!!!");
        if (emailService.sendEmailResetPassword(emailUtil)) {
            messageType = MessageType.SUCCESS;
            setMessage(messageType, "Yêu cầu đã được gửi, vui lòng check email");
        } else {
            messageType = MessageType.ERROR;
            setMessage(messageType, "Không thể gửi yêu cầu của bạn!!");
        }
    }

    public User getUserForgot() {
        String email = request.getParameter("email");
        return userService.findByEmail(email);
    }
}
