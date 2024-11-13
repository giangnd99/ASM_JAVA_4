package dao.impl;

import dao.FavoriteDao;
import dao.GenericDao;
import entity.Favorite;

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
}
