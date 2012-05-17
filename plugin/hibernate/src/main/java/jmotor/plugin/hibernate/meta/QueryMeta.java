package jmotor.plugin.hibernate.meta;

/**
 * Component:
 * Description:
 * Date: 12-3-20
 *
 * @author Andy.Ai
 */
public interface QueryMeta {
    String getStatement();

    Object[] getParameters();

    Integer getCurrentPage();

    Integer getPageSize();
}
