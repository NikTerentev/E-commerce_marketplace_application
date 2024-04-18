<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container">
        <a class="navbar-brand" href="index">E-Commerce Shopping Cart</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page"
                       href="index">Домой</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cart">Корзина <span class="badge bg-danger px-1">${ cart_list.size() }</span></a>
                </li>

                <%
                    if (auth != null) { %>

                <li class="nav-item">
                    <a class="nav-link" href="orders">Заказы</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="chat">Чат</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="log-out">Выход</a>
                </li>

                <%} else { %>

                <li class="nav-item">
                    <a class="nav-link" href="login">Вход</a>
                </li>

                <%
                    }
                %>


            </ul>
        </div>
    </div>
</nav>
