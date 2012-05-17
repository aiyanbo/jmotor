package jmotor.util.aop.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * Component:Utility
 * Description:CGLib utilities
 * Date: 12-2-15
 *
 * @author Andy.Ai
 */
public class CGLibUtils {
    private CGLibUtils() {
    }

    public static Class<?> createProxyClass(Class clazz, Callback callback) {
        return createProxyClass(clazz, callback);
    }

    public static Class<?> createProxyClass(Class clazz, Callback callback, CallbackFilter callbackFilter) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        if (callbackFilter != null) {
            enhancer.setCallbackTypes(new Class<?>[]{callback.getClass(), NoOp.class});
            enhancer.setCallbacks(new Callback[]{callback, NoOp.INSTANCE});
            enhancer.setCallbackFilter(callbackFilter);
        } else {
            enhancer.setCallbackType(callback.getClass());
            enhancer.setCallback(callback);
        }
        return enhancer.createClass();
    }

    public static <T> T createProxyObject(Class clazz, Callback callback) {
        return createProxyObject(clazz, callback, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createProxyObject(Class clazz, Callback callback, CallbackFilter callbackFilter) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        if (callbackFilter != null) {
            enhancer.setCallbackTypes(new Class<?>[]{callback.getClass(), NoOp.class});
            enhancer.setCallbacks(new Callback[]{callback, NoOp.INSTANCE});
            enhancer.setCallbackFilter(callbackFilter);
        } else {
            enhancer.setCallbackType(callback.getClass());
            enhancer.setCallback(callback);
        }
        return (T) enhancer.create();
    }
}
