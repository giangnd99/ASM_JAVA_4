package controller.client_side;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.impl.VideoServiceImpl;

import java.io.IOException;

@WebServlet("/home")
public class HomePage extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1001950612212965953L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VideoServiceImpl videoService = new VideoServiceImpl(req, resp);
        //Khong nen khoi tao lai service
        try {
            videoService.loadListToPage();
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác
            e.printStackTrace();
        }
    }
}
