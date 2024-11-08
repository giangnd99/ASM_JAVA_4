package service.impl;

import constant.MessageType;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.AuthencationService;
import service.GenericService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class UserServiceImp extends GenericService<User> {

    private UserDao userDao;
    private AuthencationService authenticationService;

    public UserServiceImp(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.userDao = new UserDaoImpl();
        this.authenticationService = new AuthenticationServiceImpl(request, response);
    }


    public void loadListToPage() throws Exception {
        String managePage = "/views/amin/manage_user.jsp";
        List<User> userPerPage = userDao.paginate(super.getPageNumber(), super.PAGE_SIZE);
        loadListInPageNumber(Collections.singletonList(userPerPage), "users");
        message = message == null ? "" : message;
        super.setMessage(messageType, message);
        super.forward(managePage);
    }


    public void addEntityFormPage() throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean existingUser = userDao.isExists(email);

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
        result = userDao.create(newUser) != null;
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
        String encodePassword = userDao.findByEmail(user.getEmail()).getPassword();
        Integer id = userDao.findByEmail(user.getEmail()).getId();

        user.setId(id);
        user.setPassword(encodePassword);

        result = userDao.update(user) != null;
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
        result = userDao.delete(id);
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
        User user = userDao.get(Long.parseLong(id));
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
        boolean existingUser = userDao.isExists(email);

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
        result = userDao.create(newUser) != null;

        if (result) {
            messageType = MessageType.SUCCESS;
            message = "Thêm user thành công";
            authenticationService.createTokenForUser(newUser);
            redirectToHome();
        } else {
            messageType = MessageType.ERROR;
            message = "Không thể thêm người dùng";
            super.setMessage(messageType, message);
            showRegister();
        }
    }

    public void settingAccountUser() throws ServletException, IOException {
        User user = new User();
        super.readFields(user);
        String encodePassword = userDao.findByEmail(user.getEmail()).getPassword();
        Integer id = userDao.findByEmail(user.getEmail()).getId();

        user.setId(id);
        user.setPassword(encodePassword);
        user = userDao.update(user);
        result = user != null;
        if (result) {
            messageType = MessageType.SUCCESS;
            message = "Cập nhật thành công";
            super.setMessage(messageType, message);
            request.setAttribute("loggedUser", user);
            showAccountSetting();
        } else {
            messageType = MessageType.ERROR;
            message = "Không thể cập nhật";
            super.setMessage(messageType, message);
            showAccountSetting();
        }
    }

    public void showAccountSetting() throws ServletException, IOException {
        String accountSettingPage = "/views/user/account_setting.jsp";
        super.forward(accountSettingPage);
    }
}
