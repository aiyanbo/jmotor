package jmotor.core.i18n.impl;

import jmotor.core.cache.Cache;
import jmotor.core.i18n.I18nManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * Component:I18n
 * Description:I18n manager ehcache implementation
 * Date: 12-3-10
 *
 * @author Andy.Ai
 */
public class I18nManagerCacheImpl implements I18nManager {
    private Cache cache;

    @Override
    public String getI18nValue(String key) {
        return null;
    }

    @Override
    public String getI18nValue(String key, Locale locale) {
        return null;
    }

    @Override
    public void register(String baseName) {

    }

    @Override
    public Properties register(String path, Locale locale) {
        return null;
    }

    @Override
    public void register4FileSystem(String baseName) {

    }

    @Override
    public Properties register4FileSystem(String path, Locale locale) throws IOException {
        return null;
    }

    @Override
    public Properties register(InputStream inputStream, Locale locale) throws IOException {
        return null;
    }

    @Override
    public Properties register(byte[] content, Locale locale) {
        return null;
    }

    @Override
    public Properties getResources(String[] baseNames, Locale locale) {
        return null;
    }

    @Override
    public Properties getResources(String baseNames, Locale locale) {
        return null;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
