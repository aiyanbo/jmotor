package jmotor.core.ioc.context;

import jmotor.core.ioc.parser.ContextParser;
import jmotor.core.ioc.parser.ContextQueueParser;
import jmotor.core.ioc.parser.impl.ContextParserImpl;
import jmotor.core.ioc.parser.impl.ContextQueueParserImpl;
import jmotor.core.ioc.pool.BeanConfigurationPool;
import jmotor.core.ioc.repository.ContextRepository;

/**
 * Component:
 * Description:
 * Date: 11-8-23
 *
 * @author Andy.Ai
 */
public class ApplicationContext {
    public static boolean debug = false;
    private static ContextParser contextParser;
    private static ContextQueueParser contextQueueParser;

    static {
        createContextParser();
        createContextQueueParser();
    }

    private static void createContextQueueParser() {
        ContextQueueParserImpl contextQueueParserImpl = new ContextQueueParserImpl();
        contextQueueParser = contextQueueParserImpl;
    }

    private static void createContextParser() {
        contextParser = new ContextParserImpl();
    }

    public static ContextParser getContextParser() {
        return contextParser;
    }

    public static ContextQueueParser getContextQueueParser() {
        return contextQueueParser;
    }

    public static BeanConfigurationPool getBeanConfigurationPool() {
        return ContextRepository.getRepository(BeanConfigurationPool.class);
    }
}
