package service.impl;

import dao.VideoDao;
import dao.impl.VideoDaoImpl;
import entity.Favorite;
import entity.Video;
import service.VideoService;

import java.util.ArrayList;
import java.util.List;

public class VideoServiceImpl implements VideoService {

    private VideoDao videoDao;

    public VideoServiceImpl() {
        this.videoDao = new VideoDaoImpl();
    }


    @Override
    public Video save(Video video) {
        return videoDao.create(video);
    }

    @Override
    public Video update(Video video) {
        return videoDao.update(video);
    }

    @Override
    public Video remove(Video video) {
        video.setActive(false);
        return videoDao.update(video);
    }

    @Override
    public Video findById(int id) {
        return videoDao.get(id);
    }

    @Override
    public List<Video> listAll() {
        return videoDao.listAll();
    }

    @Override
    public List<Video> findByTitleContaining(String title) {
        return videoDao.findByTitleContain(title);
    }

    @Override
    public List<Video> findByDescriptionContaining(String description) {
        return videoDao.findByDescriptionContain(description);
    }

    @Override
    public Video findByHref(String href) {
        return videoDao.findByHref(href);
    }

    @Override
    public List<Video> listFavVideoByUser(List<Favorite> favorites) {
        List<Video> favVideos = new ArrayList<>();
        List<Video> list = listAll();
        for (Favorite favorite : favorites) {
            for (Video video : list) {
                boolean checkVideoId = favorite.getVideoId().getId().equals(video.getId());
                if (checkVideoId) {
                    favVideos.add(video);
                }
            }
        }
        return favVideos;
    }

    @Override
    public List<Video> top5VideoByViews() {
        return videoDao.sortByViews().subList(0, 5);
    }
}
