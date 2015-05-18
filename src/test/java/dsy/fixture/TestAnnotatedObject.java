package dsy.fixture;

import dsy.annotation.DataSet;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 15.
 */
@DataSet
public class TestAnnotatedObject {
    @DataSet(value = "string")
    public void test() {

    }
    @DataSet
    public void defaultTest() {

    }
}
