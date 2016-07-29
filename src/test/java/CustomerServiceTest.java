import ccc.helper.DatabaseHelper;
import chapter2.model.Customer;
import chapter2.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/7/22.
 */
public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init() throws Exception {
        try {
            String file = "sql/customer_insert.sql";
            InputStream str = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            BufferedReader rd = new BufferedReader(new InputStreamReader(str));
            String sql;
            while ((sql = rd.readLine()) != null) {
                DatabaseHelper.excuteUpdate(sql);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Test
    public void getCustomerListTest() throws Exception {
        List<Customer> customerList = customerService.getCustomerList("");
        Assert.assertEquals(2, customerList.size());
    }

    @Test
    public void getCustomerTest() throws Exception {
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerServiceTest() {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("name", "customer3");
        fieldMap.put("contact", "ccc");
        fieldMap.put("telephone", "13585979361");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCutomerServiceTest() {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("name", "陈晨昊");
        boolean result = customerService.updateCustomer(3, fieldMap);
        Assert.assertTrue(result);
    }

}

