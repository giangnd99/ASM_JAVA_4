package util;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XImage {

    // Tạo thư mục nếu chưa tồn tại
    public static File createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    // Lưu file đơn vào thư mục đã chỉ định
    public static String saveFile(Part part, String saveDirectory) throws IOException {
        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        if (fileName == null || fileName.isEmpty()) {
            throw new IOException("Tên tệp không hợp lệ");
        }

        createDirectory(saveDirectory);

        // Đảm bảo thư mục tồn tại trước khi lưu
        File destFile = new File(saveDirectory + File.separator + fileName);

        try (InputStream inputStream = part.getInputStream()) {
            Files.copy(inputStream, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Lỗi khi lưu tệp: " + fileName, e);
        }
        return fileName;  // Trả về tên file đã lưu
    }

    // Lưu nhiều file vào thư mục đã chỉ định
    public static List<String> saveFiles(Collection<Part> parts, String saveDirectory) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (Part part : parts) {
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (fileName != null && !fileName.isEmpty()) {
                String savedFileName = saveFile(part, saveDirectory);  // Gọi hàm saveFile để lưu từng file
                fileNames.add(savedFileName);  // Thêm tên file đã lưu vào danh sách
            }
        }
        return fileNames;  // Trả về danh sách tên file đã lưu
    }

    // Lấy đường dẫn đầy đủ của file ảnh
    public static String getFilePath(String saveDirectory, String fileName) {
        return Paths.get(saveDirectory, fileName).toString();
    }

    // Kiểm tra sự tồn tại của file
    public static boolean fileExists(String saveDirectory, String fileName) {
        File file = new File(saveDirectory + File.separator + fileName);
        return file.exists();
    }

    // Xóa file khỏi thư mục
    public static boolean deleteFile(String saveDirectory, String fileName) throws IOException {
        File file = new File(saveDirectory + File.separator + fileName);
        if (file.exists()) {
            return file.delete();  // Trả về true nếu xóa thành công
        }
        return false;  // File không tồn tại
    }

    // Upload và lưu hình, trả về tên hình
    public static String uploadAndSaveImage(Part part, String saveDirectory) throws IOException {
        String fileName = saveFile(part, saveDirectory);
        return fileName;  // Trả về tên hình đã lưu
    }

    // Hàm upload và lưu hình dành cho GenericService
    public static String uploadImageForEntity(Part part, String saveDirectory) throws IOException {
        return uploadAndSaveImage(part, saveDirectory);
    }
}