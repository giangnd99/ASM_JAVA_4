package servlet.controller.admin_side.video;

import entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.HistoryHandler;
import servlet.handler.request.VideoHandler;

import java.io.IOException;
import java.util.List;

@WebServlet({"/admin/manage-video"})
public class ManagerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private HistoryHandler historyHandler;
    private VideoHandler videoHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoManager = "/views/admin/manage-video.jsp";
        historyHandler = new HistoryHandler(req, resp);
        historyHandler.showAllVideoHistory();
        req.getRequestDispatcher(videoManager).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoManager = "/views/admin/manage-video.jsp";
        historyHandler = new HistoryHandler(req, resp);
        String key = req.getParameter("search");
        videoHandler = new VideoHandler(req, resp);
        List<Video> list = videoHandler.getVideoByTitleContaining(key);
        historyHandler.setAllVideoHistories(list);
        req.getRequestDispatcher(videoManager).forward(req, resp);
    }
}
