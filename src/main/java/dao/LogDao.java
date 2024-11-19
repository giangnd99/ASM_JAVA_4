package dao;

import entity.Log;

import java.util.List;

public interface LogDao {

    Log findById(int id);

    Log save(Log log);

    Log update(Log log);

    boolean delete(Log log);

    List<Log> findAll();

    Log findByUserName(String name);

    Log updateVisitor(Log log, Integer count);

}
