package service;

import entity.Video;

import java.util.List;

public interface VideoService {

    public void save(Video video);

    public void update(Video video);

    public void remove(Video video);

    public Video findById(int id);

    public List<Video> listAll();

    public List<Video> findByTitleContaining(String title);

    public List<Video> findByDescriptionContaining(String description);
}
