package util;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface EntityManagerOperation<R> {
    R apply(EntityManager entityManager);
}
