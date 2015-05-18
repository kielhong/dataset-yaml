package dsy.fixture;

import dsy.annotation.DataSet;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 15.
 */
@DataSet(value = "CustomClassValue.yaml")
public class TestAnotherAnnotatedObject {
    @DataSet(value = "CustomValue.custom.yaml")
    public void custom() {

    }
    @DataSet
    public void defaultTest() {

    }
}
