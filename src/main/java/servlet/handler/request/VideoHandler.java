package servlet.handler.request;

import constant.MessageType;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.VideoService;
import service.impl.VideoServiceImpl;
import servlet.handler.AbstractHandler;
import util.PaginationHelper;

import java.util.ArrayList;
import java.util.List;

public class VideoHandler extends AbstractHandler<Video> {

    private VideoService videoService;
    private HistoryHandler historyHandler;
    private PaginationHelper<Video> paginationVideo;
    private static List<String> cachedHref = new ArrayList<>();


    public VideoHandler(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
        this.videoService = new VideoServiceImpl();
        this.historyHandler = new HistoryHandler(req, resp);
    }

    public void loadVideosToHomePage() throws Exception {
        List<Video> videos = videoService.listAll();
        paginationVideo = new PaginationHelper<Video>(videos, PAGE_SIZE);
        List<Video> videoOfPage = paginationVideo.getPage(getPageNumber());
        String homePage = "/views/user/index.jsp";
        setupPagination(paginationVideo);
        request.setAttribute("videos", videoOfPage);
        message = message == null ? "" : message;
        super.setMessage(messageType, message);
        super.forward(homePage);
    }

    public void showDetails() throws Exception {
        String href = request.getParameter("id");
        Video video = videoService.findByHref(href);
        addHrefToCache();
        String action = getAction();
        if (action != null) {
            historyHandler.doAction(action);
        }
        setViews(video);
        setTop5VideoByViews();
        request.setAttribute("video", video);
    }

    public String getAction() {
        String action = request.getParameter("action");
        if (CURRENT_USER == null) {
            message = "Vui lòng đăng nhập để like";
            setMessage(MessageType.WARNING, message);
            return null;
        } else if (action == null) {
            return "watch";
        } else if (action.equals("unlike")) {
            return "unlike";
        } else if (action.equals("share")) {
            return "share";
        }
        return "like";
    }

    private void setViews(Video video) throws Exception {
        String action = getAction();
        if (action == null || action.equals("watch")) {
            doViews(video);
        }
    }

    private void doViews(Video video) throws Exception {
        int view = video.getViews();
        view++;
        video.setViews(view);
        videoService.update(video);
    }

    public void showCurrentlyVideos() {
        List<Video> currentlyVideos = getCurrentlyVideos();
        paginationVideo = new PaginationHelper<>(currentlyVideos, PAGE_SIZE);
        List<Video> videoOfPage = paginationVideo.getPage(getPageNumber());
        setupPagination(paginationVideo);
        request.setAttribute("videos", videoOfPage);
        message = message == null ? "" : message;
        super.setMessage(messageType, message);
    }

    public List<Video> getCurrentlyVideos() {
        List<Video> videos = new ArrayList<Video>();
        List<String> hrefs = getCacheHref();
        for (String href : hrefs) {
            Video video = videoService.findByHref(href);
            videos.add(video);
        }
        return videos;
    }

    private List<String> getCacheHref() {
        return cachedHref;
    }

    public void addHrefToCache() {
        String href = request.getParameter("id");

        if (cachedHref.isEmpty()) cachedHref.add(href);
        if (!checkExistsHref(href)) cachedHref.add(href);
    }

    private boolean checkExistsHref(String href) {
        for (String h : cachedHref) {
            if (href.equals(h)) {
                return true;
            }
        }
        return false;
    }

    public void setTop5VideoByViews(){
        List<Video> top5Views = videoService.top5VideoByViews();
        request.setAttribute("top5Videos", top5Views);
    }
}
