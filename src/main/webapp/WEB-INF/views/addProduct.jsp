<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Products page</title>
</head>
<body>
<h1>Please add products</h1>

<form method="post" action="${pageContext.request.contextPath}/products/add_new">
    Name: <input type="text" name="name"/> <br/>
    Price: <input type="number" name="price"/> <br/>

    <button type="submit">Add</button>
</form>
    <a href="${pageContext.request.contextPath}/products/for_admin">All products</a> <br/>
    <a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
