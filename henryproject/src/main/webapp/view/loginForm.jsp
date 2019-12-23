<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0" />
<title>Login</title>
</head>
<body>
	<h1>login page</h1>
	<div id="wrapper">
		<h3>ログイン</h3>
		<c:if test="${param.containsKey('error')}">
			<span style="color: red;"> <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
			</span>
		</c:if>
		<form:form action="/login">
			<table>
				<tr>
					<td><label for="userid">ユーザID</label>
					<td><input type="text" id="username" name="username" value="admin" /></td>
				</tr>
				<tr>
					<td><label for="password">パスワード</label></td>
					<td><input type="password" id="password" name="password" value="12345" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><button>ログイン</button></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>