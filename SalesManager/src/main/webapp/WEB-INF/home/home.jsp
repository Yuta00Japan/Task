<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
</head>
<body>

<form method="post">

<h1><button class="button" formaction="EmployeeConroller">従業員一覧</button></h1>

<%--システム管理者のみ実行可能 ～--%>

<h2><button class="button" formaction="EmployeeController">従業員登録</button></h2>

<%--～＞ --%>

<h3><button class="button" formaction="ItemController">商品登録</button></h3>



<h4><button class="button" name="state" value="logout" formaction="EmployeeController">ログアウト</button></h4>

</form>

</body>
</html>