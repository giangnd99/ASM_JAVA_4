package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.User;
import service.UserService;

import java.util.List;

public class UserServiceImp implements UserService {

    private UserDao userDao;


    public UserServiceImp() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public User save(User user) {
        return userDao.create(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(int id) {
        return userDao.delete(id);
    }

    @Override
    public User findById(int id) {
        return userDao.get(id);
    }

    @Override
    public List<User> listAll() {
        return userDao.listAll();
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean isExistEmail(String email) {
        return userDao.isExists(email);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User finByUsernameOrEmail(String key) {
        return userDao.getUserByUserNameOrEmail(key);
    }


}
