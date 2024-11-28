package servlet.api.history;

import dto.HistoryDTO;
import entity.Video;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.HistoryService;
import service.VideoService;
import service.impl.HistoryServiceImpl;
import service.impl.VideoServiceImpl;
import util.RestIO;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

@WebServlet({"/admin/api/history", "/admin/api/history/share", "/admin/api/history/favorite"})
public class HistoryAdminController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private HistoryService historyService = new HistoryServiceImpl();
    private VideoService videoService = new VideoServiceImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<HistoryDTO> histories = HistoryDTO.getAllVideosMapper(getVideos(), historyService);
        RestIO.writeObject(resp, histories);
    }

    List<Video> getVideos() {
        return videoService.listAll();
    }
}
