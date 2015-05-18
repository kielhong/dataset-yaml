package dsy.dataset;

import org.dbunit.dataset.*;
import org.dbunit.dataset.datatype.DataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
public class YamlTable implements ITable {
    String name;
    List<Map> data;
    ITableMetaData metaData;

    public YamlTable(String name, List<String> columnNames) {
        this.name = name;
        this.data = new ArrayList<Map>();
        metaData = createMetaData(name, columnNames);
    }

    ITableMetaData createMetaData(String name, List<String> columnNames) {
        Column[] columns = null;
        if (columnNames != null) {
            columns = new Column[columnNames.size()];
            for (int i = 0; i < columnNames.size(); i++) {
                columns[i] = new Column(columnNames.get(i), DataType.UNKNOWN);
            }
        }
        return new DefaultTableMetaData(name, columns);
    }

    @Override
    public ITableMetaData getTableMetaData() {
        return metaData;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValue(int row, String column) throws DataSetException {
        if (data.size() <= row) {
            throw new RowOutOfBoundsException(String.valueOf(row));
        }

        // TODO : column.toUpperCase() ?
        return data.get(row).get(column);
    }

    public void addRow(Map values) {
        data.add(convertMap(values));
    }

    private Map convertMap(Map<String, Object> values) {
        Map ret = new HashMap();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            ret.put(entry.getKey(), entry.getValue());
        }
        return ret;
    }
}
