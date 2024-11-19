package service;

import entity.Log;

import java.util.List;

public interface LogService {

    Log getById(int id);

    Log add(Log log);

    Log update(Log log);

    List<Log> getAll();

    Log updateVisitor(Integer visitors);

    Log findByUsername(String username);

}

