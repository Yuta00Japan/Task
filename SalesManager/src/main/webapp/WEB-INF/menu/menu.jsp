<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/home.css">
</head>
<body>

<form method="post">

<h1><button class="button" name="state" value="list" formaction="EmployeeController">従業員一覧</button></h1>

<%--システム管理者のみ実行可能 ～--%>
		<c:if test='${user.userRole.equals("0000000001") }'>
			<h2><button class="button" name="state" value="new" formaction="EmployeeController">従業員登録</button></h2>
		</c:if>
<%--～＞ --%>

<h3><button class="button" name="state" value="new" formaction="ItemController">商品登録</button></h3>



<h4><button class="button" name="state" value="logout" formaction="EmployeeController">ログアウト</button></h4>

</form>

</body>
</html>