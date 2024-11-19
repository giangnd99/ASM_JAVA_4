package dao.impl;

import entity.Video;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class VideoDaoImplTest {

    @Test
    void sortVideoByLike() {
        VideoDaoImpl dao = new VideoDaoImpl();
        List<Video> list = dao.sortVideoByLike();
        System.out.println(list.size());
        for (Video video : list) {
            System.out.println(video.getTitle());
        }
        assertNotNull(list);
    }

    @Test
    void findBySharedDateIn2024() {
        VideoDaoImpl dao = new VideoDaoImpl();
        List<Video> list = dao.findBySharedDateIn2024();
        System.out.println(list.toString());
        for (Video video : list) {
            System.out.println(video.getTitle());
        }
        assertNotNull(list);
    }

    @Test
    void findVideoUnlike() {
        VideoDaoImpl dao = new VideoDaoImpl();
        List<Video> list = dao.findVideoNotExistsInFavorite();
        System.out.println(list.size());
        for (Video video : list) {
            System.out.println(video.getTitle());
        }
        assertNotNull(list);
    }
}