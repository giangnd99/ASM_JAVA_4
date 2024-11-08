package dao.impl;

import dao.GenericDao;
import dao.VideoDao;
import entity.Video;
import jakarta.persistence.Query;

import java.util.List;

public class VideoDaoImpl extends GenericDao<Video> implements VideoDao {


    @Override
    public Video get(Object id) {
        return super.findById(Video.class, id);
    }

    @Override
    public boolean delete(Object id) {
        return super.deleteById(Video.class, id);
    }

    @Override
    public List<Video> listAll() {
        return super.findAll(Video.class);
    }

    @Override
    public List<Video> paginate(int pageNumber, int pageSize) {
        return super.paginate(Video.class, pageNumber, pageSize);
    }

    @Override
    public int count() {
        int count = Integer.parseInt(String.valueOf(super.count(Video.class)));
        return count;
    }

    @Override
    public Video findByHref(String href) {
        String jpql = "select v from Video v where v.href = ?1 ";
        return super.findOneThingByJpql(Video.class, jpql, href);
    }

    @Override
    public Video findByTitle(String title) {
        String jpql = "select v from Video v where v.title = ?1 ";
        return super.findOneThingByJpql(Video.class, jpql, title);
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
    public List<Video> sortByViews(boolean asc) {
        String jpql = "select v from Video v order by v.views asc";
        return execute(entityManager -> entityManager.createQuery(jpql).getResultList());
    }


}
