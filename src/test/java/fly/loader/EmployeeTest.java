package fly.loader;

import fly.TestApplication;
import fly.annotation.Fly;
import fly.annotation.FlyTestExecutionListener;
import fly.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@TestExecutionListeners(
        listeners = {FlyTestExecutionListener.class,
                DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Fly
public class EmployeeTest {
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void testEmployeeDataSetLoad() {
        Employee employee = employeeRepository.findOne(1);

        System.out.println(employeeRepository.count());
        Assert.assertTrue(employeeRepository.count() > 0);
        Assert.assertEquals(employee.getName(), "Fido");
    }
}
