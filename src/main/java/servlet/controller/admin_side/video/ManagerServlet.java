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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoManager = "/views/admin/manage-video.jsp";
        req.getRequestDispatcher(videoManager).forward(req, resp);
    }
}
