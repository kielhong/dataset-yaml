package dsy.dataset;

import org.dbunit.dataset.*;
import org.dbunit.dataset.datatype.DataType;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
public class YamlDataSet implements IDataSet {
    Map<String, YamlTable> tables = new HashMap<>();

    public YamlDataSet(File file) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Map<String, List<Map>> data = (Map<String, List<Map>>)yaml.load(new FileInputStream(file));

        for (Map.Entry<String, List<Map>> entry : data.entrySet()) {
            createTable(entry.getKey(), entry.getValue());
        }
    }

    YamlTable createTable(String name, List<Map> rows) {
        YamlTable table = new YamlTable(
                name,
                rows.size() > 0 ? new ArrayList(rows.get(0).keySet()) : null);
        for (Map values : rows) {
            table.addRow(values);
        }
        tables.put(name, table);

        return table;
    }

    @Override
    public String[] getTableNames() throws DataSetException {
        return (String[])tables.keySet().toArray(new String[tables.size()]);
    }

    @Override
    public ITableMetaData getTableMetaData(String tableName) throws DataSetException {
        return tables.get(tableName).getTableMetaData();
    }

    @Override
    public ITable getTable(String tableName) throws DataSetException {
        return tables.get(tableName);
    }

    @Override
    public ITable[] getTables() throws DataSetException {
        return (ITable[])tables.values().toArray(new ITable[tables.size()]);
    }

    @Override
    public ITableIterator iterator() throws DataSetException {
        return new DefaultTableIterator(getTables());
    }

    @Override
    public ITableIterator reverseIterator() throws DataSetException {
        return new DefaultTableIterator(getTables(), true);
    }
}
