package service.impl;

import dao.FavoriteDao;
import dao.ShareDao;
import dao.VideoDao;
import dao.impl.FavoriteDaoImpl;
import dao.impl.ShareDaoImpl;
import dao.impl.VideoDaoImpl;
import entity.Favorite;
import entity.Share;
import entity.User;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.GenericService;

import java.util.List;

public class HistoryServiceImpl extends GenericService implements service.HistoryService {

    private FavoriteDao favoriteDao;
    private ShareDao shareDao;
    private VideoDao videoDao;

    public HistoryServiceImpl(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.favoriteDao = new FavoriteDaoImpl();
        this.shareDao = new ShareDaoImpl();
        this.videoDao = new VideoDaoImpl();
    }

    @Override
    public List<Favorite> getFavorites() {
        User currentUser = super.getCurrentUser();
        Video currentVideo = videoDao.findByHref(getCurrentVideoHref());
        Integer idUser = currentUser.getId();
        Integer idVideo = currentVideo.getId();
        return favoriteDao.findByUserIdAndVideoId(idUser, idVideo);
    }

    @Override
    public List<Share> getShares() {
        User currentUser = super.getCurrentUser();
        Video currentVideo = videoDao.findByHref(getCurrentVideoHref());
        Integer idUser = currentUser.getId();
        Integer idVideo = currentVideo.getId();
        return shareDao.findByUserIdAndVideoId(idUser, idVideo);
    }

    @Override
    public void addFavorite() {
        User currentUser = super.getCurrentUser();
        Video currentVideo = videoDao.findByHref(getCurrentVideoHref());
        if (isLikeOrUnLike()) {
            Favorite favorite = new Favorite();
            favorite.setUserId(currentUser);
            favorite.setVideoId(currentVideo);
            favorite = favoriteDao.create(favorite);
            super.setObject(favorite);
        } else {
            Integer favoriteId = Integer.valueOf(request.getParameter("favoriteId"));
            favoriteDao.delete(favoriteId);
        }
    }

    @Override
    public void addShare() {
        String action = request.getParameter("action");
        User currentUser = super.getCurrentUser();
        Video currentVideo = videoDao.findByHref(getCurrentVideoHref());
        if (action.equals("share")) {
            Share share = new Share();
            share.setUserId(currentUser);
            share.setVideoId(currentVideo);
            share = shareDao.create(share);
            super.setObject(share);
        }
    }

    @Override
    public Favorite findFavoriteById() {
        User currentUser = super.getCurrentUser();
        Video currentVideo = videoDao.findByHref(getCurrentVideoHref());
        Integer idUser = currentUser.getId();
        Integer idVideo = currentVideo.getId();
        return favoriteDao.findByUserIdAndVideoId(idUser, idVideo).stream().findFirst().orElse(null);
    }

    @Override
    public Share findShareById() {
        User currentUser = super.getCurrentUser();
        Video currentVideo = videoDao.findByHref(getCurrentVideoHref());
        Integer idUser = currentUser.getId();
        Integer idVideo = currentVideo.getId();
        return shareDao.findByUserIdAndVideoId(idUser, idVideo).stream().findFirst().orElse(null);
    }

    @Override
    public boolean isLikeOrUnLike() {
        String action = request.getParameter("action");
        Favorite favorite = findFavoriteById();
        return action.equals("like") && favorite == null;
    }
}
