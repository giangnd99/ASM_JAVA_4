package servlet.api.video;

import entity.Video;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.VideoService;
import service.impl.VideoServiceImpl;
import util.RestIO;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

@WebServlet({"/admin/api/videos/*"})
public class VideoAdminController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private final VideoService videoService;
    public VideoAdminController() {
        this(new VideoServiceImpl()); // Default service
    }

    public VideoAdminController(VideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = extractIdFromPath(req);
        if (id == null) {
            handleGetAllVideos(resp);
        } else {
            handleGetVideoById(resp, id);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handleCreateVideo(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = extractIdFromPath(req);
        if (id != null) {
            handleUpdateVideo(req, resp, id);
        } else {
            RestIO.writeError(resp, "Missing video ID in the request path");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = extractIdFromPath(req);
        if (id != null) {
            handleDeleteVideo(resp, id);
        } else {
            RestIO.writeError(resp, "Missing video ID in the request path");
        }
    }

    // Helper Methods

    private void handleGetAllVideos(HttpServletResponse resp) throws IOException {
        List<Video> videos = videoService.listAll();
        RestIO.writeObject(resp, videos);
    }

    private void handleGetVideoById(HttpServletResponse resp, String id) throws IOException {
        try {
            Video video = videoService.findById(Integer.parseInt(id));
            if (video != null) {
                RestIO.writeObject(resp, video);
            } else {
                RestIO.writeError(resp, "Video not found");
            }
        } catch (NumberFormatException e) {
            RestIO.writeError(resp, "Invalid video ID format");
        }
    }

    private void handleCreateVideo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Video video = RestIO.readObject(req, Video.class);
        videoService.save(video);
        RestIO.writeObject(resp, video);
    }

    private void handleUpdateVideo(HttpServletRequest req, HttpServletResponse resp, String id) throws IOException {
        try {
            Video video = RestIO.readObject(req, Video.class);
            video.setId(Integer.parseInt(id)); // Ensure the ID matches the path
            videoService.update(video);
            RestIO.writeEmptyObject(resp);
        } catch (NumberFormatException e) {
            RestIO.writeError(resp, "Invalid video ID format");
        }
    }

    private void handleDeleteVideo(HttpServletResponse resp, String id) throws IOException {
        try {
            int videoId = Integer.parseInt(id);
            Video video = videoService.findById(videoId);
            if (video != null) {
                videoService.remove(video);
                RestIO.writeEmptyObject(resp);
            } else {
                RestIO.writeError(resp, "Video not found");
            }
        } catch (NumberFormatException e) {
            RestIO.writeError(resp, "Invalid video ID format");
        }
    }

    private String extractIdFromPath(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            return pathInfo.substring(1).trim(); // Remove leading slash
        }
        return null;
    }
}
