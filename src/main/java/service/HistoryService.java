package service;

import entity.Favorite;
import entity.Share;
import entity.User;

import java.util.List;

public interface HistoryService {


    List<Favorite> getFavorites();

    List<Share> getShares();

    void addFavorite();

    void addShare();

    Favorite findFavoriteById();

    Share findShareById();

    boolean isLikeOrUnLike();
}
