package servlet.handler;

import constant.MessageType;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import service.impl.UserServiceImp;
import util.JwtUtil;
import util.PaginationHelper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AbstractHandler<T> {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    private int pageNumber;
    protected boolean result;
    protected int PAGE_SIZE = 8;
    protected String message;
    protected MessageType messageType = MessageType.INFO;
    protected User CURRENT_USER;
    protected String CURRENT_HREF;
    protected JwtUtil jwtUtil;
    protected UserService  userService;

    public AbstractHandler(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.CURRENT_USER = getCurrentUser();
        this.CURRENT_HREF = getCurrentVideoHref();
        this.jwtUtil = new JwtUtil();
        this.userService = new UserServiceImp();
    }

    /**
     * Đọc và gán giá trị các trường từ request vào đối tượng
     *
     * @param entity Đối tượng cần gán giá trị
     * @throws ServletException, IOException
     */
    public void readFields(T entity) throws ServletException, IOException {
        Field[] fields = entity.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String parameterValue = request.getParameter(field.getName());
                if (parameterValue != null && !parameterValue.isEmpty()) {
                    Object value = convertToFieldType(parameterValue, field.getType());
                    field.set(entity, value);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ServletException("Lỗi khi đọc giá trị từ request", e);
        }
    }

    /**
     * Chuyển đổi giá trị từ String sang kiểu dữ liệu của trường
     *
     * @param value     Giá trị cần chuyển đổi
     * @param fieldType Kiểu dữ liệu của trường
     * @return Giá trị đã chuyển đổi
     */
    private Object convertToFieldType(String value, Class<?> fieldType) {
        if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return Long.parseLong(value);
        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
            return Float.parseFloat(value);
        } else if (fieldType.equals(java.util.Date.class)) {
            return java.sql.Timestamp.valueOf(value);
        } else {
            return value;
        }
    }

    /**
     * Chuyển đổi giá trị từ Object sang kiểu dữ liệu của trường
     * Ánh xạ value với name value
     *
     * @param value Giá trị cần chuyển đổi
     */
    public void setObject(Object value) {
        String nameValue = value.getClass().getSimpleName().toLowerCase();
        request.setAttribute(nameValue, value);
    }

    /**
     * Chuyển đổi giá trị từ Object sang kiểu dữ liệu của trường
     * Ánh xạ value với name value
     *
     * @param messageType Kiểu thông báo cần chuyển đổi
     *                    SUCCESS("successMessage"),
     *                    ERROR("errorMessage"),
     *                    WARNING("warningMessage"),
     *                    INFO("infoMessage")
     * @param message     Nội dung
     */
    public void setMessage(MessageType messageType, String message) {
        request.setAttribute(messageType.getValue(), message);
    }

    public int getPageNumber() {
        String pageStr = request.getParameter("pageNumber");
        if (pageStr != null) {
            try {
                pageNumber = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                pageNumber = 1;
            }
        } else {
            pageNumber = 1;
        }
        return pageNumber;
    }


    /**
     * Tính toán các thông tin phân trang và gán vào request.
     *
     * @param paginationHelper Truyền đối tượng để phân trang.
     */
    public void setupPagination(PaginationHelper<T> paginationHelper) {

        int totalPages = paginationHelper.getPageTotal();

        String prevPageHref = (pageNumber > 1) ? "?pageNumber=" + (pageNumber - 1) : "javascript:void(0);";
        String nextPageHref = (pageNumber < totalPages) ? "?pageNumber=" + (pageNumber + 1) : "javascript:void(0);";

        String prevPageClass = (pageNumber == 1) ? "disabled" : "";
        String nextPageClass = (pageNumber == totalPages) ? "disabled" : "";

        List<Integer> pageLinks = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageLinks.add(i);
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("prevPageHref", prevPageHref);
        request.setAttribute("nextPageHref", nextPageHref);
        request.setAttribute("prevPageClass", prevPageClass);
        request.setAttribute("nextPageClass", nextPageClass);
        request.setAttribute("pageLinks", pageLinks);
    }

    protected User getCurrentUser() {
        CURRENT_USER = (User) request.getSession().getAttribute("loggedUser");

        return CURRENT_USER == null ? null : CURRENT_USER;
    }

    protected String extractTokenFromCookies() {
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
    protected String getCurrentVideoHref() {
        return request.getParameter("id");
    }

    public void forward(String uri) throws ServletException, IOException {
        request.getRequestDispatcher(uri).forward(request, response);
    }

    public void redirectToHome() throws IOException {
        response.sendRedirect("home");
    }
    public boolean isLoggedIn() {
        return CURRENT_USER != null;
    }

}

