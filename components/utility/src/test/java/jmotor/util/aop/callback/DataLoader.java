package jmotor.util.aop.callback;

import jmotor.util.ClassUtils;
import jmotor.util.aop.meta.StudentMeta;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 12-2-15
 *
 * @author Andy.Ai
 */
public class DataLoader implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        if (!Modifier.isAbstract(method.getModifiers())) {
            try {
                if (ClassUtils.isRelationship(method.getReturnType(), Collection.class) && method.getName().startsWith("get")) {
                    Method setter = ClassUtils.getMethod(o.getClass(), "s" + method.getName().substring(1), method.getReturnType());
                    if (setter != null) {
                        StudentMeta student = new StudentMeta();
                        student.setStuId("000001");
                        student.setStuName("Jobs");
                        List<StudentMeta> students = new ArrayList<StudentMeta>();
                        students.add(student);
                        setter.invoke(o, students);
                    }
                }
            } finally {
                result = methodProxy.invokeSuper(o, objects);
            }
        }
        return result;
    }
}
