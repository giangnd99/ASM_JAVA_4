package dao.impl;

import dao.GenericDao;
import dao.UserDao;
import entity.User;
import jakarta.persistence.Query;

import java.util.List;

public class UserDaoImpl extends GenericDao<User> implements UserDao {

    public UserDaoImpl() {
        super();
    }

    @Override
    public User get(Object id) {
        return super.findById(id);
    }

    @Override
    public boolean delete(Object id) {
        return super.deleteById(id);
    }

    @Override
    public List<User> listAll() {
        return super.findAll();
    }

    @Override
    public int count() {
        return super.count();
    }

    @Override
    public User findByFavoriteId(Object userId) {
        String jpql = "select f.userId from Favorite f where f.userId = :userId";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter("userId", userId);
            return (User) query.getSingleResult();
        });
    }

    @Override
    public List<User> listUserUnlike() {
        String jpql = "select u from User u join fetch u.id where u.id is null";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            return (List<User>) query.getResultList();
        });
    }

    @Override
    public List<User> listUserLike(Object videoId) {
        String jpql = "select f.userId from Favorite f where f.videoId = :videoId";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter("videoId", videoId);
            return (List<User>) query.getResultList();
        });
    }

    public List<User> paginate(int page, int size) {
        return super.paginate(User.class, page, size);
    }

    @Override
    public User findByEmail(String email) {
        String jpql = "SELECT o FROM User o WHERE o.email = :email";
        return super.execute(entityManager -> {
            Query query = entityManager.createQuery(jpql, User.class);
            query.setParameter("email", email);
            if (query.getResultList().size() > 0) {
                return (User) query.getSingleResult();
            }
            return null;
        });
    }

    @Override
    public List<User> listUserShare(Object videoId) {
        String jpql = "SELECT o FROM Share o WHERE o.videoId.id = :videoId";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql, User.class);
            query.setParameter("videoId", videoId);
            return (List<User>) query.getResultList();
        });
    }

    @Override
    public User findByShareId(Object videoId) {
        String jpql = "SELECT o FROM Share o WHERE o.videoId.id = :videoId";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql, User.class);
            query.setParameter("videoId", videoId);
            if (query.getResultList().size() > 0) {
                return (User) query.getSingleResult();
            }
            return null;
        });
    }

    /**
     * Kiểm tra tồn tại của user theo email cung cấp
     *
     * @param email là email được lấy từ web
     */
    @Override
    public Boolean isExists(Object email) {
        try {
            User userExist = findByEmail(email.toString());
            return userExist != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User findByUsername(String username) {
        String jpql = "select u from User u where u.username = ?1";
        return execute(entityManager ->
        {
            Query query = entityManager.createQuery(jpql);
            query.setParameter(1, username);
            List<User> users = query.getResultList();
            if (users.size() > 0) {
                return users.get(0);
            }
            return null;
        });
    }

    @Override
    public User getUserByUserNameOrEmail(String key) {
        String jpql = "select u from User u where u.username = ?1 or u.email = ?2";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setParameter(1, key);
            query.setParameter(2, key);
            List<User> users = (List<User>) query.getResultList();
            if (users.size() > 0) {
                return users.get(0);
            }
            return null;
        });
    }

    public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.getUserByUserNameOrEmail("nguyenvana@fpt.edu.vn");
        System.out.println(user);
    }
}
