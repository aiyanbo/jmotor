package jmotor.core.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Component:
 * Description:
 * Date: 12-4-14
 *
 * @author Andy.Ai
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {
    long liveSeconds() default 120;

    int maxElementsInMemory() default 600;

    boolean overflowToDisk() default true;

    int maxElementsInDisk() default 600;
}
