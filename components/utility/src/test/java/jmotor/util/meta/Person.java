package jmotor.util.meta;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 11-8-16
 * Time: 上午10:39
 *
 * @author Andy.Ai
 */
public class Person implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
