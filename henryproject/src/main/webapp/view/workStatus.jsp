<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/>
<title>勤務入力状況 </title>
</head>
<style>
tr.graytd{
	background-color: rgb(219, 219, 219);
}
</style>
<%
Map<String, String> yearList = new TreeMap<String, String>();
yearList.put("", "");
yearList.put("2019", "2019");
yearList.put("2020", "2020");
yearList.put("2021", "2021");
Map<String, String> monthList = new TreeMap<String, String>();
monthList.put("", "");
monthList.put("01", "01");
monthList.put("02", "02");
monthList.put("03", "03");
monthList.put("04", "04");
monthList.put("05", "05");
monthList.put("06", "06");
monthList.put("07", "07");
monthList.put("08", "08");
monthList.put("09", "09");
monthList.put("10", "10");
monthList.put("11", "11");
monthList.put("12", "12");
session.setAttribute("yearList",yearList);
session.setAttribute("monthList",monthList);
%>
<body>
    <h1>
        勤務入力状況
    </h1>
    <form:form action="/workStatus" modelAttribute="workStatusForm">
    				<table border="0" id="itemtable">
						<tr>
							<td><input type="submit" name="return" value="戻る" /><td>
						</tr>
						<tr>
							<td><form:select path="workYear">
									<form:options items="${yearList}" />
								</form:select>年</td>
							<td><form:select path="workMonth">
									<form:options items="${monthList}" />
								</form:select>月</td>
							<td><input type="submit" name="search" value="検索"/></td>
						</tr>
					</table>

        <table border="1">
            <tbody>
            	<tr class="graytd">
                <th>連番</th>
                <th>ユーザID</th>
                <th>社員名</th>
                <th>勤務表</th>
                <th>備考</th>
                </tr>
                <tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr>
                        <td><c:out value="${status.index+1}"/></td>
                        <td><c:out value="${employee.userid}"/></td>
                        <td><c:out value="${employee.username}"/></td>
                        <td>
                        	<a href="/workInput/link?id=${employee.userid}&name=${employee.username}">
                        	<c:choose>
                        	<c:when test="${employee.countOfWorkInput>0}" >入力あり</c:when>
                        	<c:otherwise>未入力</c:otherwise>
                        	</c:choose>
                        	</a>
                        </td>
                        <td><c:out value=""/></td>
                    </tr>
                </c:forEach>
                </tr>
            </tbody>
        </table>

        <br/>
        <input type="submit" name="test1" value="test1"/><input type="submit" name="test2" value="test2"/>message:${message}<br/>
    </form:form>
</body>
</html>