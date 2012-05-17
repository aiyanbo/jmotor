package jmotor.core.persistence.connection;

import jmotor.core.persistence.meta.ConnectionMeta;

import java.sql.Connection;

/**
 * Component:
 * Description:
 * Date: 11-9-25
 *
 * @author Andy.Ai
 */
public interface ConnectionManager {
    Connection connection(ConnectionMeta connection);

    Connection connection(String jndiServiceName, String username, String password);
}
