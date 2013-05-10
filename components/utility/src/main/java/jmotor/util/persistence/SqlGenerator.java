package jmotor.util.persistence;

import jmotor.util.persistence.dto.SqlStatement;
import jmotor.util.persistence.parser.EntityParserCallback;

/**
 * Component:
 * Description:
 * Date: 12-11-6
 *
 * @author Andy.Ai
 */
public interface SqlGenerator {
    SqlStatement generateInsertSql(Class<?> entityClass);

    SqlStatement generateInsertSql(Class<?> entityClass, EntityParserCallback callback);

    SqlStatement generateUpdateSql(Class<?> entityClass);

    SqlStatement generateUpdateSql(Class<?> entityClass, EntityParserCallback callback);

    SqlStatement generateDeleteSql(Class<?> entityClass);

    SqlStatement generateDeleteSql(Class<?> entityClass, EntityParserCallback callback);
}
