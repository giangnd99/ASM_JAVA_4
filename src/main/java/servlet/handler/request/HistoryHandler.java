package servlet.handler.request;

import constant.MessageType;
import entity.Favorite;
import entity.Share;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmailService;
import service.HistoryService;
import service.VideoService;
import service.impl.EmailServiceImpl;
import service.impl.HistoryServiceImpl;
import service.impl.VideoServiceImpl;
import servlet.handler.AbstractHandler;
import util.EmailUtil;

import java.io.IOException;
import java.util.List;

public class HistoryHandler extends AbstractHandler<Video> {

    private final HistoryService historyService;
    private final VideoService videoService;

    private final EmailService emailService;

    public HistoryHandler(HttpServletRequest req, HttpServletResponse resp) {
        super(req, resp);
        this.historyService = new HistoryServiceImpl();
        this.videoService = new VideoServiceImpl();
        this.emailService = new EmailServiceImpl();
    }


    public Favorite getCurrentFavorite() {
        if (isLoggedIn()) {
            return historyService.findFavoriteById(CURRENT_USER, CURRENT_HREF);
        }
        return null;
    }

    public void showFavVideoByCurrentUser() throws Exception {
        String action = request.getParameter("action");
        if (action != null) {
            doAction(action);
        }
        List<Favorite> favoritesByUser = historyService.getFavoritesByUser(CURRENT_USER);
        List<Video> favoriteVideoByUser = videoService.listFavVideoByUser(favoritesByUser);
        request.setAttribute("videos", VideoHandler.getActiveVideo(favoriteVideoByUser));
        request.setAttribute("favUser", favoritesByUser);
    }

    public void doAction(String action) throws Exception {
        Favorite favorite = getCurrentFavorite();
        switch (action) {
            case "like":
                favorite = historyService.addFavorite(CURRENT_USER, CURRENT_HREF);
                request.setAttribute("favorite", favorite);
                break;
            case "unlike":
                historyService.removeFavorite(favorite);
                break;
            case "watch":
                setViews(videoService.findByHref(CURRENT_HREF));
                request.setAttribute("favorite", favorite);
                break;
            case "share":
                doShare();
                request.setAttribute("favorite", favorite);
                break;
            default:
                break;
        }
    }

    public String getAction() {
        String action = request.getParameter("action");

        if (action.equals("watch")) {
            return "watch";
        } else if (CURRENT_USER == null) {
            message = "Vui lòng đăng nhập để like";
            setMessage(MessageType.WARNING, message);
            return null;
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

    public void doShare() throws IOException {
        if (notificationForShare()) {
            addShare();
        }
    }

    public boolean addShare() {
        String emails = request.getParameter("emails");
        String href = request.getParameter("id");
        Share currentShare = historyService.addShare(getCurrentUser(), href, emails);
        sendShareEmail(emails, CURRENT_HREF);
        return currentShare != null;
    }

    private void sendShareEmail(String emails, String videoHref) {
        EmailUtil emailUtil = new EmailUtil();
        String ytbUrl = "https://www.youtube.com/watch?v=";
        String[] emailList = emails.split(",");
        emailUtil.setMessageBody("Video của " + CURRENT_USER.getFullname() + " gửi cho bạn: " + ytbUrl + videoHref);
        emailUtil.setRecipients(emailList);
        if (emailService.sendEmailLinkVideo(emailUtil)) {
            setMessage(MessageType.INFO, "Video đã được chia sẽ thành công");
        }
    }
    boolean notificationForShare() {
        if (!isLoggedIn()) {
            setMessage(MessageType.WARNING, "Vui lòng đăng nhập để share");
            return false;
        }
        return true;
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

}
