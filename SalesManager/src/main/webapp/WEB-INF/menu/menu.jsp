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

	<h1>
		<button class="button" name="state" value="list,normal" formaction="EmployeeController">従業員一覧</button>
	</h1>

	<%--システム管理者のみ実行可能 ～--%>
		<%--システム管理者である0000000001の最後の文字を取りだし比較する --%>
		<c:set var="authority" value="${Character.toString(user.userRole.charAt(9)) }"/>
		<c:if test="${authority=='1' }">
			<h2>
				<button class="button" name="state" value="new" formaction="EmployeeController">従業員登録</button>
			</h2>
		</c:if>
	<%-- --%>

	<h3>
		<button class="button" name="state" value="majorCategory" formaction="ItemController">商品登録</button>
	</h3>

	<h3>
		<button class="button" name="state" value="achievement" formaction="ItemController">商品販売実績</button>
	</h3>


	<h4>
		<button class="button" name="state" value="logout" formaction="SessionController">ログアウト</button>
	</h4>

</form>

</body>
</html>