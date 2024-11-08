package service;

import entity.User;

import java.util.List;

public interface UserService {

    public void save(User user);

    public void update(User user);

    public void delete(int id);

    public User findById(int id);

    public List<User> listAll();
}
