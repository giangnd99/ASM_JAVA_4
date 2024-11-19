package servlet.handler.request;

import constant.MessageType;
import dto.VideoHistoryDTO;
import entity.Favorite;
import entity.Share;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.HistoryService;
import service.VideoService;
import service.impl.HistoryServiceImpl;
import service.impl.VideoServiceImpl;
import servlet.handler.AbstractHandler;

import java.util.List;
import java.util.stream.Collectors;

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
                break;
            case "share":

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

    public void setAllFavoriteToAdmin() {
        List<Favorite> favorites = historyService.getAllFavorites();
        request.setAttribute("favorites", favorites);
    }

    public void doShare() {
        if (notificationForShare()) {
            addShare();

        }

    }

    public boolean addShare() {
        String emails = request.getParameter("emails");
        Share currentShare = historyService.addShare(getCurrentUser(), CURRENT_HREF, emails);
        return currentShare != null;
    }

    boolean notificationForShare() {
        if (!isLoggedIn()) {
            setMessage(MessageType.WARNING, "Vui lòng đăng nhập để share");
            return false;
        }
        return true;
    }

    Share getShareByIdUserAndVideo() {
        if (isLoggedIn()) {
            return historyService.findShareById(CURRENT_USER, CURRENT_HREF);
        }
        return null;
    }

    public void setShareCount() {
        Integer idVideo = videoService.findByHref(getCurrentVideoHref()).getId();
        int shareCount = historyService.getShareCount(idVideo);
        request.setAttribute("shareCount", shareCount);
    }

    public void setLikeCount() {
        Integer idVideo = videoService.findByHref(getCurrentVideoHref()).getId();
        int likeCount = historyService.getLikeCount(idVideo);
        request.setAttribute("likeCount", likeCount);
    }

    public void showAllVideoHistory() {
        setAllVideoHistories(videoService.listAll());
    }

    public void setAllVideoHistories(List<Video> allVideos) {
        // Tạo danh sách VideoHistoryDTO
        if (allVideos != null) {
            List<VideoHistoryDTO> videoHistories = allVideos.stream().map(video -> {
                VideoHistoryDTO dto = new VideoHistoryDTO();

                // Set các thuộc tính cơ bản của video
                dto.setVideoTitle(video.getTitle());
                dto.setVideoHref(video.getHref());
                dto.setViewCount(video.getViews().toString());
                dto.setIsActive(video.isActive() ? "Showed" : "UnShow");

                // Lấy số lượng yêu thích
                int likeCount = historyService.getLikeCount(video.getId());
                dto.setLikeCount(likeCount);

                // Lấy ngày yêu thích mới nhất và cũ nhất
                String likeDateLatest = historyService.getLatestLikeDate(video.getId());
                String likeDateOldest = historyService.getOldestLikeDate(video.getId());
                dto.setLikeDateLatest(likeDateLatest);
                dto.setLikeDateOldest(likeDateOldest);

                // Lấy thông tin share
                int shareCount = historyService.getShareCount(video.getId());
                dto.setShareCount(shareCount);
                Share latestShare = historyService.getLatestShare(video.getId());
                Share oldestShare = historyService.getOldestShare(video.getId());

                if (latestShare != null) {
                    dto.setSenderName(latestShare.getUserId().getFullname());
                    dto.setSenderEmail(latestShare.getUserId().getEmail());
                    dto.setReceiverEmail(latestShare.getEmails());
                    dto.setShareDate(latestShare.getShareDate().toString());
                    dto.setShareDateLatest(latestShare.getShareDate().toString());
                }
                if (oldestShare != null) {
                    dto.setShareDateOldest(oldestShare.getShareDate().toString());
                }

                return dto;
            }).collect(Collectors.toList());
            request.setAttribute("histories", videoHistories);
        }
        request.setAttribute("allVideos", allVideos);
        // Set danh sách DTO vào request

    }

}
