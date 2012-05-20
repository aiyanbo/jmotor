package jmotor.core;

import jmotor.core.ioc.BeanBusManager;
import jmotor.core.ioc.context.ApplicationContext;
import jmotor.core.ioc.factory.BeanBusFactory;
import jmotor.core.ioc.pool.BeanConfigurationPool;
import jmotor.core.meta.Person;
import jmotor.core.meta.Student;
import org.junit.Test;

/**
 * Component:
 * Description:
 * Date: 11-8-23
 *
 * @author Andy.Ai
 */
public class AppTest {

    @Test
    public void testIoc() {
        ApplicationContext.debug = true;
        BeanBusManager beanBusManager = BeanBusFactory.createBeanBus("jmotor/core/beanbus.xml");
        BeanConfigurationPool beanConfigurationPool = ApplicationContext.getBeanConfigurationPool();
        for (int i = 0; i < 100; i++) {
            beanConfigurationPool.get("stu");
            beanConfigurationPool.get("person");
            long start = System.currentTimeMillis();
            Student student = beanBusManager.getBean("stu");
            System.out.println(student.toString());
            System.out.println(System.currentTimeMillis() - start + " milliseconds------");
            start = System.currentTimeMillis();
            Person person = beanBusManager.getBean("person");
            System.out.println(person.toString());
            System.out.println(System.currentTimeMillis() - start + " milliseconds------");
        }
    }

    @Test
    public void testMatch() {
        System.out.println("${value}".matches("[$][{].*[}]"));
    }

    @Test
    public void testGetBus() {
        BeanBusFactory.createBeanBus("jmotor/core/beanbus.xml");
        BeanBusManager beanBusManager = BeanBusFactory.getBeanBus();
        Student stu = beanBusManager.getBean("stu");
        System.out.println(stu.toString());
    }
}
