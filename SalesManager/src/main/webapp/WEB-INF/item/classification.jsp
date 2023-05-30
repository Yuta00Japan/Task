<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>分類</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/category.css">
</head>
<body>


<h1><span id="no">No</span><span id="name">名称</span></h1>

<div id="container">
	
	<div id="left">
		<%--現在の分類を表示する --%>
		<h2 id="center">${nowLocation }</h2>
			
			<c:if test="${(nowLocation=='中分類' || nowLocation=='小分類') && majorId !='' && majorId !=null  }">
			
			<%--大分類 --%>
			
				<p class="center">
					<a href="javascript:major.submit();">大分類</a>
				</p>
				<form action="ItemController" name="major" method="post">
					<input type="hidden" name="state" value="majorCategory">
				</form>
				
				<p class="center">
					<input type="textbox" id="txtLargeClass" readonly="readonly"  value="${majorName }">
				</p>
			<%--大分類ここまで --%>	
			
			</c:if>
			
			<c:if test="${nowLocation=='小分類' && minorId !='' && minorId !=null}">
			
			<%--中分類 --%>		
				<p class="center">
					<a href="javascript:minor.submit();">中分類</a>
				</p>
				<form action="ItemController" name="minor" method="post">
					<input type="hidden" name="state" value="detail,${majorId},大分類">
				</form>
				
				<p class="center">
					<input type="textbox" id="txtLargeClass2" readonly="readonly"  value="${minorName }">
				</p>
			<%--中分類ここまで --%>
			
			</c:if>	
			
			
				<%--非表示分類ID 大分類、中分類--%>
				<label id="hidClassState">${majorId }</label>
				<label id="hidClassState2">${minorId }</label>
	</div>

	<div id="right">
	
		<c:forEach var="item" items="${item01List.list }">
		
		<form action="ItemController" method="post" class="form">	
			
			<input type="textbox" name="txtAddNo" class="txtAddNo" value="${item.rowNo }">

			<input type="textbox" name="txtAddName" class="txtAddName" value="${item.shouhin01Name }">

			<button class="button" name="state" value="detail,${item.shouhin01ID },${nowLocation}">詳細</button>

			<button class="button update" name="state"  value="updateItem01,${item.shouhin01ID },${item.parentID },${nowLocation}">変更</button>

			<button class="button delete" name="state" value="deleteItem01,${item.shouhin01ID },${item.parentID },${nowLocation}">削除</button>
			
		</form>
			
			<br>
			
		</c:forEach>
		
			<div id="new">
			<form  action="ItemController" method="post" id="newForm">
			
				<input type="textbox" name="txtAddNo" class="txtAddNo">
			
				<input type="textbox" name="txtAddName" class="txtAddName">
			 	<c:choose>
			 	
			 		<c:when test="${nowLocation =='大分類' }">
			 			<button class="button"  name="state" id="newBtn" value="newItem01,0,${nowLocation}">追加</button>
			 		</c:when>
			 		<c:otherwise>
			 			<button class="button"  name="state" id="newBtn" value="newItem01,${classificationId},${nowLocation}">追加</button>
			 		</c:otherwise>
			 		
				</c:choose>
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