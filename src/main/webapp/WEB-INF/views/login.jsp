<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Please sign in</h1>

<h4 style="color:red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/login">
    Login: <input type="text" name="login"/> <br/>
    Password: <input type="password" name="pwd"/> <br/>

    <button type="submit"/>Sign in</button>
</form> <br/>
    <a href="${pageContext.request.contextPath}/inject_data">Inject test data into DB</a> <br/>

    Don't have an account?
    <a href="${pageContext.request.contextPath}/registration">Registration</a>
</body>
</html>
