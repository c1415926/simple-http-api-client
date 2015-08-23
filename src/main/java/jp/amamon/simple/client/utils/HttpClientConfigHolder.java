package jp.amamon.simple.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Properties;

class HttpClientConfigHolder {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientConfigHolder.class);

    private static final String CONFIG_FILE_NAME = "httpclient.properties";

    public static final String MAX_CONNECTION = "maxConnection";
    public static final String MAX_CONNECTION_PER_ROUTE = "maxConnectionPerRoute";
    public static final String PROXY_HOST = "proxyHost";
    public static final String PROXY_PORT = "proxyPort";

    HttpClientConfigHolder() {
    }

    public static String getProperties(String key) {
        return HttpClientConfigUtilHolder.properties.getProperty(key);
    }

    public static Properties getProperties() {
        return HttpClientConfigUtilHolder.properties;
    }

    private static class HttpClientConfigUtilHolder {
        private static volatile Properties properties;

        static {
            ConfigurationParser configurationParser = new ConfigurationParser(new DefaultHttpConnectionSetting());
            try {
                configurationParser.parse(CONFIG_FILE_NAME);
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException(e);
            }
            properties = configurationParser.getProperties();
        }
    }

    private static class DefaultHttpConnectionSetting implements ConfigurationParser.ConfigInitializer {
        @Override
        public void init(Properties properties) {
            properties.put(MAX_CONNECTION, "500");
            properties.put(MAX_CONNECTION_PER_ROUTE, "50");
            properties.put(PROXY_HOST, "");
            properties.put(PROXY_PORT, "");
        }
    }
}
