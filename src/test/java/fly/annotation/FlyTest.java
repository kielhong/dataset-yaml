package fly.annotation;

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
public class FlyTest {
    @Fly(value = "class")
    public class TestAnnotatedObject {
        @Fly(value = "string")
        public void test() {

        }
        @Fly
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
                .filter(m -> m.isAnnotationPresent(Fly.class))
                .count();

        Assert.assertEquals(count, 2);
    }

    @Test
    public void testMethodValue() {
        Arrays.stream(tao.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Fly.class))
                .filter(m -> m.getName().equals("test"))
                .forEach(m -> {
                    Fly ds = m.getAnnotation(Fly.class);
                    Assert.assertEquals(ds.value(), "string");
                });
    }

    @Test
    public void testDefaultValue() {
        for (Method m : tao.getClass().getMethods()) {
            if (m.isAnnotationPresent(Fly.class)) {
                Fly ds = m.getAnnotation(Fly.class);

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
            if (annotation instanceof Fly) {
                Fly ds = (Fly)annotation;

                Assert.assertEquals("class", ds.value());
            }
        }
    }
}
