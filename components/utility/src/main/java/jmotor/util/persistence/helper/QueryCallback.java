package jmotor.util.persistence.helper;

import java.sql.ResultSet;

/**
 * Component:
 * Description:
 * Date: 13-5-10
 *
 * @author Andy Ai
 */
public interface QueryCallback<T> {
    T mappingRow(ResultSet resultSet);
}
