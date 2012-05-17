package jmotor.plugin.hibernate.meta;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 12-3-20
 *
 * @author Andy.Ai
 */
public class SqlQueryMeta implements QueryMeta, Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String statement;
    private Object[] parameters;
    private Integer currentPage;
    private Integer pageSize;
    private Class<?> mappingClass;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object... parameters) {
        this.parameters = parameters;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Class<?> getMappingClass() {
        return mappingClass;
    }

    public void setMappingClass(Class<?> mappingClass) {
        this.mappingClass = mappingClass;
    }
}
