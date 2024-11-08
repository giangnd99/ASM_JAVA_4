package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    User create(User t);

    User update(User t);

    User get(Object id);

    boolean delete(Object id);

    List<User> listAll();

    List<User> paginate(int pageNumber, int pageSize);

    long count();

    User findByEmail(String email);

    Boolean isExists(Object email);
}
