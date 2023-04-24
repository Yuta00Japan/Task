<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>従業員一覧</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/list.css">
</head>
<body>

<form method="post">

<h1><button  formaction="MenuController" class="button">戻る</button></h1>

	<div id="form">
		<span class="form">名前検索 <input type="textbox" name="name" id="textBox"></span>
		<span class="form">所属 <select id="branch"></select></span>
		<span class="form">部署<select id="department"></select></span>
		<span class="form">削除者<input type="radio"></span>
		<span class="form"><button class="button">検索</button></span>
	</div>
	
	<h2>(名前、仮名の部分一致)</h2>
	
	<div id="result">
		<table border="1">
			<tr>
				<th class="start"></th>
				<th class="head">No</th>
				<th class="head">従業員名</th>
				<th class="head">所属</th>
				<th class="head">部署</th>
				<th class="head">上司</th>
			</tr>
			<%--ここでforを使い検索結果を表示する --%>
			<c:choose>
				<%--検索結果なし --%>
				<c:when test="${2%2==0 }">
					
				</c:when>
				<%--検索結果あり --%>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
			<%--ここまで --%>
		</table>
	</div>
<%--script読み込み --%>
<script src="js/list.js"></script>

</form>

</body>
</html>