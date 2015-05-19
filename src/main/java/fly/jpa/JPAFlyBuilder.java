package fly.jpa;

import fly.loader.FlyImpl;
import fly.loader.Fly;
import org.yaml.snakeyaml.introspector.BeanAccess;

import javax.persistence.EntityManager;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
public class JPAFlyBuilder {
    private EntityManager entityManager;
    private boolean mergeEntities;
    private String defaultPackage;
    private String file;
    private BeanAccess beanAccess = BeanAccess.DEFAULT;

    public JPAFlyBuilder() {
    }

    public JPAFlyBuilder entityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        return this;
    }

    public JPAFlyBuilder withDefaultPackage(String defaultPackage) {
        this.defaultPackage = defaultPackage;
        return this;
    }

    public JPAFlyBuilder withFileName(String fileName) {
        this.file = fileName.replace(".", "/") + ".yaml";
        return this;
    }

    public JPAFlyBuilder mergeEntities() {
        this.mergeEntities = true;
        return this;
    }

    public JPAFlyBuilder useFieldAccess() {
        this.beanAccess = BeanAccess.FIELD;
        return this;
    }

    public JPAFlyBuilder withTestClass(Object object) {
        this.defaultPackage = object.getClass().getPackage().getName();
        this.file = object.getClass().getName().replace(".", "/") + ".yaml";
        return this;
    }

    public Fly build() {
        return new FlyImpl(
                new JPAPersister(entityManager, mergeEntities),
                defaultPackage,
                file,
                beanAccess);
    }
}
