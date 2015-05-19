package fly.loader;

import fly.TestApplication;
import fly.jpa.JPAFlyBuilder;
import fly.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@Transactional
public class EmployeeTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    EmployeeRepository employeeRepository;

    Fly fixture;

    @Before
    public void setup() {
        new JPAFlyBuilder(entityManager).withTestClass(this).build().load();
    }

    @Test
    public void testEmployeeLoad() {
        Employee employee = employeeRepository.findOne(1);

        Assert.assertTrue(employeeRepository.count() > 0);
        Assert.assertEquals(employee.getName(), "Fido");
    }
}
