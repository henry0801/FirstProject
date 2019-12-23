<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/>
<title>Login</title>
</head>
<body>
<h1>login page</h1>

<p style="color:red;font-weight: 900">${msg}</p>

<form:form method="POST" action="/login" modelAttribute="LoginForm">
   <table>
    <tr>
        <td>ユーザID:</td>
        <td><input type="text" name="userid" value="henry"/></td>
    </tr>
    <tr>
        <td>パスワード：</td>
        <td><input type="password" name="password" value="12345"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="ログイン">
        </td>
    </tr>
</table>
</form:form>
</body>
</html>