<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログインフォーム</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css">
</head>
<body>

<h1><label id="header" for="txtID">ログイン</label></h1>

<form action="SessionController" method="post">

<div id="container">

	<div id="label">
		<h2><label class="ID" for="txtID">LoginID</label></h2>

		<h3><label class="PASS" for="txtPASS">パスワード</label></h3>
	</div>

	<div id="form">
		<h2><input type="textbox" name="txtID" id="txtID"></h2>

		<h3><input type="textbox" name="txtPASS" id="txtPASS"><button class="button" name="state" value="try_Login">LogIn</button></h3>

	</div>

</div>

</form>

<%--エラー文表示領域 --%>

	<h4><label>${lbError }</label></h4>

<%--ここまで --%>

<script>

</script>
</body>
</html>