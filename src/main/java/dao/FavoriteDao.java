package dao;

import entity.Favorite;

import java.util.List;

public interface FavoriteDao {

    List<Favorite> findByUser(Object id);

    List<Favorite> findByUserIdAndVideoId(Object userId, Object videoId);

    Favorite create(Favorite favorite);

    Favorite update(Favorite favorite);

    boolean delete(Object id);
}
