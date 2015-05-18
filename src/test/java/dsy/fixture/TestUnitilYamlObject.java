package dsy.fixture;

import dsy.config.TestApplication;
import dsy.datasetfactory.YamlDataSetFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.util.SpringUnitilsAdaptorTestExecutionListener;

import javax.sql.DataSource;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@DataSet(factory = YamlDataSetFactory.class)
public class TestUnitilYamlObject extends UnitilsJUnit4 {
    @SpringBean("dataSource")
    DataSource dataSource;

    @Before
    public void setUp() {

    }

    @Test
    @DataSet(factory = YamlDataSetFactory.class)
    public void testYamlDataSet() {
        System.out.println("yamlDataSet");
    }
}
