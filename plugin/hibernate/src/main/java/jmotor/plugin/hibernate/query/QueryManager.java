package jmotor.plugin.hibernate.query;

import jmotor.plugin.hibernate.dto.PaginationDto;
import jmotor.plugin.hibernate.meta.QueryMeta;

import java.util.List;

/**
 * Component:Hibernate-plugin
 * Description:Query manager
 * Date: 12-3-20
 *
 * @author Andy.Ai
 */
public interface QueryManager {
    PaginationDto hqlQueryPagination(QueryMeta query);

    PaginationDto sqlQueryPagination(QueryMeta query);

    <T> List<T> hqlQuery(QueryMeta query);

    <T> List<T> sqlQuery(QueryMeta query);
}
