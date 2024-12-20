package dao.impl;

import dao.GenericDao;
import dao.VideoDao;
import entity.Video;
import jakarta.persistence.Query;

import java.util.Date;
import java.util.List;

public class VideoDaoImpl extends GenericDao<Video> implements VideoDao {


    @Override
    public Video get(Object id) {
        return super.findById(id);
    }

    @Override
    public boolean delete(Object id) {
        return super.deleteById(id);
    }

    @Override
    public List<Video> listAll() {
        return super.findAll();
    }

    @Override
    public List<Video> paginate(int pageNumber, int pageSize) {
        return super.paginate(Video.class, pageNumber, pageSize);
    }

    @Override
    public int count() {
        return super.count();
    }

    @Override
    public Video findByHref(String href) {
        String jpql = "select v from Video v where v.href = ?1 ";
        return super.findOneThingByJpql(jpql, href);
    }

    @Override
    public Video findByTitle(String title) {
        String jpql = "select v from Video v where v.title = ?1 ";
        return super.findOneThingByJpql(jpql, title);
    }

    @Override
    public List<Video> findByTitleContain(String keywordTitle) {
        String jpql = "select v from Video v where v.title like:keywordTitle ";
        return super.execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter("keywordTitle", "%" + keywordTitle.toLowerCase() + "%");
            return query.getResultList();
        });
    }

    @Override
    public List<Video> sortByViews() {
        String jpql = "select v from Video v order by v.views desc ";
        return execute(entityManager -> entityManager.createQuery(jpql).getResultList());
    }

    @Override
    public List<Video> findByDescriptionContain(String keywordDescription) {
        return execute(entityManager -> {
            Query query = entityManager.createQuery("select v from Video v where v.description = ?1 ");
            query.setParameter(1, keywordDescription);
            return query.getResultList();
        });
    }

    @Override
    public List<Video> findBySharedDate(Date sharedDate) {
        String jpql = "select f.videoId from Favorite f where f.sharedDate = ?1 desc limit 10";
        return findManyThingByJpql(jpql, sharedDate);
    }

    @Override
    public List<Video> findByFavorite(Object userId) {
        String jpql = "select f.videoId from Favorite f  where f.userId.id = ?1";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter(1, userId);
            return query.getResultList();
        });
    }

    @Override
    public List<Video> findByShare(String userId) {
        String jpql = "select s.videoId from Share s";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            return query.getResultList();
        });
    }

    @Override
    public List<Video> sortVideoByLike() {
        String jpql = "select v from Video v join Favorite f on v.id = f.videoId.id group by v.id order by count(f.videoId.id)";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setMaxResults(10);
            return query.getResultList();
        });
    }

    @Override
    public List<Video> sortVideoByShare() {
        String jpql = "select  v from Video v join Share s on v.id = s.videoId.id group by v.id order by count(s.videoId.id)";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            return query.getResultList();
        });
    }

        @Override
        public List<Video> findBySharedDateIn2024() {
            String jpql = "select s.videoId from Share s where year(s.shareDate) = 2024 group by s.videoId.id order by s.shareDate desc";
            return execute(entityManager -> {
                Query query = entityManager.createQuery(jpql);
                return query.getResultList();
            });
        }

    @Override
    public List<Video> findVideoNotExistsInFavorite() {
        String jpql = "select v from Video v left join Favorite f on v.id = f.videoId.id where f.videoId.id is null";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            return query.getResultList();
        });
    }


}
