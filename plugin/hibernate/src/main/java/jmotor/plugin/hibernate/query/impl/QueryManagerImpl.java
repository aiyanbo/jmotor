package jmotor.plugin.hibernate.query.impl;

import jmotor.plugin.hibernate.dto.PaginationDto;
import jmotor.plugin.hibernate.meta.QueryMeta;
import jmotor.plugin.hibernate.meta.SqlQueryMeta;
import jmotor.plugin.hibernate.query.QueryManager;
import jmotor.plugin.hibernate.transformer.SimpleResultTransformer;
import jmotor.util.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Component:Hibernate-plugin
 * Description:Query manager implementation
 * Date: 12-3-20
 *
 * @author Andy.Ai
 */
public class QueryManagerImpl implements QueryManager {
    private SessionFactory sessionFactory;

    @Override
    public PaginationDto hqlQueryPagination(QueryMeta queryMeta) {
        Query query = createQuery(queryMeta);

        return null;
    }

    @Override
    public PaginationDto sqlQueryPagination(QueryMeta query) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> hqlQuery(QueryMeta queryMeta) {
        return createQuery(queryMeta).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> sqlQuery(QueryMeta queryMeta) {
        Query query = createQuery(queryMeta);
        if (queryMeta instanceof SqlQueryMeta) {
            SqlQueryMeta _queryMeta = (SqlQueryMeta) queryMeta;
            if (_queryMeta.getMappingClass() != null) {
                SimpleResultTransformer resultTransformer = new SimpleResultTransformer();
                resultTransformer.setMappingClass(_queryMeta.getMappingClass());
                query.setResultTransformer(resultTransformer);
            }
        }
        return query.list();
    }

    private Query createQuery(QueryMeta queryMeta) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(queryMeta.getStatement());
        if (queryMeta instanceof SqlQueryMeta) {
            query = session.createSQLQuery(queryMeta.getStatement());
        }
        if (CollectionUtils.isNotEmpty(queryMeta.getParameters())) {
            Object[] parameters = queryMeta.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                query.setParameter(i, parameters[i]);
            }
        }
        if (null != queryMeta.getCurrentPage()) {
            query.setFirstResult((queryMeta.getCurrentPage() - 1) * queryMeta.getPageSize());
        }
        if (null != queryMeta.getPageSize()) {
            query.setMaxResults(queryMeta.getPageSize());
        }
        return query;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
