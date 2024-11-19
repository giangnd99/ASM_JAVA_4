package dao;

import entity.Video;

import java.util.Date;
import java.util.List;
/* Danh sách các Video được share bởi User ? */

public interface VideoDao {
    Video create(Video t);

    Video update(Video t);

    Video get(Object id);

    boolean delete(Object id);

    List<Video> listAll();

    List<Video> paginate(int pageNumber, int pageSize);

    int count();

    Video findByHref(String href);

    Video findByTitle(String title);

    /* Xem các Video mà tiêu đề chứa từ ... */
    List<Video> findByTitleContain(String keywordTitle);

    List<Video> sortByViews();

    List<Video> findByDescriptionContain(String keywordDescription);

    List<Video> findBySharedDate(Date sharedDate);

    /* Danh sách các Video được User ? yêu thích  */
    List<Video> findByFavorite(Object userId);

    /* Danh sách các Video được share */
    List<Video> findByShare(String userId);

    List<Video> sortVideoByLike();

    List<Video> sortVideoByShare();

    List<Video> findBySharedDateIn2024();
///Video khong duoc ai yeu thich
    List<Video> findVideoNotExistsInFavorite();
}
