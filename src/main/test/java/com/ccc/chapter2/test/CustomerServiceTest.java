package com.ccc.chapter2.test;

import chapter2.model.Customer;
import chapter2.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
}
