package jmotor.core.persistence.connection.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import jmotor.core.persistence.connection.ConnectionManager;
import jmotor.core.persistence.exception.ConnectionException;
import jmotor.core.persistence.meta.ConnectionMeta;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Component:Persistence
 * Description:Connection provider manager implementation
 * Date: 11-9-25
 *
 * @author Andy.Ai
 */
public class ConnectionManagerImpl implements ConnectionManager {
    private Map<String, DataSource> dataSourceCache = new HashMap<String, DataSource>();

    public Connection connection(ConnectionMeta connection) {
        try {
            ComboPooledDataSource dataSource =
                    (ComboPooledDataSource) dataSourceCache.get(connection.getConnectionName());
            if (dataSource == null) {
                dataSource = new ComboPooledDataSource();
                dataSource.setDataSourceName(connection.getConnectionName());
                dataSource.setDriverClass(connection.getDriverClass());
                dataSource.setJdbcUrl(connection.getJdbcUrl());
                dataSource.setUser(connection.getUsername());
                dataSource.setPassword(connection.getPassword());
                dataSourceCache.put(connection.getConnectionName(), dataSource);
            }
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new ConnectionException(e);
        }
    }

    public Connection connection(String jndiServiceName, String username, String password) {
        try {
            DataSource dataSource = dataSourceCache.get(jndiServiceName);
            if (dataSource == null) {
                InitialContext initialContext = new InitialContext();
                dataSource = (DataSource) initialContext.lookup(jndiServiceName);
                dataSourceCache.put(jndiServiceName, dataSource);
            }
            return dataSource.getConnection(username, password);
        } catch (Exception e) {
            throw new ConnectionException(e);
        }
    }
}
