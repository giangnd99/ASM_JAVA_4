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

    int count();

    /* Người dùng u like video v */
    User findByFavoriteId(Object favoriteId);

    /* Người dùng u unlike video v */
    List<User> listUserUnlike();

    /* Danh sách các User thích Video ? */
    List<User> listUserLike(Object videoId);

    User findByEmail(String email);
    List<User> listUserShare(Object videoId);

    /* Người dùng u share video v */
    User findByShareId(Object videoId);

    Boolean isExists(Object email);

    User findByUsername(String username);

    User getUserByUserNameOrEmail(String key);
}
