<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Complete orders</title>
</head>
<body>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>User ID</th>
    </tr>
    <c:forEach var="order" items="${order}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <c:out value="${order.user.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order?id=${order.id}">Details</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/delete?id=${order.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
    <a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
