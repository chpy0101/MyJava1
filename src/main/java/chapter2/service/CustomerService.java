package chapter2.service;

import ccc.Utils.ProUtils;
import ccc.helper.DatabaseHelper;
import chapter2.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2016/7/22.
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProUtils.class);

    /**
     * 获取客户列表
     */
    public List<Customer> getCustomerList(String keyword) {

        Connection con = null;
        try {
            List<Customer> customerList = new ArrayList<Customer>();
            String sql = "select * from customer";
            con = DatabaseHelper.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer cus = new Customer();
                cus.setId(rs.getLong("id"));
                cus.setContact(rs.getString("contact"));
                customerList.add(cus);
            }
            return customerList;
        } catch (SQLException ex) {
            LOGGER.error(" execute sql faild", ex);
        } finally {
            DatabaseHelper.closeConnectiong(con);
        }
        return null;
    }

    /**
     * 获取客户
     */
    public Customer getCustomer(long id) {
        return null;
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String, Object> fieldMap) {
        return false;
    }

    /**
     * 更新客户
     */
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return false;
    }

    /**
     * 删除客户
     */
    public boolean deleteCustomer(long id) {
        return false;
    }


}
