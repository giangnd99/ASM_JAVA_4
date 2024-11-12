package servlet.controller.client_side.video;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.handler.request.HistoryHandler;

import java.io.IOException;

@WebServlet("/favorite-video")
public class ListVideoByFavorite extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HistoryHandler historyHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        historyHandler = new HistoryHandler(req, resp);
        String favoritePage = "/views/user/favorite-video.jsp";
        historyHandler.showFavVideoByCurrentUser();
        req.getRequestDispatcher(favoritePage).forward(req, resp);
    }
}
