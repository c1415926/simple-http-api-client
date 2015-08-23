package jp.amamon.simple.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Properties;

class ServicePathConfigHolder {

    private static final Logger logger = LoggerFactory.getLogger(ServicePathConfigHolder.class);

    private static final String CONFIG_FILE_NAME = "servicepath.properties";

    public static final String HOST_NAME = "host.name";
    public static final String HOST_PORT = "host.port";
    public static final String HOST_ROOT = "host.root";

    private ServicePathConfigHolder() {
    }

    public static String getProperties(String key) {
        return ServicePathFetcherHolder.properties.getProperty(key);
    }

    private static class ServicePathFetcherHolder {
        private static volatile Properties properties;

        static {
            ConfigurationParser configurationParser = new ConfigurationParser();
            try {
                configurationParser.parse(CONFIG_FILE_NAME);
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException(e);
            }
            properties = configurationParser.getProperties();
        }
    }
}
