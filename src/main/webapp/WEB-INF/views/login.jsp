<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 02.02.2019
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
</head>
<style>
    <%@include file='css/style.css' %>
</style>
<body>
<div class="forms">
    <div class="forms__container">
        <h2 class="forms__title">Log in</h2>
        <form id="logIn" action="/Lab8_war_exploded/servlet/login" method="POST">
            <div>
                <label class="forms__label" for="login-user">Login</label>
            </div>
            <div>
                <input class="forms__input" name="login-user" id="login-user" autocomplete="username" required>
            </div>
            <div>
                <label class="forms__label" for="login-pass">Password</label>
            </div>
            <div>
                <input class="forms__input" name="login-password" id="login-pass" type="password" required>
            </div>
            <button class="forms__button" type="submit">Log In</button>
        </form>
    </div>
</div>
<script>
</script>
</body>
</html>
