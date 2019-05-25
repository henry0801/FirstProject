<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/>
<title><c:out value="${title}"></c:out></title>
</head>
<body>
    <h1>
        社員一覧
    </h1>
    <form:form action="/showEmployee/showEmployeeForm" modelAttribute="employeeListForm">
    <input type="submit" name="return" value="戻る"/>
    <input type="submit" name="search" value="検索"/>
        <table border="1">
            <tbody>
            	<tr>
                <th>連番</th>
                <th>ユーザID</th>
                <th>社員名</th>
                </tr>
                <tr>
                <c:forEach var="employee" items="${employeeList}" varStatus="status">
                    <tr>
                        <td><c:out value="${status.index+1}"/></td>
                        <td><c:out value="${employee.userid}"/></td>
                        <td><c:out value="${employee.username}"/></td>
                    </tr>
                </c:forEach>
                </tr>
            </tbody>
        </table>
    </form:form>
</body>
</html>