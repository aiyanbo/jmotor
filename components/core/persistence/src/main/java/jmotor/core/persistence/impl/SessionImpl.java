package jmotor.core.persistence.impl;

import jmotor.core.persistence.Session;
import jmotor.core.persistence.connection.ConnectionManager;
import jmotor.core.persistence.exception.PersistenceException;
import jmotor.core.persistence.meta.QueryMeta;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 11-9-25
 *
 * @author Andy.Ai
 */
public class SessionImpl implements Session {
    private ConnectionManager connectionManager;

    @Override
    public Serializable save(Object object) throws PersistenceException {
        return null;
    }

    @Override
    public void delete(Object object) throws PersistenceException {

    }

    @Override
    public void update(Object object) throws PersistenceException {

    }

    @Override
    public void merge(Object object) throws PersistenceException {

    }

    @Override
    public void saveOrUpdate(Object object) throws PersistenceException {

    }

    @Override
    public void executeUpdate(QueryMeta query) throws PersistenceException {

    }

    @Override
    public void executeBatchUpdate(List<QueryMeta> queries) throws PersistenceException {

    }

    @Override
    public List<Object> executeQuery(QueryMeta query) throws PersistenceException {
        return null;
    }

    @Override
    public Object uniqueQuery(QueryMeta query) throws PersistenceException {
        return null;
    }

    @Override
    public Object get(Object pk) {
        return null;
    }

    @Override
    public Object load(Object pk) {
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void close() {

    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
}
