<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Please provide your user details</h1>

<h4 style="color:red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/registration">
    Name: <input type="text" name="name"/> <br/>
    Login: <input type="text" name="login"/> <br/>
    Password: <input type="password" name="pwd"/> <br/>
    Verify password: <input type="password" name="pwd-repeat"/> <br/>

    <button type="submit"/>Register</button>
</form>
    <a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>