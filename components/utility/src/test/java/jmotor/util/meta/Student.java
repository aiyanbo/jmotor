package jmotor.util.meta;

/**
 * Component:
 * Description:
 * Date: 11-8-16
 * Time: 上午10:40
 *
 * @author Andy.Ai
 */
public class Student extends Person {
    private String stuId;
    private String className;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void show(String stuId, String name) {
        System.out.println(stuId + ";" + name);
    }
}
