package dsy.annotation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 15.
 */
public class DataSetTest {
    @DataSet(value = "class")
    public class TestAnnotatedObject {
        @DataSet(value = "string")
        public void test() {

        }
        @DataSet
        public void defaultTest() {

        }
    }

    private TestAnnotatedObject tao;

    @Before
    public void setUp() {
        tao = new TestAnnotatedObject();
    }

    @Test
    public void testAnnotatedMethodCount() {
        long count = Arrays.stream(tao.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(DataSet.class))
                .count();

        Assert.assertEquals(count, 2);
    }

    @Test
    public void testMethodValue() {
        Arrays.stream(tao.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(DataSet.class))
                .filter(m -> m.getName().equals("test"))
                .forEach(m -> {
                    DataSet ds = m.getAnnotation(DataSet.class);
                    Assert.assertEquals(ds.value(), "string");
                });
    }

    @Test
    public void testDefaultValue() {
        for (Method m : tao.getClass().getMethods()) {
            if (m.isAnnotationPresent(DataSet.class)) {
                DataSet ds = m.getAnnotation(DataSet.class);

                if (m.getName().equals("defaultTest")) {
                    Assert.assertEquals(ds.value(), "");
                }
            }
        }
    }

    @Test
    public void testClassValue() {
        Class c = tao.getClass();

        for (Annotation annotation : c.getAnnotations()) {
            if (annotation instanceof DataSet) {
                DataSet ds = (DataSet)annotation;

                Assert.assertEquals("class", ds.value());
            }
        }
    }
}
