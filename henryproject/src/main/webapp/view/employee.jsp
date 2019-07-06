<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE>
<html>
<head>
<title>社員一覧</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width">
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
var count = 0;
$(document).ready(function(){
	//機種に合わせて表示
	var scalevalue = 0.0016 * screen.width;
	var content = 'width=device-width,height=device-height,initial-scale='+scalevalue;
	document.getElementsByName('viewport')[0].setAttribute('content', content);

	$("#add").click(function(){

		  var new_tr = $('#foradd').clone();
		  new_tr.show();
		  ++count;
		  $('#table1').append(new_tr);

	  });
	});
function notshow(object){
	object.nextElementSibling.value = "1";
	object.parentNode.parentNode.style.display = "none";
}
</script>
</head>
<body>
	<h1>社員一覧</h1>
    <form:form action="/employee" modelAttribute="employeeForm">
        				<table border="0" id="itemtable">
						<tr>
							<td><input type="submit" name="return" value="戻る" /><td>
						</tr>
						<tr>
							<td><input type="submit" name="refresh" value="再表示"/></td>
							<td><input type="submit" name="save" value="保存"/></td>
						</tr>
					</table><br/>


    <div>
        <table border="1" id="table1" style="display: table-cell">
            <tbody>
            	<tr class="graytd">
            	<th></th>
            	<th>ユーザID</th>
                <th>ユーザ名</th>
                <th>備考</th>
                </tr>
				<tr>
					<c:forEach var="employee" items="${employees}" varStatus="status">
						<input type="hidden" name="userid_exist" value="${employee.userid}">
						<tr>
							<td align="center">
								<input type="button" name="delete_exist" value="削除" onclick="notshow(this)"/>
								<input type="hidden" name="delete_flg_exist" value="0">
							</td>
							<td width="100">${employee.userid}</td>
							<td ><input type="text" name="username_exist" size="10" value="${employee.username}"></td>
							<td><input type="text" name="biko_exist" value="${employee.biko}"></td>
						</tr>
					</c:forEach>
				</tr>
			</tbody>
        </table>
        <br/><input type="button" id="add" value="行追加"/>
    </div>
    </form:form>


	<table>
		<tr id="foradd" style="display: none">
			<td align="center">
				<input type="button" name="delete_new" value="削除" onclick="notshow(this)"/>
				<input type="hidden" name="delete_flg_new" value="0">
			</td>
			<td width="100"><input type="text" name="userid_new" size="11" value=""></td>
			<td><input type="text" name="username_new" size="10" value=""></td>
			<td><input type="text" name="biko_new" value=""></td>

		</tr>
	</table>
</body>
</html>