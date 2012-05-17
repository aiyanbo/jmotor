package jmotor.plugin.hibernate.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 12-3-20
 *
 * @author Andy.Ai
 */
public class PaginationDto implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPage;
    private Collection data;

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

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Collection getData() {
        return data;
    }

    public void setData(Collection data) {
        this.data = data;
    }
}
