package dsy.datasetfactory;

import dsy.dataset.YamlDataSet;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.unitils.core.UnitilsException;
import org.unitils.dbunit.datasetfactory.DataSetFactory;
import org.unitils.dbunit.util.MultiSchemaDataSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
public class YamlDataSetFactory implements DataSetFactory {
    protected String defaultSchemaName;

    @Override
    public void init(Properties configuration, String defaultSchemaName) {
        System.out.println("test");
        this.defaultSchemaName = defaultSchemaName;
    }

    @Override
    public MultiSchemaDataSet createDataSet(File... dataSetFiles) {
        Map<String, YamlDataSet> dataSets = new HashMap<>();
        for (File file : dataSetFiles) {
            System.out.println("createDataSet():" + file.getName());
            try {
                YamlDataSet yamlDataSet = new YamlDataSet(file);
                dataSets.put(defaultSchemaName, yamlDataSet);
            } catch (FileNotFoundException e) {
                throw new UnitilsException("Data set file not found.", e);
            }
        }


        MultiSchemaDataSet multiSchemaDataSet = new MultiSchemaDataSet();
        for (String schemaName : dataSets.keySet()) {
            YamlDataSet yamlDataSet = dataSets.get(schemaName);

            // wrap datasets in replacement datasets, and replace [null] tokens by the null reference
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(yamlDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            multiSchemaDataSet.setDataSetForSchema(schemaName, replacementDataSet);
        }
        return multiSchemaDataSet;
    }

    @Override
    public String getDataSetFileExtension() {
        return "yaml";
    }
}
