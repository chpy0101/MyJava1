package chapter2.controller.Customer;

import chapter2.controller.BaseServlet;
import chapter2.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/21.
 */
@WebServlet("/customer_create")
public class CustomerCreateServlet extends BaseServlet {

    private CustomerService service;

    @Override
    public void init() throws ServletException {
        service = new CustomerService();
    }

    /**
     * 进入创建页面
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(_htmlPath + "");
    }

    /**
     * 处理创建客户请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(_htmlPath + "");
    }
}
