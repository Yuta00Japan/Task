<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>大分類</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/category.css">
</head>
<body>

<h1><span id="no">No</span><span id="name">名称</span></h1>

<div id="container">
	
	<div id="left">
		<h2>大分類</h2>
	</div>

	<div id="right">
	
		<c:forEach var="item" items="${majorItem.list }">
		
		<form action="ItemController" method="post">	
			
			<input type="textbox" name="txtAddNo" class="txtAddNo" value="${item.rowNo }">

			<input type="textbox" name="txtName" class="txtAddName" value="${item.shouhin01Name }">

			<button class="button" name="state" value="detail,${item.parentID }">詳細</button>

			<button class="button" name="state" value="update,${item.shouhin01ID }">変更</button>

			<button class="button" name="state" value="delete,${item.shouhin01ID }">削除</button>
		
		</form>
			
			<br>
			
		</c:forEach>
		
			<div id="new">
			<form action="ItemController" method="post">
			
				<input type="textbox" name="txtAddNo" class="txtAddNo">
			
				<input type="textbox" name="txtAddName" class="txtAddName">
			 
				<button class="button" name="state" value="newItem01,0,major">追加</button>
				
			</form>
			</div>
	</div>
			
</div>

<%--モーダルのもと --%>
		<div id="easyModal" class="modal">
    		<div class="modal-content">
      			<div class="modal-header">
        			<h2>警告</h2>
        			<span class="modalClose">×</span>
      			</div>
      			<div class="modal-body">
       				<p>削除します。よろしいですか？</p>
      			</div>
      			<div id="buttonPart2">
      				<button name="state" value="deleteEmployee,${employee.empId }" id="exeDelete" class="button2">OK</button>
      				<button id="cancel" type="button" class="button2">NO</button>
      			</div>
      			
    		</div>
		</div>
		

</body>
</html>