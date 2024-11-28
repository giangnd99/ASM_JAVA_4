package dao;

import entity.Favorite;

import java.util.List;

public interface FavoriteDao {

    List<Favorite> listAll();

    List<Favorite> findByUser(Object id);

    List<Favorite> findByHref(Object href);

    List<Favorite> findByUserIdAndVideoId(Object userId, Object videoId);

    Favorite create(Favorite favorite);

    Favorite update(Favorite favorite);

    boolean delete(Object id);

    Long countByVideoId(Object videoId);

    Favorite findLatestLikeDate();

    Favorite findOldestLikeDate();

}
