package ccc.helper;

import ccc.Utils.CollectionUtil;
import ccc.Utils.ProUtils;
import chapter2.model.Customer;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by Administrator on 2016/7/26.
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProUtils.class);

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final String URL;
    private static final Properties Pro = new Properties();

    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();

    /**
     * hashMap表名字典
     */
    private static final Map<String, String> TABLE_MAP;

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
        //初始化表名字典表
        TABLE_MAP = new HashedMap<String, String>();
    }

    /**
     * 初始化数据库字典表
     */
    private static void initTableMap() {
        Properties pro = ProUtils.loadProps("table.properties");
        HashedMap<String, String> map = new HashedMap<String, String>();
        for (Map.Entry item : pro.entrySet()) {
            map.put(item.getKey().toString(), item.getValue().toString());
        }
        if (TABLE_MAP != null && !TABLE_MAP.isEmpty()) {
            TABLE_MAP.clear();
        }
        TABLE_MAP.putAll(map);
    }

    /**
     * 获取sql连接
     */
    private static Connection getConnection() {
        Connection con = CONNECTION_HOLDER.get();
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, Pro);
            } catch (SQLException ex) {
                LOGGER.error("get connection failed", ex);
                throw new RuntimeException(ex);
            } finally {
                CONNECTION_HOLDER.set(con);
            }
        }
        return con;
    }

    /**
     * 关闭sql连接
     */
    private static void closeConnectiong() {
        Connection con = CONNECTION_HOLDER.get();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOGGER.error("close connection faild", ex);
                throw new RuntimeException(ex);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }


    /**
     * 获取表名
     */
    private static <T> String getTableName(Class<T> entiyClass) throws Exception {

        if (!TABLE_MAP.containsKey(entiyClass.getName())) {
            initTableMap();
            if (!TABLE_MAP.containsKey(entiyClass.getName())) {
                throw new Exception("table not exist");
            }
        }
        return TABLE_MAP.get(entiyClass.getName());
    }

    /**
     * 查询实体列表
     */
    public static <I> List<I> queryEntityList(Class<I> entityClass, String sql, Object... params) {
        List<I> entityList;

        try {
            Connection con = getConnection();
            entityList = QUERY_RUNNER.query(con, sql, new BeanListHandler<I>(entityClass), params);
        } catch (Exception ex) {
            LOGGER.error("query entity list failed", ex);
            throw new RuntimeException(ex);
        } finally {
            closeConnectiong();
        }
        return entityList;
    }

    /**
     * 查询单个实体
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection con = getConnection();
            entity = QUERY_RUNNER.query(con, sql, new BeanHandler<T>(entityClass), params);
        } catch (Exception ex) {
            LOGGER.error("query entity list failed", ex);
            throw new RuntimeException(ex);
        } finally {
            closeConnectiong();
        }
        return entity;
    }

    /**
     * 多表查询
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> entity;
        try {
            Connection con = getConnection();
            entity = QUERY_RUNNER.query(con, sql, new MapListHandler(), params);
        } catch (Exception ex) {
            LOGGER.error("query entity list failed", ex);
            throw new RuntimeException(ex);
        } finally {
            closeConnectiong();
        }
        return entity;
    }

    /**
     * 执行更新语句（insert,update,delete）
     */
    private static int excuteUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection con = getConnection();
            rows = QUERY_RUNNER.update(con, sql, params);
        } catch (Exception ex) {
            LOGGER.error("execute update failed", ex);
            throw new RuntimeException(ex);
        } finally {
            closeConnectiong();
        }
        return rows;
    }

    /**
     * 新增
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity: fieldMap is empty");
            return false;
        }
        try {
            String sql = "Insert into" + getTableName(entityClass);

            StringBuilder colums = new StringBuilder("(");
            StringBuilder values = new StringBuilder("(");
            for (Map.Entry fieldName : fieldMap.entrySet()) {
                colums.append(fieldName.getKey()).append(",");
                values.append("?,");
            }
            colums.replace(colums.lastIndexOf(","), colums.length(), ")");
            values.replace(values.lastIndexOf(","), values.length(), ")");
            sql += colums + "values" + values;
            Connection con = getConnection();

            Object[] params = fieldMap.values().toArray();
            return excuteUpdate(sql, params) == 1;
        } catch (Exception ex) {
            LOGGER.error("insert failed", ex);
            throw new RuntimeException("insert failed");
        } finally {
            closeConnectiong();
        }
    }

    /**
     * 更新
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap) || id < 0) {
            return false;
        }
        try {
            String sql = "Update " + getTableName(entityClass) + " Set";
            StringBuilder colums = new StringBuilder("(");
            for (String colum : fieldMap.keySet()) {
                colums.append(colum).append("=?,");
            }
            sql += colums.substring(0, colums.lastIndexOf(",")) + ")";

            Object[] params = fieldMap.values().toArray();
            return excuteUpdate(sql, params) > 0;
        } catch (Exception ex) {
            LOGGER.error("update failed", ex);
            throw new RuntimeException("update failed");
        } finally {
            closeConnectiong();
        }

    }

}
