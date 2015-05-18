package dsy;

import dsy.annotation.DataSet;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 15.
 */
public class DataSetLoader {
    private final List<String> fixtures = new ArrayList<>();

    public void construct(Object object) {
        Class c = object.getClass();

        Annotation annotation = c.getAnnotation(DataSet.class);
        if (annotation instanceof DataSet) {
            DataSet ds = (DataSet)annotation;

            System.out.println("class.getName(): " + c.getName());
            System.out.println("ds.value(): " + ds.value());
        }
    }

    public Object loadDataSet(Object object) throws FileNotFoundException {
        Yaml yaml = new Yaml();

        return yaml.load(object.getClass().getResourceAsStream(getClassBaseDataSetName(object)));
    }

    public String getClassBaseDataSetName(Object object) {
        String dataSetName = "";

        Class c = object.getClass();

        Annotation annotation = c.getAnnotation(DataSet.class);
        if (annotation instanceof DataSet) {
            DataSet ds = (DataSet)annotation;

            if (ds.value().equals(DataSet.defaultString)) {
                dataSetName = c.getSimpleName() + "." + getDataSetFileExtension();
            } else {
                dataSetName = ds.value();
            }
        }

        return dataSetName;
    }

    /**
     * @see : http://www.yaml.org/faq.html
     * @return The extension that files which can be interpreted by this factory must have
     */
    public String getDataSetFileExtension() {
        return "yaml";
    }
}
