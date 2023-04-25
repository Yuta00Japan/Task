<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>従業員登録</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/new.css">
</head>
<body>

	<div id="container">
		<div id="form">
			<button class="button">ログアウト</button>
			<table>
				<tr><td>No</td><td><input type="textBox" name="txtEmpNo"></td></tr>
				<tr><td>名前</td><td><input type="textBox" name="txtName"></td><td><button class="button">検索</button></td></tr>
				<tr><td>名前(カナ)</td><td><input type="textBox" name="txtKanaName"></td></tr>
				<tr><td>LogInID</td><td><input type="textBox" name="txtLogInID"></td></tr>
				<tr><td>メール</td><td><input type="textBox" name="txtMail"></td></tr>
				<tr><td>所属</td><td><select name="branch"></select></td></tr>
				<tr><td>部署</td><td><select name="department"></select></td></tr>
				<tr><td>上司</td><td><input type="textBox" name="txtBoss"></td><td><button  class="button">検索</button></td></tr>
				<tr>
					<td>
						権限
					</td>
					<td>
						<%--javascript node expressで実装する --%>	
					</td>
				</tr>
				<tr><td>パスワード</td><td><input type="textBox" name="txtPW"></td><td><button class="button">パスワード登録</button></td></tr>
			</table>
			  	<div id="buttonPart">
					<button class="button">登録</button>
					<button class="button">削除</button>
					<button class="button">クリア</button>
				</div>
				<%--エラーなどを表示する領域 --%>
		</div>
		
		<div id="password">
			<%--パスワード設定画面を設置する --%>
		</div>
	</div>
</body>
</html>