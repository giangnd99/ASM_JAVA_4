package servlet.controller.client_side.video;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.VideoHandler;

import java.io.IOException;

@WebServlet("/search")
public class ListVideoBySearch extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VideoHandler videoHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchPage = "/views/user/search-video.jsp";
        videoHandler = new VideoHandler(req, resp);
        videoHandler.showVideoByTitleContaining();
        req.getRequestDispatcher(searchPage).forward(req, resp);

    }
}
