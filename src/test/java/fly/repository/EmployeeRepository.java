package fly.repository;

import fly.loader.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
