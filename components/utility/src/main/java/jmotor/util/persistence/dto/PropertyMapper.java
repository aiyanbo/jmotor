package jmotor.util.persistence.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 12-11-6
 *
 * @author Andy.Ai
 */
public class PropertyMapper {
    private Map<String, String> entries = new HashMap<String, String>(10);
    private List<String> insertOrders = new ArrayList<String>(10);

    public void put(String columnName, String propertyName) {
        if (!entries.containsKey(columnName)) {
            insertOrders.add(columnName);
        }
        entries.put(columnName, propertyName);
    }

    public List<String> keyList() {
        return insertOrders;
    }

    public String[] keyArray() {
        return insertOrders.toArray(new String[insertOrders.size()]);
    }

    public List<String> valueList() {
        List<String> values = new ArrayList<String>(insertOrders.size());
        for (String key : insertOrders) {
            values.add(entries.get(key));
        }
        return values;
    }

    public String[] valueArray() {
        String[] values = new String[insertOrders.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = entries.get(insertOrders.get(i));
        }
        return values;
    }

    public String get(String key) {
        return entries.get(key);
    }

    public int size() {
        return insertOrders.size();
    }
}
