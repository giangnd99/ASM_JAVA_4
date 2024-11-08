package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SearchUtil {

    public static <T> List<T> searchByKeyword(List<T> list, String keyword) throws Exception {
        List<T> result = new ArrayList<>();

        // Chuyển keyword thành mảng ký tự
        Character[] keywords = new Character[keyword.length()];
        for (int i = 0; i < keyword.length(); i++) {
            keywords[i] = keyword.charAt(i);
        }

        // Duyệt qua từng phần tử trong danh sách
        for (T item : list) {
            Field[] fields = item.getClass().getDeclaredFields(); // Lấy các thuộc tính của đối tượng
            boolean found = false;

            // Kiểm tra từng thuộc tính
            for (Field field : fields) {
                field.setAccessible(true);

                if (field.getType() == String.class) { // Chỉ tìm trong các thuộc tính kiểu chuỗi
                    String value = (String) field.get(item);

                    if (value != null && containsKeyword(value, keywords)) {
                        found = true;
                        break; // Dừng nếu tìm thấy từ khóa
                    }
                }
            }

            // Nếu tìm thấy từ khóa, thêm đối tượng vào kết quả
            if (found) {
                result.add(item);
            }
        }

        return result;
    }

    // Kiểm tra chuỗi có chứa các ký tự từ keyword hay không
    private static boolean containsKeyword(String value, Character[] keywords) {
        for (Character keyword : keywords) {
            if (!value.toLowerCase().contains(keyword.toString().toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
