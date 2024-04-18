<%@ page import="org.example.lab_1.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        request.setAttribute("auth", auth);
    } else {
        response.sendRedirect("login");
    }
%>

<html>
<head>
    <title>Чат</title>
    <%@include file="../includes/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="../../css/main.css"/>

</head>

<body>

<%@include file="../includes/navbar.jsp" %>

<%
    if (auth != null) {
%>

<input type="hidden" id="nickname" name="nickname"
       value='<%= ((User) session.getAttribute("auth")).getNickname() %>'>

<%} %>

<div class="container border shadow-sm bg-white rounded p-3"
     style="max-width: 800px; min-height: 630px;" id="chat-page">
    <div class="row g-3">
        <div class="col-4 border-end ps-3 pt-3 pe-3 mt-3 bg-primary text-white rounded d-flex flex-column justify-content-between" style="min-height: 588px;">
            <div>
                <h2 class="h5 fw-bold">Online Users</h2>
                <div class="overflow-auto" style="max-height: 500px;">
                    <ul class="list-unstyled" id="connectedUsers">
                    </ul>
                </div>
            </div>

            <div>
                <p id="connected-user-fullname"></p>
            </div>
        </div>

        <div class="col-8">
            <div class="form-check form-switch">
                <label class="form-check-label" for="customSwitch">Подписаться
                    на push-уведомления</label>
                <input class="form-check-input" type="checkbox"
                       id="customSwitch" data-bs-toggle="toggle">
            </div>
            <div class="overflow-auto rounded mb-4 ps-3 pt-3 pe-3"
                 id="chat-messages"
                 style="height: 500px;">
            </div>

            <form id="messageForm" name="messageForm" class="d-none mb-0">
                <div class="input-group">
                    <input type="text" class="form-control" id="message"
                           placeholder="Type your message..."
                           autocomplete="off">
                    <button class="btn btn-primary">Send</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>


<script type="module" src="../../js/main.js"></script>

<%@include file="../includes/footer.jsp" %>

</body>

</html>