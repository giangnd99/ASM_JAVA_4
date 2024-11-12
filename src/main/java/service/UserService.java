package service;

import entity.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User update(User user);

    boolean delete(int id);

    User findById(int id);

    List<User> listAll();

    User findByEmail(String email);

    boolean isExistEmail(String email);
}
