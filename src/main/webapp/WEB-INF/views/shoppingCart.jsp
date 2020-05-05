<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart page</title>
</head>
<body>
<h1>Your shopping cart</h1>
<h4 style="color:red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/shopping_cart/new_order">
<table border="1">
    <tr>
        <th>ID</th>
        <th>Product</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${shoppingCart.products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/shopping_cart/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<button type="submit"/>Buy</button> </br>
</form>
    <a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
