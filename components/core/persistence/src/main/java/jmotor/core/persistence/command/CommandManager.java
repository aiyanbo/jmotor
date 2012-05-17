package jmotor.core.persistence.command;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Component:
 * Description:
 * Date: 11-10-1
 *
 * @author Andy.Ai
 */
public interface CommandManager {
    ResultSet executeQuery(Connection connection, String sql, Object... parameters) throws SQLException;

    int executeUpdate(Connection connection, String sql, Object... parameters) throws SQLException;

    ResultSetMetaData getResultSetMetaData(Connection connection, String sql, Object... parameters) throws SQLException;

    ParameterMetaData getParameterMetaData(Connection connection, String sql, Object... parameters) throws SQLException;
}
