package dao.impl;

import dao.GenericDao;
import dao.ShareDao;
import entity.Share;

import java.util.List;

public class ShareDaoImpl extends GenericDao<Share> implements ShareDao {
    @Override
    public List<Share> findByUser(Object id) {
        String jpql = "select s from Share s where s.userId.id = ?1 ";
        return super.findManyThingByJpql( jpql, id);
    }

    @Override
    public List<Share> findByUserIdAndVideoId(Object userId, Object videoId) {
        String jpql = "select s from Share s where s.userId.id = ?1 and s.videoId.id = ?2 ";
        return super.findManyThingByJpql(jpql, userId, videoId);
    }

    @Override
    public boolean delete(Object id) {
        return super.deleteById(id);
    }
}
