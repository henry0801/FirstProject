<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/>
<title>Welcome to HenryProject!</title>
</head>
<%@ include file="mainPanel.jsp"%>
<body>
	<h1>メニュー</h1>
	<table style="border-spacing: 10px;">
	<tr><td><a href='/workStatus'>➤勤務表入力状況</a></td></tr>
	<!-- <tr><td><a href="/workInput">➤勤務表実績</a></td></tr> -->
	<!-- <tr><td><a href="/statistic">➤統計</a></td></tr> -->

	<sec:authorize access="hasRole('ADMIN')">
		<tr><td><a href="/employee">➤社員一覧（管理者用）</a></td></tr>
	</sec:authorize>


	<!--<tr><td><a href="/tetris">➤俄罗斯方块</a></td></tr>-->
	</table>
</body>
</html>
