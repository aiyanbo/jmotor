package jmotor.core.persistence;

import jmotor.core.persistence.exception.PersistenceException;
import jmotor.core.persistence.meta.QueryMeta;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 11-9-22
 *
 * @author Andy.Ai
 */
public interface Session {
    Serializable save(Object object) throws PersistenceException;

    void delete(Object object) throws PersistenceException;

    void update(Object object) throws PersistenceException;

    void merge(Object object) throws PersistenceException;

    void saveOrUpdate(Object object) throws PersistenceException;

    void executeUpdate(QueryMeta query) throws PersistenceException;

    void executeBatchUpdate(List<QueryMeta> queries) throws PersistenceException;

    List<Object> executeQuery(QueryMeta query) throws PersistenceException;

    Object uniqueQuery(QueryMeta query) throws PersistenceException;

    Object get(Object pk);

    Object load(Object pk);

    Connection getConnection();

    void close();
}
