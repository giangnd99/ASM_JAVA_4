package dao.impl;

import dao.GenericDao;
import dao.LogDao;
import entity.Log;
import jakarta.persistence.Query;

public class LogDaoImpl extends GenericDao<Log> implements LogDao {

    @Override
    public Log findById(int id) {
        return null;
    }

    @Override
    public Log save(Log log) {
        return super.create(log);
    }

    @Override
    public boolean delete(Log log) {
        return super.deleteById(log.getId());
    }

    @Override
    public Log findByUserName(String userName) {
        String jpql = "select l from Log l where l.username = :userName";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter("userName", userName);
            return !query.getResultList().isEmpty() ? (Log) query.getSingleResult() : null;
        });
    }

    @Override
    public Log updateVisitor(Log log, Integer count) {
        log.setVisitors(count);
        return update(log);
    }
}
