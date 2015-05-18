package dsy;

import dsy.annotation.DataSet;
import dsy.fixture.TestAnnotatedObject;
import dsy.fixture.TestAnotherAnnotatedObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 15.
 */
public class DataSetLoaderTest {
    private DataSetLoader dsl = new DataSetLoader();

    private TestAnnotatedObject tao;
    private TestAnotherAnnotatedObject taao;

    @Before
    public void setUp() {
        tao = new TestAnnotatedObject();
        taao = new TestAnotherAnnotatedObject();

    }

    @Test
    public void testClassDataSetNameDefault() {
        Assert.assertEquals("TestAnnotatedObject.yaml", dsl.getClassBaseDataSetName(tao));
    }

    @Test
    public void testClassDataSetNameCustom() {
        Assert.assertEquals("CustomClassValue.yaml", dsl.getClassBaseDataSetName(taao));
    }

    @Test
    public void testYaml() throws Exception {
        Yaml yaml = new Yaml();
        Map map = (Map)yaml.load(tao.getClass().getResourceAsStream(dsl.getClassBaseDataSetName(tao)));

        Assert.assertEquals("ed", map.get("User"));
        Assert.assertEquals("test", map.get("Test Code"));
    }

    /**
     * item:
     - id: 1
     Time: 2001-11-23 15:01:42 -5
     User: ed
     Test Code: test

     * @throws Exception
     */
    @Test
    public void testLoadDefaultClassYaml() throws Exception {
        Object loadData = dsl.loadDataSet(tao);
        Map data = (Map)loadData;

        Assert.assertEquals("ed", data.get("User"));
        Assert.assertEquals("test", data.get("Test Code"));
    }

    @Test
    public void testCustomDefaultClassYaml() throws Exception {
        Object loadData = dsl.loadDataSet(taao);
        Map data = (Map)loadData;

        Assert.assertEquals("igniter", data.get("User"));
        Assert.assertEquals("load test", data.get("Test Code"));
    }
}
