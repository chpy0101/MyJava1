package ccc.helper;

import ccc.Utils.ProUtils;
import chapter2.model.Customer;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by Administrator on 2016/7/26.
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProUtils.class);

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final String URL;
    private static final Properties Pro = new Properties();

    static {
        Properties config = ProUtils.loadProps("config.properties");
        URL = config.getProperty("url");
        Pro.setProperty("driver", config.getProperty("driver"));
        Pro.setProperty("user", config.getProperty("user"));
        Pro.setProperty("password", config.getProperty("password"));
        Pro.setProperty("serverTimezone", "GMT+8");
        try {
            Class.forName(Pro.getProperty("driver"));
        } catch (ClassNotFoundException ex) {
            LOGGER.error("can not load jdbc driver", ex);
        }
    }

    /**
     * 获取sql连接
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, Pro);
        } catch (SQLException ex) {
            LOGGER.error("get connection failed", ex);
        }
        return con;
    }

    /**
     * 关闭sql连接
     */
    public static void closeConnectiong(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOGGER.error("close connection faild", ex);
            }
        }
    }

    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        Connection con = getConnection();
        try {
            entityList = QUERY_RUNNER.query(con, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException ex) {
            LOGGER.error("query entity list failed", ex);
            throw new RuntimeException(ex);
        } finally {
            closeConnectiong(con);
        }
        return entityList;
    }
}
