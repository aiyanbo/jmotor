package jmotor.core;

import jmotor.core.cache.Cache;
import jmotor.core.ioc.cache.CacheManager;
import jmotor.core.meta.Student;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Component:
 * Description:
 * Date: 11-12-10
 *
 * @author Andy.Ai
 */
public class CacheManagerTest extends TestCase{
    @Test
    public void testCache(){
        Cache cache = CacheManager.getCache("elementConfigCache2");
        Student student = new Student();
        cache.put("student",student);
        System.out.println(cache);
        System.out.println(cache.get("student"));
    }
}
