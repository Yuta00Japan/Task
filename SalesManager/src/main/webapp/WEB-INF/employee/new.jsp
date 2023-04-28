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

<form method="post" action="EmployeeController" id="Main"  >

<h1><button class="button" name="state" value="logout" formaction="EmployeeController">ログアウト</button></h1>

	<div id="container">
		<div id="form">
			<table>
				<tr><td class="header">No</td><td class="content"><input type="text" name="txtEmpNo" value="${employee.empNo }" id="empNo"></td></tr>
				<tr><td class="header">名前</td><td class="content"><input type="text" name="txtName" value="${employee.fullName }"></td>
					<td><%--従業員検索 --%>
						<button name="state" value="list,employee" formaction="EmployeeController" class="button">検索</button>
					</td>
				</tr>
				<tr><td class="header">名前(カナ)</td><td class="content"><input type="text" name="txtKanaName" value="${employee.kanaName }"></td></tr>
				<tr><td class="header">LogInID</td><td class="content"><input type="text" name="txtLogInID" value="${employee.loginId }" id="loginId"></td></tr>
				<tr><td class="header">メール</td><td class="content"><input type="text" name="txtMail" value="${employee.email }" id="mail"></td></tr>
				<tr><td class="header">所属</td>
					<td class="content">
						<input type="hidden" id="branchValue" value="${employee.branchId }">
						<select name="branchId" id="branch">
							<option value=""></option>
						</select>
					</td>
				</tr>
				<tr><td class="header">部署</td>
					<td class="content">
						<input type="hidden" id="departmentValue" value="${employee.departmentId}">
					<select name="departmentId" id="department">
						<option value=""></option>
					</select>
					</td>
				</tr><%--上司検索 --%>
				<tr><td class="header">上司</td><td class="content"><input type="text" name="txtBoss" value="${ boss.fullName}" id="bossName"></td>
					<td>
						<button name="state" value="list,boss" formaction="EmployeeController" class="button">上司検索</button>
					</td>
				</tr>
				<tr>
					<td class="header">
						権限
					</td>
					<td>
						<span id="role"></span>
					</td>
				</tr>
				<tr><td class="header">パスワード</td>
					<td><input type="text" name="txtPW" value="${employee.password }" id="passValue"></td>
					<td><button class="button" type="button" id="password">パスワード登録</button></td>
				</tr>
			</table>
			  	<div id="buttonPart">
					<button class="button2"  name="state" value="" id="submit" formaction="EmployeeController">登録</button>
					<button class="button2" type="button" id="delete">削除</button><span id="modal"></span>
				
					<input type="reset" class="button2" form="Main" value="リセット">
				</div>
				<%--エラーなどを表示する領域 --%>
				<span id="formError"></span>
				<%--ここまで --%>
				
				<%--非表示従業員ID　上司ID --%>
				<label class="hidden" id="empId">${employee.empId }</label>
				<br>
				<label class="hidden" id="bossId">${boss.empId }</label>
		</div>
		
		<div id="password">
			<%--パスワード設定画面を設置する --%>
			<span id="passTable"></span>
			<span id="passButton1"></span>
			<span id="passButton2"></span>
			<br>
			<span id="passwordError"></span>
		</div>
	</div>
	
</form>
<script src="js/new.js"></script>
</body>
</html>