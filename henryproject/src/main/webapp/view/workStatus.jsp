<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
a.link{
  text-decoration: none;  /* 下線等を消す。 */
</style>
<script>
function allcheck(object){
	var elementLength = document.getElementsByName("taisyo").length;
	for(i=0;i<elementLength;i++){
		document.getElementsByName("taisyo")[i].checked = object.checked;
	}

}
</script>
<%@ include file="mainPanel.jsp"%>
<body>
    <h1>
        勤務表入力状況
    </h1>
    <form:form action="/workStatus" modelAttribute="workStatusForm">
    				<table border="0" id="itemtable">
						<tr>
							<td><input type="submit" name="return" value="戻る" /><td>
							<td></td>
						</tr>
						<tr>
							<td><form:select path="workYear">
									<form:options items="${yearList}" />
								</form:select>年</td>
							<td><form:select path="workMonth">
									<form:options items="${monthList}" />
								</form:select>月</td>
							<td><input type="submit" name="search" value="検索"/></td>
							<sec:authorize access="hasRole('ADMIN')">
								<td>&nbsp;<input type="submit" name="multiDownLoad" value="勤務表一括ダウンロード（管理者用）"/></td>
							</sec:authorize>
						</tr>


					</table>

        <table border="1">
            	<tr class="graytd">
            	<sec:authorize access="hasRole('ADMIN')">
            		<th><input type="checkbox"  onclick="allcheck(this)" checked/></th>
            	</sec:authorize>
                <th>連番</th>
                <th>ユーザID</th>
                <th>社員名</th>
                <th>勤務表入力状況</th>
                <th>勤務表確定状況</th>
                </tr>
                <tr>
                <c:forEach var="employee" items="${employeeDtoList}" varStatus="status">
                    <tr>
                    	<sec:authorize access="hasRole('ADMIN')">
                    		<td align="center"><input type="checkbox" name="taisyo" value="${status.index}" checked/></td>
                    	</sec:authorize>
                        <td align="center"><c:out value="${status.index+1}"/></td>
                        <td><c:out value="${employee.userid}"/></td>
                        <td><c:out value="${employee.username}"/></td>
						<td align="center">
							<sec:authorize access="hasRole('ADMIN') || ${username==employee.userid}">
	                        	<a href="/workInput/link?userid=${employee.userid}&employeename=${employee.username}" class="link">
	                        	<c:choose>
	                        	<c:when test="${employee.status==1}" >入力あり</c:when>
	                        	<c:otherwise>未入力</c:otherwise>
	                        	</c:choose>
	                        	</a>
                        	</sec:authorize>
                        	<sec:authorize access="hasRole('USER') && ${username!=employee.userid}">
	                        	<c:choose>
	                        	<c:when test="${employee.status==1}" >入力あり</c:when>
	                        	<c:otherwise>未入力</c:otherwise>
	                        	</c:choose>
                        	</sec:authorize>
                        </td>
                        <td align="center">未確定</td>
                    </tr>
                </c:forEach>
                </tr>
        </table>

        <%-- <br/>
        <input type="submit" name="test1" value="test1"/><input type="submit" name="test2" value="test2"/>message:${message}
        <br/> --%>

    </form:form>
</body>
</html>