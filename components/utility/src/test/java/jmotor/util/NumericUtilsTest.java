package jmotor.util;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Component:
 * Description:
 * Date: 11-11-10
 *
 * @author Andy.Ai
 */
public class NumericUtilsTest extends TestCase {
    @Test
    public void test() {
        int random = NumericUtils.randomInteger("[-666,789)");
        System.out.println(random);
    }
}
