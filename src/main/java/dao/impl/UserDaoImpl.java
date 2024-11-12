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

    public List<User> paginate(int page, int size) {
        return super.paginate(User.class, page, size);
    }

    public long countWithFilter(String filter) {
        String jpql = "SELECT COUNT(u) FROM User u";
        jpql = jpql + " WHERE u.email LIKE :email AND u.admin = :role";
        String finalJpql = jpql;
        return (long) super.execute(entityManager -> {
            Query query = entityManager.createQuery(finalJpql);
            query.setParameter("email", "%" + filter + "%");
            query.setParameter("role", false);
            return (long) query.getSingleResult();
        });
    }

    public List<User> paginateByEmailAndRole(String email, int pageNumber, int pageSize) {
        String jpql = "SELECT o FROM User o WHERE o.email LIKE :search AND o.admin = :role";
        return super.execute(entityManager -> {
            Query query = entityManager.createQuery(jpql, User.class);
            query.setParameter("search", "%" + email + "%");
            query.setParameter("role", false);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        });
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
}
