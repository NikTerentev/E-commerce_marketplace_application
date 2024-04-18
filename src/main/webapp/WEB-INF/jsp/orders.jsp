        <%@ page import="org.example.lab_1.model.User" %>
<%@ page import="org.example.lab_1.model.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="org.example.lab_1.daos.OrderDAO" %>
<%@ page import="org.example.lab_1.model.Order" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
    User auth = (User) request.getSession().getAttribute("auth");
    List<Order> orders = null;
    if (auth != null) {
        request.setAttribute("auth", auth);
        orders = new OrderDAO().userOrders(auth.getId());
    } else {
        response.sendRedirect("login");
    }
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }
%>
<html>
<head>
    <title>Страница заказов</title>
    <%@include file="../includes/header.jsp" %>
</head>
<body>
<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <div class="card-header my-3">Все заказы</div>
    <table class="table table-light">
        <thead>
        <tr>
            <th scope="col">Дата</th>
            <th scope="col">Название</th>
            <th scope="col">Категория</th>
            <th scope="col">Количество</th>
            <th scope="col">Цена</th>
            <th scope="col">Отмена</th>
        </tr>
        </thead>
        <tbody>

        <%
            if (orders != null) {
                for (Order o : orders) {%>
        <tr>
            <td><%=o.getDate()%>
            </td>
            <td><%=o.getPid().getName()%>
            </td>
            <td><%=o.getPid().getCategory().getName()%>
            </td>
            <td><%=o.getQuantity()%>
            </td>
            <td><%= dcf.format(o.getPid().getPrice()) %>
            </td>
            <td><a class="btn btn-sm btn-danger"
                   href="cancel-order?id=<%=o.getOrderId()%>">Удалить</a>
            </td>
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
