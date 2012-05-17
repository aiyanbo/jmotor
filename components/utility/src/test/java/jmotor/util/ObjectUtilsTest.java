package jmotor.util;

import jmotor.util.meta.Student;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

/**
 * Component:
 * Description:
 * Date: 11-8-25
 *
 * @author Andy.Ai
 */
public class ObjectUtilsTest extends TestCase {
    @Test
    public void testInvoke() {
        Student student = new Student();
        ObjectUtils.invoke(student, "setStuId", "0002");
        ObjectUtils.invoke(student, "show", "0002", "Dog");
        System.out.println(student.getStuId());
    }

    @Test
    public void testSerializable() throws IOException, ClassNotFoundException {
        Student student = new Student();
        student.setStuId("00001");
        student.setName("Jobs");
        student.setClassName("IT");
        String fileName = ObjectUtils.writeObject(student);
        Student readObject = ObjectUtils.readObject(fileName);
        System.out.println(readObject);
    }
}
