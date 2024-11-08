package controller.client_side.video;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.impl.VideoServiceImpl;

import java.io.IOException;

@WebServlet("/video-detail")
public class VideoDetail extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VideoServiceImpl videoService = new VideoServiceImpl(req, resp);
        try {
            videoService.showDetails();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
