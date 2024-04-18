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

@WebServlet("/cart-check-out")
public class CheckOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");

            User auth = (User) request.getSession().getAttribute("auth");

            if (cart_list != null && auth != null) {

                for (Cart c:cart_list) {
                    Order order = new Order();
                    ProductDAO tmpPDAO = new ProductDAO();
                    order.setPid(tmpPDAO.getSingleProduct(c.getId()));
                    order.setUid(auth.getId());
                    order.setQuantity(c.getQuantity());
                    order.setDate(formatter.format(date));

                    OrderDAO oDao = new OrderDAO();
                    boolean result = oDao.insertOrder(order);

                    if (!result) break;
                }

                cart_list.clear();
                response.sendRedirect("orders");

            } else {
                if (auth == null) response.sendRedirect("login");
                response.sendRedirect("cart");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
