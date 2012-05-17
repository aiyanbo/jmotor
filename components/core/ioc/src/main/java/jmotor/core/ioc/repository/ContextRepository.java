package jmotor.core.ioc.repository;

import jmotor.core.ioc.exception.RegisterRepositoryException;
import jmotor.core.ioc.pool.BeanConfigurationPool;
import jmotor.core.ioc.pool.ElementConfigurationPool;
import jmotor.core.ioc.pool.ElementPool;
import jmotor.core.ioc.pool.EntryWrapperConfigurationPool;
import jmotor.core.ioc.pool.PropertiesPool;
import jmotor.core.ioc.pool.impl.BeanConfigurationCachePoolImpl;
import jmotor.core.ioc.pool.impl.ElementConfigurationPoolImpl;
import jmotor.core.ioc.pool.impl.ElementPoolImpl;
import jmotor.core.ioc.pool.impl.EntryWrapperConfigurationPoolImpl;
import jmotor.core.ioc.pool.impl.PropertiesPoolImpl;
import jmotor.util.ClassUtils;
import jmotor.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Component:IOC
 * Description:Context repository
 * Date: 12-1-14
 *
 * @author Andy.Ai
 */
public class ContextRepository {
    private static Map<String, Repository> poolRepository = new HashMap<String, Repository>();

    private ContextRepository() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T getRepository(Class<?> clazz) {
        Repository repository = poolRepository.get(clazz.getName());
        if (repository == null && ClassUtils.isRelationship(clazz, Repository.class)) {
            repository = initRepository(clazz);
            poolRepository.put(clazz.getName(), repository);
        }
        return (T) repository;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getRepository(String name) {
        return (T) poolRepository.get(name);
    }

    private static Repository registerRepository(String name, Class<?> repositoryClass, Class<?>[] parameterTypes, Object[] parameters) {
        Repository repository = null;
        if (ClassUtils.isRelationship(repositoryClass, Repository.class)) {
            try {
                repository = ObjectUtils.newInstance(repositoryClass, parameterTypes, parameters);
                poolRepository.put(name, repository);
            } catch (Exception e) {
                throw new RegisterRepositoryException(e);
            }
        }
        return repository;
    }

    private static Repository initRepository(Class<?> clazz) {
        Repository repository = null;
        if (BeanConfigurationPool.class.equals(clazz)) {
            repository = new BeanConfigurationCachePoolImpl();
        } else if (ElementConfigurationPool.class.equals(clazz)) {
            repository = new ElementConfigurationPoolImpl();
        } else if (ElementPool.class.equals(clazz)) {
            repository = new ElementPoolImpl();
        } else if (EntryWrapperConfigurationPool.class.equals(clazz)) {
            repository = new EntryWrapperConfigurationPoolImpl();
        } else if (PropertiesPool.class.equals(clazz)) {
            repository = new PropertiesPoolImpl();
        }
        return repository;
    }
}
