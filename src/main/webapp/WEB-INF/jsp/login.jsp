<%@ page import="org.example.lab_1.model.User" %>
<%@ page import="org.example.lab_1.daos.ProductDAO" %>
<%@ page import="org.example.lab_1.model.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<% User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        response.sendRedirect("index.jsp");
    }

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }
%>
<html>
<head>
    <title>Вход в корзину</title>
    <%@include file="../includes/header.jsp" %>
</head>
<body>

<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <div class="card w-50 mx-auto my-5">
        <div class="card-header text-center">
            Вход пользователя
        </div>
        <div class="card-body">
            <form action="user-login" method="post">
                <div class="form-group">
                    <label>Email адрес</label>
                    <input type="email" class="form-control" name="login-email"
                           placeholder="Введите Ваш Email" required>
                </div>

                <div class="form-group">
                    <label>Пароль</label>
                    <input type="password" class="form-control"
                           name="login-password" placeholder="*******" required>
                </div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-primary">Login</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="../includes/footer.jsp" %>
</body>
</html>
