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
import service.HistoryService;

import java.util.List;

public class HistoryServiceImpl implements HistoryService {

    private FavoriteDao favoriteDao;
    private ShareDao shareDao;
    private VideoDao videoDao;

    public HistoryServiceImpl() {
        this.favoriteDao = new FavoriteDaoImpl();
        this.shareDao = new ShareDaoImpl();
        this.videoDao = new VideoDaoImpl();
    }

    @Override
    public List<Favorite> getAllFavorites() {
        return favoriteDao.listAll();
    }

    @Override
    public Share getSharesByHref(String href) {
        return shareDao.findByHref(href);
    }

    @Override
    public List<Favorite> getFavoritesByUser(User currentUser) {
        Integer idUser = currentUser.getId();
        return favoriteDao.findByUser(idUser);
    }

    @Override
    public List<Share> getSharesByUser(User currentUser) {
        Integer idUser = currentUser.getId();
        return shareDao.findByUser(idUser);
    }

    @Override
    public Favorite addFavorite(User currentUser, String href) {
        Video currentVideo = videoDao.findByHref(href);
        Favorite favorite = new Favorite();
        favorite.setUserId(currentUser);
        favorite.setVideoId(currentVideo);
        return favoriteDao.create(favorite);

    }

    @Override
    public Share addShare(User currentUser, String href,String emails) {
        Video currentVideo = videoDao.findByHref(href);
        Share share = new Share();
        share.setUserId(currentUser);
        share.setVideoId(currentVideo);
        return shareDao.create(share);
    }

    @Override
    public Favorite findFavoriteById(User currentUser, String href) {
        Video currentVideo = videoDao.findByHref(href);
        Integer idUser = currentUser.getId();
        Integer idVideo = currentVideo.getId();
        return favoriteDao.findByUserIdAndVideoId(idUser, idVideo).stream().findFirst().orElse(null);
    }

    @Override
    public Share findShareById(User currentUser, String href) {
        Video currentVideo = videoDao.findByHref(href);
        Integer idUser = currentUser.getId();
        Integer idVideo = currentVideo.getId();
        return shareDao.findByUserIdAndVideoId(idUser, idVideo).stream().findFirst().orElse(null);
    }

    @Override
    public boolean removeFavorite(Favorite favorite) {
        return favoriteDao.delete(favorite.getId());
    }

    @Override
    public int getLikeCount(Object videoId) {
        return favoriteDao.countByVideoId(videoId).intValue();
    }

    @Override
    public int getShareCount(Object videoId) {
        return shareDao.countByVideoId(videoId).intValue();
    }

    @Override
    public String getLatestLikeDate(Integer id) {
        return favoriteDao.findLatestLikeDate().getLikedDate().toString();
    }

    @Override
    public String getOldestLikeDate(Integer id) {
        return favoriteDao.findOldestLikeDate().getLikedDate().toString();
    }

    @Override
    public Share getLatestShare(Integer idVideo) {
        return shareDao.findLatestShareDate(idVideo);
    }

    @Override
    public Share getOldestShare(Integer idVideo) {
        return shareDao.findOldestShareDate(idVideo);
    }
}
