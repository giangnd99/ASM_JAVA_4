package service;

import entity.Favorite;
import entity.Share;
import entity.User;

import java.util.List;

public interface HistoryService {

    List<Favorite> getAllFavorites();

    List<Share> getSharesByHref(String href);


    List<Favorite> getFavoritesByHref(String href);

    List<Favorite> getFavoritesByUser(User currentUser);

    List<Share> getSharesByUser(User currentUser);

    Favorite addFavorite(User currentUser, String href);

    Share addShare(User currentUser, String href,String emails);

    Favorite findFavoriteById(User currentUser, String href);

    Share findShareById(User currentUser, String href);

    boolean removeFavorite(Favorite favorite);

    int getLikeCount(Object videoId);

    int getShareCount(Object videoId);

    String getLatestLikeDate(Integer id);

    String getOldestLikeDate(Integer id);

    Share getLatestShare(Integer id);

    Share getOldestShare(Integer id);
}
