<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h1>Admin page for all products</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Product</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${products}">
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
                <a href="${pageContext.request.contextPath}/products/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/addproducts">Add products</a> <br/>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
