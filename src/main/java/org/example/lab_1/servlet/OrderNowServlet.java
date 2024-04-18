package org.example.lab_1.servlet;

import org.example.lab_1.daos.OrderDAO;
import org.example.lab_1.daos.ProductDAO;
import org.example.lab_1.model.Cart;
import org.example.lab_1.model.Order;
import org.example.lab_1.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date date = new Date();

            User auth = (User) request.getSession().getAttribute("auth");
            if (auth != null) {

                String productId = request.getParameter("id");
                int productQuantity = Integer.parseInt(request.getParameter("quantity"));
                if (productQuantity <= 0) {
                    productQuantity = 1;
                }

                Order orderModel = new Order();
                ProductDAO tmpPDAO = new ProductDAO();
                orderModel.setPid(tmpPDAO.getSingleProduct(Integer.parseInt(productId)));
                orderModel.setUid(auth.getId());
                orderModel.setQuantity(productQuantity);
                orderModel.setDate(formatter.format(date));

                OrderDAO orderDAO = new OrderDAO();
                boolean result = orderDAO.insertOrder(orderModel);

                if (result) {
                    ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                    if (cart_list != null) {
                        for (Cart c : cart_list) {
                            if (c.getId() == Integer.parseInt(productId)) {
                                cart_list.remove(cart_list.indexOf(c));
                                break;
                            }
                        }
                    }
                    response.sendRedirect("orders");
                } else {
                    out.print("order failed");
                }

            } else {
                response.sendRedirect("login");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
