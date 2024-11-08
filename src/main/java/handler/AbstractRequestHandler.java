package handler;

import constant.Image;
import constant.MessageType;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import service.GenericService;
import util.XImage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AbstractRequestHandler<T> {

    private HttpServletRequest request;

    public AbstractRequestHandler(HttpServletRequest request) {
        this.request = request;
    }

    public List<T> searchByKeyword(List<T> allEntities, String keyword) throws IllegalAccessException {
        List<T> resultList = new ArrayList<>();
        for (T entity : allEntities) {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value != null && value.toString().toLowerCase().contains(keyword.toLowerCase())) {
                    resultList.add(entity);
                    break; // Nếu tìm thấy kết quả khớp, thêm entity vào danh sách và thoát vòng lặp
                }
            }
        }
        return resultList;
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
                    if (parameterValue.equals(Image.IMAGE_FIELD)) {
                        processImageField(field, entity);
                    }
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

    public void setTotalPage(int totalRecord) {
        Double totalPages = ((Math.ceil((double) totalRecord / PAGE_SIZE)));
        request.setAttribute("totalPages", totalPages);
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

    private void processImageField(Field field, T entity) throws IOException, IllegalAccessException, ServletException {
        Part part = request.getPart(field.getName());
        if (part != null && part.getSize() > 0) {
            String fileName = saveImage(part);
            field.set(entity, fileName);
        }
    }

    private String saveImage(Part part) throws IOException {
        return XImage.uploadAndSaveImage(part, GenericService.SAVE_DIRECTORY);
    }

    /**
     * Tính toán các thông tin phân trang và gán vào request.
     *
     * @param totalRecords Tổng số bản ghi để phân trang.
     */
    public void setupPagination(int totalRecords) {
        // Tính toán tổng số trang
        String baseUri = request.getRequestURI();
        pageNumber = getPageNumber();
        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);

        // Đảm bảo số trang hiện tại nằm trong khoảng hợp lệ
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageNumber > totalPages) {
            pageNumber = totalPages;
        }

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
        return (User) request.getSession().getAttribute("loggedUser");
    }

    protected String getCurrentVideoHref() {
        return request.getParameter("id");
    }
}

