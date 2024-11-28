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
import java.util.List;

public class VideoHandler extends AbstractHandler<Video> {

    private final VideoService videoService;
    private final HistoryHandler historyHandler;
    private final HistoryService historyService;
    private PaginationHelper<Video> paginationVideo;
    private final static List<String> cachedHref = new ArrayList<>();


    public VideoHandler(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
        this.videoService = new VideoServiceImpl();
        this.historyHandler = new HistoryHandler(req, resp);
        this.historyService = new HistoryServiceImpl();
    }

    public void loadVideosToHomePage() throws Exception {
        List<Video> videos = videoService.listAll();
        paginationVideo = new PaginationHelper<>(videos, PAGE_SIZE);
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
        String action = historyHandler.getAction();
        if (action != null) {
            historyHandler.doAction(action);
        }
        setHisory();
        setTop5VideoByViews();
        request.setAttribute("video", video);
    }

    public void setTop5VideoByViews() {
        List<Video> top10Views = videoService.top10VideoByViews();
        PaginationHelper<Video> pagination = new PaginationHelper<>(top10Views, PAGE_SIZE);
        List<Video> videoOfPage = pagination.getPage(getPageNumber());
        setupPagination(pagination);
        request.setAttribute("top5Videos", videoOfPage);
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
