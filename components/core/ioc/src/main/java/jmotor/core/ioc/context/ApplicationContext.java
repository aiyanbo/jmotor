package jmotor.core.ioc.context;

import jmotor.core.ioc.loader.ContextLoader;
import jmotor.core.ioc.loader.ContextLoaderAdapter;
import jmotor.core.ioc.loader.impl.ContextLoaderAdapterImpl;
import jmotor.core.ioc.loader.impl.ContextLoaderImpl;
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
    private static ContextLoaderAdapter contextLoaderAdapter;

    static {
        createContextParser();
        createContextLoaderAdapter();
        createContextQueueParser();
    }

    private static void createContextLoaderAdapter() {
        ContextLoaderAdapterImpl contextLoaderAdapterImpl = new ContextLoaderAdapterImpl();
        ContextLoader contextLoader = new ContextLoaderImpl();
        contextLoaderAdapterImpl.setContextLoader(contextLoader);
        contextLoaderAdapter = contextLoaderAdapterImpl;
    }

    private static void createContextQueueParser() {
        ContextQueueParserImpl contextQueueParserImpl = new ContextQueueParserImpl();
        contextQueueParserImpl.setContextLoaderAdapter(contextLoaderAdapter);
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

    public static ContextLoaderAdapter getContextLoaderAdapter() {
        return contextLoaderAdapter;
    }

    public static BeanConfigurationPool getBeanConfigurationPool() {
        return ContextRepository.getRepository(BeanConfigurationPool.class);
    }
}
