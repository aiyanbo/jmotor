package jmotor.util.aop.meta;

import java.io.Serializable;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 12-2-15
 *
 * @author Andy.Ai
 */
public class ClassMeta implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String classId;
    private String className;
    private List<StudentMeta> students;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<StudentMeta> getStudents() {
        return students;
    }

    public void setStudents(List<StudentMeta> students) {
        this.students = students;
    }
}
