package jmotor.core.persistence.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 12-4-11
 *
 * @author Andy.Ai
 */
@Table(name = "scheduler", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class SchedulerDomain implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;

    private Long id;
    private String name;
    private String processKey;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @GeneratedValue(generator = "UUID")
    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }
}
