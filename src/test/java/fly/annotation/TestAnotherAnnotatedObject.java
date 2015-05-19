package fly.annotation;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 15.
 */
@Fly(value = "CustomClassValue.yaml")
public class TestAnotherAnnotatedObject {
    @Fly(value = "CustomValue.custom.yaml")
    public void custom() {

    }
    @Fly
    public void defaultTest() {

    }
}
