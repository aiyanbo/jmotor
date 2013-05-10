package jmotor.util.persistence.helper;

import jmotor.util.persistence.dto.DataSources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 13-5-10
 *
 * @author Andy Ai
 */
public class PersistenceHelper {
    public static Connection getConnection(DataSources dataSources) throws SQLException {
        try {
            Class.forName(dataSources.getDriverClass());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getLocalizedMessage(), e);
        }
        return DriverManager.getConnection(
                dataSources.getConnector(),
                dataSources.getUsername(),
                dataSources.getPassword());
    }

    public static int executeUpdate(Connection connection, String sql, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            setParameters(preparedStatement, parameters);
            return preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

    }

    public static <T> List<T> executeQuery(Connection connection, QueryCallback<T> callback,
                                           String sql, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            setParameters(preparedStatement, parameters);
            resultSet = preparedStatement.executeQuery();
            int fetchSize = resultSet.getFetchSize();
            List<T> result = new ArrayList<T>(fetchSize > 5 ? fetchSize : 5);
            while (resultSet.next()) {
                result.add(callback.mappingRow(resultSet));
            }
            return result;
        } finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    public static void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        if (null == parameters || parameters.length < 1) {
            return;
        }
        int i = 1;
        for (Object parameter : parameters) {
            if (parameter == null) {
                statement.setNull(i++, Types.VARCHAR);
            } else {
                statement.setObject(i++, parameter);
            }
        }
    }
}
