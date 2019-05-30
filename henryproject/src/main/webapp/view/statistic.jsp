<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE>
<html>
<head>
<title>統計</title>
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
		  new_tr.find("input[id='radio1']").attr('name', 'status_new['+count+']');
		  new_tr.find("input[id='radio2']").attr('name', 'status_new['+count+']');
		  new_tr.find("input[id='radio3']").attr('name', 'status_new['+count+']');
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
    <h1>
        自分の意向を選択してください
    </h1>
    <form:form action="/statistic" modelAttribute="StatisticForm">
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
                <th>氏名</th>
                <th>状況</th>
                <th>備考</th>
                <th>更新日</th>
                </tr>
				<tr>
					<c:forEach var="statistic" items="${statistics}" varStatus="tr">
						<input type="hidden" name="id_exist" value="${statistic.id}">
						<tr>
							<td align="center">
								<input type="button" name="delete_exist" value="削除" onclick="notshow(this)"/>
								<input type="hidden" name="delete_flg_exist" value="0">
							</td>
							<td ><input type="text" name="name_exist" size="10" value="${statistic.name}"></td>
							<td>
  								<input type="radio" name="status_exist[${tr.index}]" value=""  ${statistic.status eq '' ? 'checked' : ''} style="display: none">
								<input type="radio" name="status_exist[${tr.index}]" value="1" ${statistic.status eq '1' ? 'checked' : ''}>OK
								<input type="radio" name="status_exist[${tr.index}]" value="0" ${statistic.status eq '0' ? 'checked' : ''}>NO
								<input type="radio" name="status_exist[${tr.index}]" value="2" ${statistic.status eq '2' ? 'checked' : ''}>保留</td>
							<td><input type="text" name="biko_exist" value="${statistic.biko}">
							</td>
							<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${statistic.modification_time}"></fmt:formatDate></td>
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
			<td><input type="text" name="name_new" size="10" value=""></td>
			<td>
			<input type="radio" id="radio0" value="" style="display: none">
			<input type="radio" id="radio1" value="1">OK
			<input type="radio" id="radio2" value="0">NO
			<input type="radio" id="radio3" value="2">保留
			</td>
			<td><input type="text" name="biko_new" value=""></td>
			<td>-</td>
		</tr>
	</table>
</body>
</html>