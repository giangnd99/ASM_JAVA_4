package servlet.controller.client_side.video;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.VideoHandler;

import java.io.IOException;

@WebServlet("/current-video")
public class ListCurrentlyVideo extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentVideoPage = "/views/user/current-video.jsp";
        VideoHandler videoHandler = new VideoHandler(req, resp);
        videoHandler.showCurrentlyVideos();
        req.getRequestDispatcher(currentVideoPage).forward(req, resp);
    }
}
