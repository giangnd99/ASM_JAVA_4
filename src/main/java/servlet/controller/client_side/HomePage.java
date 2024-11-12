package servlet.controller.client_side;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.VideoHandler;

@WebServlet("/home")
public class HomePage extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1001950612212965953L;

    private VideoHandler videoHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        videoHandler = new VideoHandler(req, resp);
        try {
            videoHandler.loadVideosToHomePage();
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác
            e.printStackTrace();
        }
    }
}
