<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品登録</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/item_new.css">
</head>
<body>
	
<div id="container">	

	

	<div id="select">
	
	<form method="post">
	
		<%--各商品の販売数 --%>
		<input type="radio" name="rdoSelect" value="itemSales" checked>各商品の販売個数
		<br>
		<%--カテゴリー別販売数 --%>
		<input type="radio" name="rdoSelect" value="categorySales">カテゴリーの販売個数
		<br>
		<%--平均単価以上の販売数 --%>
		<input type="radio" name="rdoSelect" value="popularItem">平均単価以上の販売個数
		
	</div>
	
	<div id="form">
		
		
		
		<h1>
			<label class="label" for="txtStartDate">集計期間</label><input type="textbox" name="txtStartDate" id="txtStartDate">
			～
			<input type="textbox" name="txtEndDate" id="txtEndDate">
			
			<button class="button" id="search" name="state" value="achievementSearch" formaction="ItemController">抽出</button>
			
		</h1>
		
		<h2>
			<label class="label" for="category">カテゴリー</label>
			<select name="category" id="category">
				<%--DBから読み込む --%>
			</select>
			
			<%--メニュー画面に戻る --%>
			<button class="button" name="state" value="menu" formaction="SessionController">閉じる</button>
		
		</h2>
		
		</form>
	</div>
	
</div>

<div id="tableCenter">
	
	<table id="table" border="1">
		<tr>
			<%--検索の内容で表示内容を変更する --%>
			<th class="itemNameOrCategory">${action}</span></th>
			<th class="quantity">個数</th>
			<th class="total">合計金額</th>
		</tr>
	
	<c:choose>
			<c:when test="${trSalesList.list.size() <= 0 }">
				
			</c:when>
			
			<c:otherwise>
				<c:choose>
					<%--カテゴリ別の検索の場合 --%>
					<c:when test="${trSalesList.detail.size() > 0 }">
						
						<c:forEach var="detail" items="${trSalesList.detail }" >
							<tr>
								<td class="itemNameOrCategory">${detail.categoryName }</td>
								<td class="total number">${detail.categoryAmount}</td>
								<td class="quantity number">${detail.categorySales }</td>
							</tr>
						</c:forEach>
						
					</c:when>
					<%--それ以外 --%>
					<c:otherwise>
						
						<c:forEach var="item" items="${trSalesList.list }" >
							<tr>
								<td class="itemName">${item.itemName }</td>
								<td class="quantity number">${item.quantity }</td>
								<td class="total number">${item.unitPrice * item.quantity }</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
										
			</c:otherwise>
	</c:choose>
	
	</table>
</div>

<script src="js/item_new.js"></script>

</body>
</html>