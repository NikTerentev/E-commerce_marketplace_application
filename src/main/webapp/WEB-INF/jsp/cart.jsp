<%@ page import="org.example.lab_1.model.User" %>
<%@ page import="org.example.lab_1.model.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.lab_1.daos.ProductDAO" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        request.setAttribute("auth", auth);
    }

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    List<Cart> cartProduct = null;
    if (cart_list != null) {
        ProductDAO pDao = new ProductDAO();
        cartProduct = pDao.getCartProducts(cart_list);
        double total = pDao.getTotalCartPrice(cart_list);
        request.setAttribute("cart_list", cart_list);
        request.setAttribute("total", total);
    }
%>
<html>
<head>
    <title>Страница корзины</title>
    <%@include file="../includes/header.jsp" %>
    <style type="text/css">
        .table tbody td {
            vertical-align: middle;
        }

        .btn-incre, .btn-decre {
            box-shadow: none;
            font-size: 25px;
        }
    </style>
</head>
<body>

<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <div class="d-flex py-3"><h3>Итоговая цена: ${ (total > 0)?dcf.format(total):0 }₽</h3><a
            class="mx-3 btn btn-primary" href="cart-check-out">Оплатить</a></div>
    <table class="table table-loght">
        <thead>
        <tr>
            <th scope="col">Название</th>
            <th scope="col">Категория</th>
            <th scope="col">Цена</th>
            <th scope="col">Купить сейчас</th>
            <th scope="col">Отмена</th>
        </tr>
        </thead>
        <tbody>
        <% if (cart_list != null) {
            for (Cart c : cartProduct) { %>

        <tr>
            <td><%= c.getName() %>
            </td>
            <td><%= c.getCategory().getName() %>
            </td>
            <td><%= dcf.format(c.getPrice()) %>₽</td>
            <td>
                <form action="order-now" method="post" class="form-inline">
                    <input type="hidden" name="id" value="<%= c.getId() %>"
                           class="form-input">
                    <div class="form-group d-flex justify-content-between w-50">
                        <a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%= c.getId()%>"><i
                                class="fas fa-minus-square"></i></a>
                        <input type="text" name="quantity" class="form-control w-50"
                               value="<%= c.getQuantity() %>" readonly>
                        <a class="btn btn-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%= c.getId()%>"><i
                                class="fas fa-plus-square"></i></a>
                         <button type="submit" class="btn btn-primary btn-sm">Купить</button>
                    </div>
                </form>
            </td>
            <td><a href="remove-from-cart?id=<%= c.getId() %>" class="btn btn-sm btn-danger">Удалить</a></td>
        </tr>

        <%
                }
            }
        %>

        </tbody>
    </table>
</div>

<%@include file="../includes/footer.jsp" %>
</body>
</html>
