package jp.amamon.simple.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.util.Properties;
import java.util.ResourceBundle;

class ConfigurationParser {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationParser.class);

    private Properties m_properties = new Properties();

    private static final String CONFIG_PATH = "configpath";

    public static String DEFAULT_CONFIG_PATH = getPropDir();

    public ConfigurationParser() {
    }

    ConfigurationParser(ConfigInitializer configInitializer) {
        configInitializer.init(this.m_properties);
    }

    void parse(String fileName) throws ParseException {
        if (StringUtils.isEmpty(fileName)) {
            logger.error("parsing NULL file!");
            throw new IllegalArgumentException("parsing NULL file!");
        }
        this.parse(new File(DEFAULT_CONFIG_PATH + fileName));
    }

    void parse(File file) throws ParseException {
        if (file == null) {
            logger.error("parsing NULL file");
            throw new IllegalArgumentException("parsing NULL file");
        }
        if (!file.exists()) {
            logger.error(String.format("parsing not existing file %s", file.getAbsolutePath()));
            throw new IllegalArgumentException(String.format("parsing not existing file %s", file.getAbsolutePath()));
        }
        try {
            FileReader reader = new FileReader(file);
            parse(reader);
        } catch (FileNotFoundException fex) {
            logger.error(String.format("parsing not existing file %s, so fallback on default configuration!", file.getAbsolutePath()), fex);
            throw new IllegalArgumentException(String.format("parsing not existing file %s!", file.getAbsolutePath()), fex);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException(String.format("close error when reading %s!", file.getAbsolutePath()), e);
        }
    }

    void parse(Reader reader) throws ParseException, IOException {
        if (reader == null) {
            logger.error("parsing NULL reader");
            throw new IllegalArgumentException("parsing NULL reader");
        }

        BufferedReader br = new BufferedReader(reader);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                int commentMarker = line.indexOf('#');
                if (commentMarker != -1) {
                    if (commentMarker == 0) {
                        continue;
                    } else {
                        throw new ParseException(line, commentMarker);
                    }
                } else {
                    if (line.isEmpty() || line.matches("^\\s*$")) {
                        continue;
                    }

                    int delimiterIdx = line.indexOf('=');
                    String key = line.substring(0, delimiterIdx).trim();
                    String value = line.substring(delimiterIdx + 1).trim();

                    m_properties.put(key, value);
                }
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            throw new ParseException("Failed to read", 1);
        } finally {
            br.close();
        }
    }

    interface ConfigInitializer {
        void init(Properties properties);
    }

    Properties getProperties() {
        return m_properties;
    }

    private static synchronized String getPropDir() {
        if (StringUtils.isEmpty(DEFAULT_CONFIG_PATH)) {
            ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_PATH);
            String propDir = bundle.getString("PROP_DIR");
            if (StringUtils.isNotEmpty(propDir)) {
                if (propDir.endsWith("/")) {
                    return propDir;
                } else {
                    return propDir + "/";
                }
            }
        }
        return "";
    }
}
