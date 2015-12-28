package net.leludo.tuto.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DefaultDao<T, PK> {
    protected EntityManager em;
    private Class<T> entityClass;

    public DefaultDao(final Class<T> entity) {
        entityClass = entity;
    }

    public void close() {
        if (em != null) {
            em.close();
        }
    }

    public void setEntityManager(final EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    public T find(final PK id) {
        T entity = em.find(entityClass, id);
        return entity;
    }

}
