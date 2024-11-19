package dao.impl;

import dao.FavoriteDao;
import dao.GenericDao;
import entity.Favorite;
import jakarta.persistence.Query;

import java.util.List;

public class FavoriteDaoImpl extends GenericDao<Favorite> implements FavoriteDao {

    @Override
    public List<Favorite> listAll() {
        return super.findAll();
    }

    @Override
    public List<Favorite> findByUser(Object id) {
        String jpql = "select f from Favorite f where f.userId.id = ?1 ";
        return super.findManyThingByJpql(jpql, id);
    }

    @Override
    public List<Favorite> findByUserIdAndVideoId(Object userId, Object videoId) {
        String jpql = "select f from Favorite f where f.userId.id = ?1 and f.videoId.id = ?2 ";
        return super.findManyThingByJpql(jpql, userId, videoId);
    }

    @Override
    public boolean delete(Object id) {
        return super.deleteById(id);
    }

    @Override
    public Long countByVideoId(Object videoId) {
        String jpql = "select count(f) from Favorite f where f.videoId.id = ?1 ";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter(1, videoId);
            return (Long) query.getSingleResult();
        });
    }

    @Override
    public Favorite findLatestLikeDate() {
        String jpql = "select f from Favorite f group by f.id order by f.likedDate desc limit 1";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            List<Favorite> list = query.getResultList();
            if (list == null || list.size() == 0) {
                return null;
            }
            return (Favorite) query.getSingleResult();
        });
    }

    @Override
    public Favorite findOldestLikeDate() {
        String jpql = "select f from Favorite f group by f.id order by f.likedDate asc limit 1 ";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            List<Favorite> list = query.getResultList();
            if (list == null || list.size() == 0) {
                return null;
            }
            return (Favorite) query.getSingleResult();
        });
    }
}
