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
		
		<%--分類ID --%>
		<label></label>
	</div>

	<div id="right">
	
		<c:forEach var="item" items="${majorItem.list }">
		
		<form action="ItemController" method="post" class="form">	
			
			<input type="textbox" name="txtAddNo" class="txtAddNo" value="${item.rowNo }">

			<input type="textbox" name="txtAddName" class="txtAddName" value="${item.shouhin01Name }">

			<button class="button" name="state" value="detail,${item.shouhin01ID },major">詳細</button>

			<button class="button update" name="state"  value="updateItem01,${item.shouhin01ID },none,major">変更</button>

			<button class="button delete" name="state" value="deleteItem01,${item.shouhin01ID },none,major">削除</button>
			
		</form>
			
			<br>
			
		</c:forEach>
		
			<div id="new">
			<form  action="ItemController" method="post" id="newForm">
			
				<input type="textbox" name="txtAddNo" class="txtAddNo">
			
				<input type="textbox" name="txtAddName" class="txtAddName">
			 	
			 	<button class="button"  name="state" id="newBtn" value="newItem01,0,none,major">追加</button>
				
			</form>	
			</div>
	</div>
			
</div>

<%--モーダルのもと --%>
<div id="easyModal" class="modal">
    <div class="modal-content">
      	<div class="modal-header">
        	<h2>確認</h2>
        	<span class="modalClose">×</span>
      	</div>
      	<div class="modal-body">
       		<p id="msg"></p>
      	</div>
      	
      	<div class="center">
      				
      		<button class="button" id="ok">OK</button>
      		<button id="cancel" type="button" class="button">NO</button>
      		
      	</div>
      			
    </div>
</div>
		
<script  src="js/item01.js"></script>
</body>
</html>