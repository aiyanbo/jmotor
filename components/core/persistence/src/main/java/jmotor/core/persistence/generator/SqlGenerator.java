package jmotor.core.persistence.generator;

/**
 * Component:
 * Description:
 * Date: 12-4-11
 *
 * @author Andy.Ai
 */
public interface SqlGenerator {
    String generateInsertSql(Object entity);

    String generateDeleteSql(Object entity);

    String generateUpdateSql(Object entity);
}
