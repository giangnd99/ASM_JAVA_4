package dao;

import entity.Share;

import java.util.List;

public interface ShareDao {

    List<Share> findByUser(Object id);

    List<Share> findListByHref(Object href);

    List<Share> findByUserIdAndVideoId(Object userId, Object videoId);

    Share create(Share entity);

    Share update(Share entity);

    boolean delete(Object id);

    Long countByVideoId(Object videoId);

    Share findLatestShareDate(Integer videoId);

    Share findOldestShareDate(Integer videoId);
}
