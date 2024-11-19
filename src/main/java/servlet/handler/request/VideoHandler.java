package servlet.handler.request;

import constant.MessageType;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.HistoryService;
import service.VideoService;
import service.impl.HistoryServiceImpl;
import service.impl.VideoServiceImpl;
import servlet.handler.AbstractHandler;
import util.PaginationHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoHandler extends AbstractHandler<Video> {

    private VideoService videoService;
    private HistoryHandler historyHandler;
    private HistoryService historyService;
    private PaginationHelper<Video> paginationVideo;
    private static List<String> cachedHref = new ArrayList<>();


    public VideoHandler(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
        this.videoService = new VideoServiceImpl();
        this.historyHandler = new HistoryHandler(req, resp);
        this.historyService = new HistoryServiceImpl();
    }

    public void loadVideosToHomePage() throws Exception {
        List<Video> videos = videoService.listAll();
        Map<Video, Integer> mapVideoAndLikeCount = new HashMap<>();
        Map<Video, Integer> mapVideoAndShareCount = new HashMap<>();
        for (Video video : videos) {
            mapVideoAndLikeCount.put(video, historyService.getLikeCount(video.getId()));
            mapVideoAndShareCount.put(video, historyService.getShareCount(video.getId()));
        }
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
        setHisory();
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

    public void showVideoByTitleContaining() {
        String keyword = request.getParameter("search");
        List<Video> videosByTitle = getVideoByTitleContaining(keyword);
        request.setAttribute("search", keyword);
        request.setAttribute("videos", videosByTitle);
    }

    public List<Video> getVideoByTitleContaining(String keyword) {
        List<Video> videosByTitle = videoService.findByTitleContaining(keyword);
        if (videosByTitle == null || videosByTitle.size() == 0) {
            setMessage(MessageType.ERROR, "Không tìm thấy video với từ khóa");
            return null;
        }
        return videosByTitle;
    }

    public void setTop5VideoByViews(){
        List<Video> top10Views = videoService.top10VideoByViews();
        request.setAttribute("top5Videos", top10Views);
    }


    public void showListVideoByFilter() {
        String keyword = request.getParameter("filter");
        switch (keyword) {
            case "time":
                setListVideoIn2024();
                break;
            case "likes":
                break;
        }
    }

    public void setTop10VideoByLikes() {
        request.setAttribute("videos", videoService.getListVideoSortByLike());
    }

    public void setListVideoIn2024() {
        List<Video> listVideoIn2024 = videoService.getListVideoIn2024();
        request.setAttribute("videos", listVideoIn2024);
    }

    public void setHisory() {
        historyHandler.setLikeCount();
        historyHandler.setShareCount();
    }
}
