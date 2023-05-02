package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.connectionPool.Pool;
import model.item.TrSales;
import model.item.TrSalesList;

public class TrSalesDao implements Crud{

	/**
	 * 入力情報から検索SQLを作成する
	 * @param start 集計開始日付
	 * @param end 集計終了日付
	 * @param category カテゴリ
	 * @param select 検索方法
	 * @return 検索用SQL
	 */
	public String createSQL(String start,String end,String category,String select) {
		
		String baseSQL =
			"select salesId,tr.categoryId itemName,unitPrice,quantity,salesDate,updateTime, ms.category, sum(unitprice * quantity) as total " 
			+" from Trsales as tr inner join MSCategory as ms on tr.categoryId = ms.categoryId ";
		
		start = start.replaceAll("[/]", "");
		end = end.replaceAll("[/]", "");
		
		if(category=="" || category==null) {
			category="";
		}
		
		
		System.out.println(start+" "+end+" "+category+" "+select);
		
		//集計日、アイテム別のみで検索した場合
		if(start !="" && end !="" && category == "" && select =="itemSales") {
			return baseSQL+String.format(" where salesDate between %s and %s",start,end);
		}
		//集計日カテゴリ別
		else if(start !="" && end !="" && category== "" && select =="categorySales") {
			return baseSQL+String.format(" where saleDate between %s and %s group by tr.categoryId",start,end);	
		}
		//集計日平均単価以上
		else if(start !="" && end !="" && category== "" && select =="popularItem") {
			return baseSQL+String.format(" where salesDate between %s and %s and  unitprice > (select sum(unitprice* quantity)/ sum(quantity) from TrSales) ",start,end);
		}
		//カテゴリ、アイテム別のみ
		else if(start=="" && end=="" && category != "" && select=="itemSales") {
			return baseSQL+String.format(" where tr.categoryId=%s",category);
		}
		//カテゴリ、カテゴリ別のみ
		else if(start=="" && end=="" && category != "" && select=="categorySales") {
			return baseSQL+String.format(" where tr.categoryId=%s  group by tr.categoryId",category);
		}
		//カテゴリ、平均単価以上
		else if(start=="" && end=="" && category != "" && select=="popularItem") {
			return baseSQL+String.format(" where tr.categoryId=%s and  unitprice > (select sum(unitprice* quantity)/ sum(quantity) from TrSales)",category);
		}
		//選択のみでアイテム別検索した場合
		else if(start =="" && end=="" && category=="" && select == "itemSales") {
			return baseSQL;
		}
		//選択のみでカテゴリ別検索した場合
		else if(start=="" && end=="" && category == "" && select=="categorySales") {
			return baseSQL+" where  group by tr.categoryId ";
		}
		//選択のみで平均単価以上検索した場合
		else if(start=="" && end=="" && category == "" && select=="popularItem") {
			return baseSQL+" where  unitprice > (select sum(unitprice* quantity)/ sum(quantity) from TrSales) ";
		}
		//集計日、カテゴリ、アイテム別で検索した場合
		else if(start !="" && end != "" && category!= "" && select=="itemSales") {
			return baseSQL +String.format(" where tr.salesDate between %s and %s and tr.categoryId=%s",start,end,category);
		}
		//集計日、カテゴリとカテゴリ別で検索した場合
		else if(start !="" && end != "" && category!= "" && select=="categorySales") {
			return baseSQL+String.format(" where tr.salesDate between %s and %s and tr.categoryId=%s group by tr.categoryId",start,end,category);
		}
		//集計日、カテゴリ、平均単価で検索した場合
		else {
			return baseSQL+String.format(" where tr.salesDate between %s and %s and tr.categoryId=%s and unitprice > (select sum(unitprice* quantity)/ sum(quantity) from TrSales)",start,end,category);
		}
		
	}
	
	
	/**
	 * 商品情報を検索し返す
	 * @param start 集計開始日
	 * @param end 集計終了日
	 * @param category カテゴリ
	 * @param select 検索情報選択
	 * @return 検索結果
	 */
	public TrSalesList search(String start,String end,String category,String select) throws Exception{
		
		String sql = createSQL(start,end,category,select);
		System.out.println(sql);
		
		TrSales tr = null;
		
		TrSalesList trList = new TrSalesList();
		
		List<TrSales> list = new ArrayList<>();
		
		try(Connection con = Pool.getConnection(); PreparedStatement pps = con.prepareStatement(sql)){
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				tr = new TrSales();
				tr.setSalesId(rs.getInt("salesId"));
				tr.setCategoryId(rs.getInt("tr.categoryId"));
				tr.setItemName(rs.getString("itemName"));
				tr.setUnitPrice(rs.getInt("unitprice"));
				tr.setQuantity(rs.getBigDecimal("quanitity"));
				tr.setSalesDate(rs.getDate("salesDate"));
				tr.setUpDateTime(rs.getTimestamp("updateTime"));
				
				list.add(tr);
				
			}
			trList.setList(list);
			
			return trList;
		}
	}
	
	
	@Override
	public void add(Object o) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void update(Object o) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void delete(Object o) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
