package dao.impl;

import dao.GenericDao;
import dao.ShareDao;
import entity.Share;
import jakarta.persistence.Query;

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

    @Override
    public Long countByVideoId(Object videoId) {
        String jpql = "select count(*) from Share s where s.videoId.id = ?1 ";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter(1, videoId);
            return (Long) query.getSingleResult();
        });
    }

    @Override
    public Share findLatestShareDate(Integer idVideo) {
        String jpql = "select s from Share s where s.videoId.id = :idVideo group by s.id order by s.shareDate desc limit 1 ";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter("idVideo", idVideo);
            List<Share> list = query.getResultList();
            if (list == null || list.size() == 0) {
                return null;
            }
            return (Share) query.getSingleResult();
        });
    }

    @Override
    public Share findByHref(String href) {
        String jpql = "select s from Share s where s.videoId.href = ?1 ";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter(1, href);
            List<Share> list = query.getResultList();
            if (list == null || list.size() == 0) {
                return null;
            }
            return (Share) query.getSingleResult();
        });
    }

    @Override
    public Share findOldestShareDate(Integer idVideo) {
        String jpql = "select s from Share s where s.videoId.id = :idVideo group by s.id order by s.shareDate asc limit 1";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter("idVideo", idVideo);
            List<Share> list = query.getResultList();
            if (list == null || list.size() == 0) {
                return null;
            }
            return (Share) query.getSingleResult();
        });
    }
}
