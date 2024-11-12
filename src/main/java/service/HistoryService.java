package service;

import entity.Favorite;
import entity.Share;
import entity.User;

import java.util.List;

public interface HistoryService {


    List<Favorite> getFavoritesByUser(User currentUser);

    List<Share> getSharesByUser(User currentUser);

    Favorite addFavorite(User currentUser, String href);

    Share addShare(User currentUser, String href);

    Favorite findFavoriteById(User currentUser, String href);

    Share findShareById(User currentUser, String href);

    boolean removeFavorite(Favorite favorite);
}
