package fly.loader;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
@Entity
public class Employee {
    @Id
    @GeneratedValue
    Integer id;

    String name;

    String address;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
