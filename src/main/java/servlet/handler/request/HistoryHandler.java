package servlet.handler.request;

import entity.Favorite;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.HistoryService;
import service.VideoService;
import service.impl.HistoryServiceImpl;
import service.impl.VideoServiceImpl;
import servlet.handler.AbstractHandler;

import java.util.List;

public class HistoryHandler extends AbstractHandler<Video> {

    private HistoryService historyService;
    private VideoService videoService;

    public HistoryHandler(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
        this.historyService = new HistoryServiceImpl();
        this.videoService = new VideoServiceImpl();
    }

    public void doAction(String action) {
        response.setContentType("application/json");
        Favorite favorite = getFavorite();
        switch (action) {
            case "like":
                favorite = historyService.addFavorite(CURRENT_USER, CURRENT_HREF);
                request.setAttribute("favorite", favorite);
                break;
            case "unlike":
                historyService.removeFavorite(favorite);
                break;
            case "watch":
                request.setAttribute("favorite", favorite);
            default:
                break;
        }
    }

    public Favorite getFavorite() {
        if (isLoggedIn()) {
            return historyService.findFavoriteById(CURRENT_USER, CURRENT_HREF);
        }
        return null;
    }

    public void showFavVideoByCurrentUser() {
        String action = request.getParameter("action");
        if (action != null) {
            doAction(action);
        }
        List<Favorite> favoritesByUser = historyService.getFavoritesByUser(CURRENT_USER);
        List<Video> favoriteVideoByUser = videoService.listFavVideoByUser(favoritesByUser);
        request.setAttribute("videos", favoriteVideoByUser);
        request.setAttribute("favUser", favoritesByUser);
    }
}
