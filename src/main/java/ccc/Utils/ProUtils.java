package ccc.Utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by chpy on 16/7/23.
 */
public final class ProUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProUtils.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProps(String filename) {
        Properties pro = null;
        InputStream str = null;
        try {
            str = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (str == null) {
                throw new FileNotFoundException(filename + " not found");
            }
            pro = new Properties();
            pro.load(str);
        } catch (IOException ex) {
            LOGGER.error("load property File faild", ex);
        } finally {
            if (str != null) {
                try {
                    str.close();
                } catch (IOException ex) {
                    LOGGER.error("close io failed", ex);
                }
            }

        }
        return pro;
    }


    public static String getString(Properties pro, String key) {
        return getString(pro, key);
    }


    public static String gerString(Properties pro, String key, String defaultValue) {
        String value = defaultValue;
        if (pro.containsKey(key)) {
            value = pro.getProperty(key);
        }
        return value;
    }

    public static int getInt(Properties pro, String key) {
        return getInt(pro, key);
    }

    public static int getInt(Properties pro, String key, int defaultValue) {
        int value = defaultValue;
        if (pro != null && pro.containsKey(key)) {
            value = CastUtils.castInt(pro.getProperty(key));
        }
        return value;
    }
}
