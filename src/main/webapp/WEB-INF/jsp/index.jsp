<%@ page import="org.example.lab_1.model.User" %>
<%@ page import="org.example.lab_1.daos.ProductDAO" %>
<%@ page import="org.example.lab_1.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.lab_1.model.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<% User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        request.setAttribute("auth", auth);
    }

    List<Product> products = ProductDAO.getAllProducts();

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }
%>
<html>
<head>
    <title>Добро пожаловать в корзину!</title>
    <%@include file="../includes/header.jsp" %>
</head>
<body>

<%@include file="../includes/navbar.jsp" %>

<div class="container">
    <div class="card-header my-3">Все товары</div>
    <div class="row">
        <%
            if (!products.isEmpty()) {
                for (Product p : products) {%>
        <div class="col-md-3 my-3">
            <div class="card w-100" style="width: 18rem;">
                <img src="../../product-image/<%= p.getImage()%>"
                     class="card-img-top" alt="Изображение товара">
                <div class="card-body">
                    <h5 class="card-title fw-bold"><%= p.getName()  %>
                    </h5>
                    <h6 class="price">Цена: <%= p.getPrice()%>₽</h6>
                    <h6 class="category">
                        Категория: <%= p.getCategory().getName() %>
                    </h6>
                    <h6 class="characteristics fw-bold">
                        Характеристики:
                    </h6>
                    <h6 class="release-date">Дата
                        выхода: <%= p.getCharacteristics().getReleaseDate()%>
                    </h6>
                    <h6 class="ram-volume">Объём оперативной
                        памяти: <%= p.getCharacteristics().getRamVolume()%>
                    </h6>
                    <h6 class="storage-volume">Объём постоянной
                        памяти: <%= p.getCharacteristics().getStorageVolume()%>
                    </h6>
                    <h6 class="diagonal">
                        Диагональ: <%= p.getCharacteristics().getDiagonal()%>
                    </h6>
                    <h6 class="processor">
                        Процессор: <%= p.getCharacteristics().getProcessor()%>
                    </h6>
                    <h6 class="weight">
                        Вес: <%= p.getCharacteristics().getWeight()%> г
                    </h6>
                    <div class="mt-3 d-flex justify-content-between">
                        <a href="add-to-cart?id=<%= p.getId() %>"
                           class="btn btn-dark me-3 btn-sm">Добавить в
                            корзину</a>
                        <a href="order-now?quantity=1&id=<%= p.getId() %>" class="btn btn-primary btn-sm">Купить
                            сейчас</a>
                    </div>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>

    </div>
</div>

<%@include file="../includes/footer.jsp" %>
</body>
</html>
