package jmotor.util;

import jmotor.util.meta.Person;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 12-3-8
 *
 * @author Andy.Ai
 */
public class CollectionUtilsTest extends TestCase {
    public void testFilter() {
        List list = new ArrayList();
        list.add(new Person());
        list.add(new Person());
        list.add(1);
        list.add(1);
        list.add(new Person());
        list.add(new Person());
        list.add(new Person());
        list.add(1);
        list.add(new Person());
        list.add(new Person());
        list.add(new Person());
        list.add(1);
        list.add(new Person());
        list.add(new Person());
        list.add(new Person());
        list.add(new Person());
        System.out.println(list.size());
        CollectionUtils.filter(list, new Validator() {
            @Override
            public boolean validate(Object object) {
                return object instanceof Person;
            }
        });
        System.out.println(list.size());
    }
}
