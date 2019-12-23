<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width" />
<title>勤務表実績（${employeename}）</title>
</head>
<style>
tr.graytd{
	background-color: rgb(219, 219, 219);
}
td.graytd{
	background-color: rgb(219, 219, 219);
}
tr.even{
	background-color: white;
}
tr.odd{
	background-color: rgb(244, 244, 225);
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
window.onload = function(){
	//機種に合わせて表示
	var scalevalue = 0.00128 * screen.width;
	var content = 'width=device-width,height=device-height,initial-scale='+scalevalue;
	document.getElementsByName('viewport')[0].setAttribute('content', content);
	}

function cleardata(object){
	var tr = object.parentNode.parentNode;

	var td1 = tr.getElementsByTagName("td")[1];;
	var select1 = td1.getElementsByTagName("select")[0];
	var select2 = td1.getElementsByTagName("select")[1];

	clearselect(select1);
	clearselect(select2);

	var td2 = tr.getElementsByTagName("td")[2];;
	var select3 = td2.getElementsByTagName("select")[0];
	var select4 = td2.getElementsByTagName("select")[1];

	clearselect(select3);
	clearselect(select4);

}

function clearselect(select){
	for(var i=0; i<select.options.length; i++){
        if(select.options[i].innerHTML == ''){
            select.options[i].selected = true;
            break;
        }
    }
}


	function show_confirm() {
		var row = table1.rows.length;
		if(row==1){
			return true;
		}

		return confirm("該当月データを最初から作り直しますか？");
	}
</script>
<%@ include file="mainPanel.jsp"%>
<body>

	<h1>勤務表実績（${employeename}）</h1>
	<form:form action="/workInput" modelAttribute="workdailyForm">
	<input type="hidden" name="userid" value="${workdailyForm.userid}">
	<input type="hidden" name="totalWorkHours" value="${workdailyForm.totalWorkHours}">
	<input type="hidden" name="totalOverHours" value="${workdailyForm.totalOverHours}">

		<table border="0">
			<tr>
				<td>
					<table border="0" id="itemtable">
						<tr>
							<td><input type="submit" name="return" value="戻る" />
							<td>
						</tr>
						<tr>
							<td><form:select path="workYear">
									<form:options items="${yearList}" />
								</form:select>年</td>
							<td><form:select path="workMonth">
									<form:options items="${monthList}" />
								</form:select>月</td>
							<td><input type="submit" name="serach" value="検索" /></td>
						</tr>
						<tr>
							<td><input type="submit" name="recreate" onclick="return show_confirm();" value="新規作成" /></td>
							<td><input type="submit" name="save" value="保存" /></td>
							<td><input type="submit" name="print" value="勤務表印刷" /></td>
						</tr>
					</table>
				</td>
				<td width="500" align="center">
					<table border="1" id="kousutable">
						<tr>
							<td class="graytd">時間数合計：</td>
							<td><label id="totalhours" for="totalhours">${workmonthlyDto.workhoursmonth}</label></td>
						</tr>
						<tr>
							<td class="graytd">残業時間数合計：</td>
							<td><label id="addoverhours" for="addoverhours">${workmonthlyDto.overhoursmonth}</label></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>




		<div>
        <table border="1" id="table1">
        <tbody id="tbody1">
            <tr class="graytd">
            	<th>日付</th>
                <th>出社時間</th>
                <th>退社時間</th>
                <th>時間数</th>
                <th>残業</th>
                <th>残業累計</th>
                <th>時間クリア</th>
                <th>出張先</th>
                <th>備考</th>
            <c:forEach var="workdaily" items="${workmonthlyDto.workdailyDtoList}" varStatus="wstatus">
            <input type="hidden" name="useridarry" value="${workdaily.userid}">
            <input type="hidden" name="workyeararry" value="${workdaily.workyear}">
            <input type="hidden" name="workmontharry" value="${workdaily.workmonth}">
            <input type="hidden" name="workdayarry" value="${workdaily.workday}">
			<tr class="${(workdaily.weekendflag == '1' || workdaily.holidayflag == '1') ? 'even' : 'odd'}">
				<c:choose>
				<c:when test="${workdaily.weekendflag == '1' || workdaily.holidayflag == '1'}" >
				<td style="color:red">
				<fmt:formatDate value="${workdaily.date}" type="DATE" pattern="dd日（E）" />
				</td>
				</c:when>
				<c:otherwise>
				<td>
				<fmt:formatDate value="${workdaily.date}" type="DATE" pattern="dd日（E）" />
				</td>
				</c:otherwise>
				</c:choose>
				<td>
				<form:select path="start_harry[${wstatus.index}]">
					<form:options items="${hourList}"/>
				</form:select>:
				<form:select path="start_marry[${wstatus.index}]">
					<form:options items="${miniteList}"/>
				</form:select>
				</td>
                <td>
				<form:select path="end_harry[${wstatus.index}]">
					<form:options items="${hourList}"/>
				</form:select>:
				<form:select path="end_marry[${wstatus.index}]">
					<form:options items="${miniteList}"/>
				</form:select>
				</td>
                <td><label id="worktime" for="worktime">${workdaily.workhoursday}</label></td>
                <td><label id="overtime" for="overtime">${workdaily.overhoursday}</label></td>
                <td><label id="addovertime" for="addovertime">${workdaily.addoverhoursday}</label></td>
                <td align="center">
				<input type="button" id="clear" value="クリア" onclick="cleardata(this)"/>
				</td>
                <td><input type="text" name="biko1arry" value="${workdaily.biko1}" size="5"></td>
                <td><input type="text" name="biko2arry" value="${workdaily.biko2}" size="13"></td>
			</tr>
			</c:forEach>
		</tbody>
        </table>
        <br/>
    </div>
    </form:form>

</body>
</html>