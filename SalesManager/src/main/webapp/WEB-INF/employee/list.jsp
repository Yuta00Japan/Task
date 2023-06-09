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

		<%--修正対象 仮修正20230517実施--%>
		<c:choose>
		<%--従業員登録画面からの場合 --%>
			<c:when test="${from=='new' }">
				<h1><button name="state" value="new" formaction="EmployeeController" class="button">戻る</button></h1>
			</c:when>
			<c:otherwise>
				<h1><button class="button" name="state" value="menu"  formaction="SessionController">戻る</button></h1>
			</c:otherwise>
		</c:choose>
		
	<div id="form">
		<span class="form">名前検索 <input type="textbox" name="name"  id="textBox"></span>
		<span class="form">所属 
			<select id="branch" name="branch">
				<option value=""></option>
			</select>
		</span>
		<span class="form">部署
			<select id="department" name="department">
				<option value=""></option>
			</select>
		</span>
		
		<span class="form">削除者<input type="radio" name="enable" value="true"></span>
		<span class="form"><button class="button" name="state" value="search" formaction="EmployeeController">検索</button></span>
	</div>
	
	<h2>(名前、仮名の部分一致)</h2>
	
	<div id="result">
		<table id="table">
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
				<c:when test="${empList.list.size() <=0}">
				
				</c:when>
				<%--検索結果あり --%>
				<c:otherwise>
					<c:forEach var="i" begin="0" end="${empList.list.size()-1 }" step="1">
					
							 <tr class="result">
								<td class="content">
								<%--システム管理者のみ実行可能 --%>
									<c:set var="authority" value="${Character.toString(user.userRole.charAt(9)) }"/>
									<c:if test="${authority == '1' }">
										<c:choose>
										<%--従業員検索の場合 --%>
											<c:when test="${method=='employee' }">		
												<button name="state" value="detail,${empList.list.get(i).empId }" formaction="EmployeeController">選択</button>
											</c:when>
										<%--上司検索の場合 --%>
											<c:otherwise>
												<button name="state" value="selectBoss,${empList.list.get(i).empId }" formaction="EmployeeController">選択</button>
											</c:otherwise>
										</c:choose>
									</c:if>
								</td>
								<td class="content">
									<c:out value="${empList.list.get(i).empNo}"/>
								</td>
								<td class="content">
									<c:out value="${empList.list.get(i).fullName }"/>
								</td>
								<td class="content">
									<c:out value="${empList.detail.get(i).branchName }"/>
								</td>
								<td class="content">
									<c:out value="${empList.detail.get(i).departmentName }"/>
								</td>
								<td class="content">
									<%--上司が有効なら表示する --%>
									<c:if test="${empList.detail.get(i).bossEnable==true }">
										<c:out value="${empList.detail.get(i).bossName }"/>
									</c:if>
								</td>
							</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<%--ここまで --%>
		</table>
	</div>

</form>
<%--script読み込み --%>
<script src="js/list.js"></script>
<script>
/**
 * 検索結果の表示に色を設定する
 */
window.addEventListener('load',()=>{

	var result = document.querySelectorAll('.result');
	
	for(let i = 0; i < result.length; i++){
		//偶数の場合
		if((i+1)%2==0){
			result[i].style.backgroundColor='#FF8C00';
		}else{
			result[i].style.backgroundColor='#FFA07A';
		}
	}
});

</script>
</body>
</html>