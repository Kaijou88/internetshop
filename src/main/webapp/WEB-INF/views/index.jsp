<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kitty's Paradise</title>
</head>
<body>
    <h1>Welcome to Kitty's Paradise!</h1>

    <a href="${pageContext.request.contextPath}/logout">Logout</a> <br/> <br/>

    <a href="${pageContext.request.contextPath}/users/all">All users</a> <br/> <br/>

    <a href="${pageContext.request.contextPath}/products/add_new">Add products</a> <br/>

    <a href="${pageContext.request.contextPath}/products/for_user">All products for user</a> <br/>

    <a href="${pageContext.request.contextPath}/products/for_admin">All products for admin</a> <br/> <br/>

    <a href="${pageContext.request.contextPath}/shopping_cart">Shopping Cart</a> <br/>

    <a href="${pageContext.request.contextPath}/orders/all">Orders</a> <br/>
</body>
</html>
