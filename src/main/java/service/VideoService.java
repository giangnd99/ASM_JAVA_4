package service;

import entity.Favorite;
import entity.Video;

import java.util.List;

public interface VideoService {

     Video save(Video video);

     Video update(Video video);

     Video remove(Video video);

     Video findById(int id);

     List<Video> listAll();

     List<Video> findByTitleContaining(String title);

     List<Video> findByDescriptionContaining(String description);

    Video findByHref(String href);

     List<Video> listFavVideoByUser(List<Favorite> favorites);

     List<Video> top5VideoByViews();
}
