package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import util.EntityManagerOperation;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDao<E> {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    private final Class<E> type;

    static {
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("asmjava4");
    }

    public GenericDao() {
        this.type = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public E create(E entity) {
        return excuteInTransaction(entityManager -> {
            entityManager.persist(entity);
            entityManager.flush();
            entityManager.refresh(entity);
            return entity;
        });
    }

    public E update(E entity) {
        return excuteInTransaction(entityManager -> {
            E managedEntity = entityManager.merge(entity);
            entityManager.flush();
            entityManager.refresh(managedEntity);
            return managedEntity;
        });
    }

    public Boolean deleteById(Object id) {
        return Boolean.logicalOr(excuteInTransaction(entityManager -> {
            E entity = entityManager.find(type, id);
            entityManager.remove(entity);
            return true;
        }), Boolean.FALSE);

    }


    public E findById(Object id) {
        return execute(entityManager -> {
            E entity = entityManager.find(type, id);
            if (entity != null) {
                entityManager.refresh(entity);
            }
            return entity;
        });
    }

    public List<E> findAll() {
        String query = "select e from " + type.getName() + " e";
        return execute(entityManager -> entityManager.createQuery(query).getResultList());
    }

    public E findOneThingByJpql(String jpql, Object... params) {
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql, type);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            List<E> list = query.getResultList();
            if (list.size() > 0) {
                return list.get(0);
            }
            return null;
        });
    }

    public List<E> findManyThingByJpql(String jpql, Object... params) {
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql, type);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return query.getResultList();
        });
    }

    public int count() {
        String jpql = "select count(*) from " + type.getName() + " e";
        return Integer.parseInt(execute(entityManager -> String.valueOf(entityManager.createQuery(jpql, Long.class).getSingleResult())));
    }

    public List<E> paginate(Class<E> type, int page, int size) {
        String jpql = "select e from " + type.getName() + " e";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        });
    }

    protected <R> R execute(EntityManagerOperation<R> operation) {
        try (EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager()) {
            return operation.apply(entityManager);
        }
    }

    protected <R> R excuteInTransaction(EntityManagerOperation<R> operation) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            R result = operation.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                System.out.println("Rolling back transaction");
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
