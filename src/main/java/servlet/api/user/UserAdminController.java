package servlet.api.user;

import entity.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.UserService;
import service.impl.UserServiceImp;
import util.RestIO;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/api/users/*")
public class UserAdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = extractPathVariable(req);
        if (idParam == null) {
            List<User> users = userService.listAll();
            RestIO.writeObject(resp, users);
        } else {
            try {
                int userId = Integer.parseInt(idParam);
                User user = userService.findById(userId);
                if (user != null) {
                    RestIO.writeObject(resp, user);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    RestIO.writeError(resp, "User not found.");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                RestIO.writeError(resp, "Invalid user ID.");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            User user = RestIO.readObject(req, User.class);
            userService.save(user);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            RestIO.writeObject(resp, user);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            RestIO.writeError(resp, "Invalid input data.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = extractPathVariable(req);
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            RestIO.writeError(resp, "User ID is required.");
            return;
        }

        try {
            int userId = Integer.parseInt(idParam);
            User existingUser = userService.findById(userId);
            if (existingUser == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                RestIO.writeError(resp, "User not found.");
                return;
            }

            User user = RestIO.readObject(req, User.class);
            user.setId(userId); // Ensure the correct ID is set
            userService.update(user);
            RestIO.writeObject(resp, user);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            RestIO.writeError(resp, "Invalid user ID.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            RestIO.writeError(resp, "Invalid input data.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = extractPathVariable(req);
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            RestIO.writeError(resp, "User ID is required.");
            return;
        }

        try {
            int userId = Integer.parseInt(idParam);
            boolean deleted = userService.delete(userId);
            if (deleted) {
                RestIO.writeEmptyObject(resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                RestIO.writeError(resp, "User not found.");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            RestIO.writeError(resp, "Invalid user ID.");
        }
    }

    /**
     * Extracts the path variable (e.g., ID) from the request URI.
     *
     * @param req the HTTP request
     * @return the path variable or null if none is provided
     */
    private String extractPathVariable(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return (pathInfo == null || pathInfo.equals("/")) ? null : pathInfo.substring(1).trim();
    }
}
