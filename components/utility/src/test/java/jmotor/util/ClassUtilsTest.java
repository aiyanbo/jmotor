package jmotor.util;

import jmotor.util.interfaces.SupperInterface;
import jmotor.util.interfaces.impl.ResourceAdapter;
import jmotor.util.meta.AbstractClass;
import jmotor.util.meta.Student;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static java.lang.System.out;

/**
 * Component:
 * Description:
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public class ClassUtilsTest extends TestCase {
    public void test() {
        testGetClassLoader();
        testGetClass();
        testGetSupperClass();
    }

    @Test
    public void testGetClassLoader() {
        out.println(ClassUtilsTest.class.getClassLoader());
        out.println(ClassUtils.getClassLoader());
    }

    @Test
    public void testGetClass() {
        Student student = new Student();
        out.println(ObjectUtils.getClass(student));
        out.println(AbstractClass.class.getName());
    }

    @Test
    public void testGetSupperClass() {
        Class<?>[] supperClasses = ClassUtils.getSuperClasses(Student.class);
        Class<?>[] interfaces = ClassUtils.getInterfaces(ResourceAdapter.class);
        out.println(supperClasses.length + "\t" + interfaces.length);
        out.println(ClassUtils.isRelationship(List.class, Collection.class));
        out.println(ClassUtils.isRelationship(ResourceAdapter.class, SupperInterface.class));
        out.println(ClassUtils.isRelationship(ResourceAdapter.class, Collection.class));
    }
}
