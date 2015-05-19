package fly.annotation;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import javax.persistence.EntityManagerFactory;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 19.
 */
public class FlyTestExecutionListener implements TestExecutionListener {
    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        Class c = testContext.getTestClass();
        EntityManagerFactory emf = (EntityManagerFactory)testContext.getApplicationContext().getBean(EntityManagerFactory.class);

        FlyDataSetLoader loader = new FlyDataSetLoader(emf.createEntityManager());
        loader.loadDataSet(c);
    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {

    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

    }
}
