package fly.annotation;

import fly.jpa.JPAFlyBuilder;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 15.
 */
public class FlyDataSetLoader {
    EntityManager entityManager;
    String packageName = null;
    String fileName = null;

    public FlyDataSetLoader(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void loadDataSet(Class c) throws FileNotFoundException {
        getFileName(c);
        loadFlyDataSet();
    }

    private void getFileName(Class c) {
        Annotation annotation = c.getAnnotation(Fly.class);
        if (annotation instanceof Fly) {
            Fly fly = (Fly)annotation;

            if (fly.dataSet().equals(Fly.defaultString)) {
                this.packageName = c.getPackage().getName();
                this.fileName = c.getName().replace(".", "/");
            } else {
                this.fileName = fly.dataSet();
            }
        }
    }

    private void loadFlyDataSet() {
        new JPAFlyBuilder()
                .entityManager(this.entityManager)
                .withDefaultPackage(this.packageName)
                .withFileName(this.fileName)
                .build()
                .load();
    }
}
