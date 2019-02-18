<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 01.02.2019
  Time: 14:27
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
        <h2 class="forms__title">Create new user</h2>
        <form id="signUp" action="/Lab8_war_exploded/servlet/register" method="POST">
            <div>
                <label class="forms__label" for="signup-user">Name</label>
            </div>
            <div>
                <input class="forms__input" name="signup-user" id="signup-user" required>
            </div>
            <div>
                <label class="forms__label" for="signup-login">Login</label>
            </div>
            <div>
                <input class="forms__input" name="signup-login" id="signup-login" required>
            </div>
            <div>
                <label class="forms__label" for="signup-email">Email</label>
            </div>
            <div>
                <input class="forms__input" name="signup-email" id="signup-email" type="email" required>
            </div>
            <div>
                <label class="forms__label" for="signup-pass">Password</label>
            </div>
            <div>
                <input class="forms__input" name="signup-password" id="signup-pass" type="password" required>
            </div>
            <div class="forms__checkbox-container">
                <input class="forms__checkbox" id="admin" type="checkbox" name="admin">
                <label class="forms__label" for="admin">Are you admin?</label>
            </div>
            <button class="forms__button" type="submit">Sign Up</button>
        </form>
    </div>
</div>
</body>
</html>
