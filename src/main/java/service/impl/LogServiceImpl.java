package service.impl;

import dao.LogDao;
import dao.impl.LogDaoImpl;
import entity.Log;
import service.LogService;

import java.util.List;


public class LogServiceImpl implements LogService {
    private LogDao dao;


    public LogServiceImpl() {
        dao = new LogDaoImpl();
    }

    @Override
    public Log getById(int id) {
        return dao.findById(id);
    }

    @Override
    public Log add(Log log) {
        return dao.save(log);
    }

    @Override
    public Log update(Log log) {
        return dao.update(log);
    }

    @Override
    public List<Log> getAll() {
        return dao.findAll();
    }

    @Override
    public Log updateVisitor(Integer visitors) {
        Log log = findByUsername("Admin");
        return dao.updateVisitor(log, visitors);
    }

    @Override
    public Log findByUsername(String username) {
        return dao.findByUserName(username);
    }






}
