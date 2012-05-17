package jmotor.util.aop.meta;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 12-2-15
 *
 * @author Andy.Ai
 */
public class StudentMeta implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String stuId;
    private String stuName;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
}
