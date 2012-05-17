package jmotor.core.persistence.command.impl;

import jmotor.core.persistence.command.CommandManager;
import jmotor.util.CollectionUtils;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;


/**
 * Component:
 * Description:
 * Date: 11-10-1
 *
 * @author Andy.Ai
 */
public class CommandManagerImpl implements CommandManager {

    public ResultSet executeQuery(Connection connection, String sql, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = createPreparedStatement(connection, sql, parameters);
        return preparedStatement.executeQuery();
    }

    public int executeUpdate(Connection connection, String sql, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = createPreparedStatement(connection, sql, parameters);
        return preparedStatement.executeUpdate();
    }

    public ResultSetMetaData getResultSetMetaData(Connection connection, String sql, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = createPreparedStatement(connection, sql, parameters);
        return preparedStatement.getMetaData();
    }

    public ParameterMetaData getParameterMetaData(Connection connection, String sql, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = createPreparedStatement(connection, sql, parameters);
        return preparedStatement.getParameterMetaData();
    }


    private PreparedStatement createPreparedStatement(Connection connection, String sql, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (CollectionUtils.isNotEmpty(parameters)) {
            int index = 1;
            for (Object parameter : parameters) {
                if (parameter == null) {
                    preparedStatement.setNull(index, Types.NULL);
                } else {
                    preparedStatement.setObject(index, parameter);
                }
                index++;
            }
        }
        return preparedStatement;
    }
}
