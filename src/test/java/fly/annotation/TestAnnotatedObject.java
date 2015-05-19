package fly.annotation;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 15.
 */
@Fly
public class TestAnnotatedObject {
    @Fly(value = "string")
    public void test() {

    }
    @Fly
    public void defaultTest() {

    }
}
