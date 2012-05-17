package jmotor.core.log;

import junit.framework.TestCase;

/**
 * Component:
 * Description:
 * Date: 11-12-15
 *
 * @author Andy.Ai
 */
public class AppTest extends TestCase {
    private static Logger logger = LoggerManager.getLogger(AppTest.class);

    public void testLogger() {
        logger.info("pppp");
    }
}
