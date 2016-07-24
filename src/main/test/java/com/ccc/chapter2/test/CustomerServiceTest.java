package com.ccc.chapter2.test;

import chapter2.model.Customer;
import chapter2.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

/**
 * Created by Administrator on 2016/7/22.
 */
public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init() {

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
    public void updateCutomerServiceTest(){
        Map<String,Object> fieldMap = new HashMap<String ,Object>();
        fieldMap.put("name", "陈晨昊");
        boolean result = customerService.updateCustomer(3,fieldMap);
        Assert.assertTrue(result);
    }

}

