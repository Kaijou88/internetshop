<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kitty's Paradise</title>
</head>
<body>
    <h1>Welcome to Kitty's Paradise!</h1>

    <a href="${pageContext.request.contextPath}/injectData">Inject test data into DB</a> <br/>

    <a href="${pageContext.request.contextPath}/injectProduct">Inject test products into DB</a> <br/> <br/>

    <a href="${pageContext.request.contextPath}/registration">Register</a> <br/>

    <a href="${pageContext.request.contextPath}/users/all">All users</a> <br/> <br/>

    <a href="${pageContext.request.contextPath}/addproducts">Add products</a> <br/>

    <a href="${pageContext.request.contextPath}/products/all">All products for user</a> <br/>

    <a href="${pageContext.request.contextPath}/products/admin">All products for admin</a> <br/> <br/>

    <a href="${pageContext.request.contextPath}/shoppingcart">Shopping Cart</a> <br/>

    <a href="${pageContext.request.contextPath}/orders/all">Orders</a> <br/>
</body>
</html>
