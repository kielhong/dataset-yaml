package fly.jpa;

import fly.loader.Persister;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
public class JPAPersister implements Persister {
    private final EntityManager entityManager;
    private boolean mergeEntities;

    public JPAPersister(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public JPAPersister(EntityManager entityManager, boolean mergeEntities) {
        this.entityManager = entityManager;
        this.mergeEntities = mergeEntities;
    }

    @Override
    public void persisit(Object entity) {
        if (entity.getClass().isAnnotationPresent(Entity.class)) {
            entityManager.getTransaction().begin();
            if (mergeEntities) {
                entityManager.merge(entity);
            } else {
                entityManager.persist(entity);
            }
            entityManager.getTransaction().commit();
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
