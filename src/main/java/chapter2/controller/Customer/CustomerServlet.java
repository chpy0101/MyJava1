package chapter2.controller.Customer;

import chapter2.controller.BaseServlet;
import chapter2.model.Customer;
import chapter2.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
@WebServlet("/customer")
public class CustomerServlet extends BaseServlet {
    private CustomerService service;

    @Override
    public void init() throws ServletException {
        service = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customerList = service.getCustomerList("");
        req.setAttribute("customerList", customerList);
        req.getRequestDispatcher(_htmlPath + "/customer/customer.jsp").forward(req, resp);
    }
}
