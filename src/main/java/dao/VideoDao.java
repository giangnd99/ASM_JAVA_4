package dao;

import entity.Video;

import java.util.List;

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

    List<Video> findByTitleContain(String keywordTitle);

    List<Video> sortByViews(boolean asc);

}
