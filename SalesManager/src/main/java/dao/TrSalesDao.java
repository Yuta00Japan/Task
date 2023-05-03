package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.connectionPool.Pool;
import model.item.TrSales;
import model.item.TrSalesDetail;
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
		
		String selectPart =
			"select salesId,tr.categoryId, itemName,salesDate,updateTime, ms.category " ;
		
		String fromPart = " from Trsales as tr inner join MSCategory as ms on tr.categoryId = ms.categoryId";
		
		if(category=="" || category==null) {
			System.out.println("OK");
			category="";
		}
		
		
		System.out.println(start+" "+end+" "+category+" "+select);
		
		//集計日、アイテム別のみで検索した場合
		if(start !="" && end !="" && category.equals("") && select.equals("itemSales")) {
			return selectPart+", sum(unitPrice) / count(unitprice)  as unitprice,sum(quantity) as quantity " + fromPart +String.format(" where salesDate between '%s' and '%s' group by itemName",start,end);
		}
		//集計日カテゴリ別
		else if(start !="" && end !="" && category.equals("") && select.equals("categorySales")) {
			return selectPart+",unitprice,quantity ,sum(unitprice*quantity) as categorySales, sum(quantity)as categoryAmount "+fromPart+String.format(" where salesDate between '%s' and '%s' group by tr.categoryId",start,end);	
		}
		//集計日平均単価以上
		else if(start !="" && end !="" && category.equals("") && select.equals("popularItem") ){
			return selectPart+", unitprice,quantity "+fromPart+String.format(" where salesDate between '%s' and '%s' and  unitprice > (select sum(unitprice* quantity)/ sum(quantity) from TrSales) ",start,end);
		}
		//カテゴリ、アイテム別のみ
		else if(start=="" && end=="" && category != "" && select.equals("itemSales")) {
			return selectPart+", sum(unitPrice)/count(unitprice) as unitprice,sum(quantity) as quantity "+fromPart+String.format(" where tr.categoryId=%s group by itemName",category);
		}
		//カテゴリ、カテゴリ別のみ
		else if(start=="" && end=="" && category != "" && select.equals("categorySales")) {
			return selectPart+",unitprice,quantity, sum(unitprice*quantity) as categorySales,sum(quantity)as categoryAmount "+fromPart+String.format(" where tr.categoryId=%s  group by tr.categoryId",category);
		}
		//カテゴリ、平均単価以上
		else if(start=="" && end=="" && category != "" && select.equals("popularItem")) {
			return selectPart+", unitprice,quantity "+fromPart+String.format(" where tr.categoryId=%s and  unitprice > (select sum(unitprice* quantity)/ sum(quantity) from TrSales)",category);
		}
		//選択のみでアイテム別検索した場合
		else if(start =="" && end=="" && category.equals("") && select.equals("itemSales")) {
			return selectPart+", sum(unitPrice)/count(unitprice) as unitprice,sum(quantity)  as quantity "+fromPart+" group by itemName";
		}
		//選択のみでカテゴリ別検索した場合
		else if(start=="" && end=="" && category.equals("") && select.equals("categorySales")) {
			return selectPart+",unitprice,quantity, sum(unitprice*quantity) as categorySales,sum(quantity)as categoryAmount "+fromPart+" where  group by tr.categoryId ";
		}
		//選択のみで平均単価以上検索した場合
		else if(start=="" && end=="" && category.equals("") && select.equals("popularItem")) {
			return selectPart+", unitprice,quantity "+fromPart+" where  unitprice > (select sum(unitprice* quantity)/ sum(quantity) from TrSales) ";
		}
		//集計日、カテゴリ、アイテム別で検索した場合
		else if(start !="" && end != "" && category!= "" && select.equals("itemSales")) {
			return selectPart+", sum(unitPrice)/count(unitprice) as unitprice,sum(quantity)  as quantity "+fromPart +String.format(" where tr.salesDate between '%s' and '%s' and tr.categoryId=%s group by itemName",start,end,category);
		}
		//集計日、カテゴリとカテゴリ別で検索した場合
		else if(start !="" && end != "" && category!= "" && select.equals("categorySales")) {
			return selectPart+",unitprice,quantity, sum(unitprice*quantity) as categorySales,sum(quantity)as categoryAmount "+fromPart+String.format(" where tr.salesDate between '%s' and '%s' and tr.categoryId=%s group by tr.categoryId",start,end,category);
		}
		//集計日、カテゴリ、平均単価で検索した場合
		else {
			return selectPart+", unitprice,quantity "+fromPart+String.format(" where tr.salesDate between '%s' and '%s' and tr.categoryId=%s and unitprice > (select sum(unitprice* quantity)/ sum(quantity) from TrSales where categoryId=%s)",start,end,category,category);
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
		
		TrSalesDetail trd = null;
		
		TrSalesList trList = new TrSalesList();
		
		List<TrSales> list = new ArrayList<>();
		
		List<TrSalesDetail> detail = new ArrayList<>();
		
		try(Connection con = Pool.getConnection(); PreparedStatement pps = con.prepareStatement(sql)){
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				tr = new TrSales();
				tr.setSalesId(rs.getInt("salesId"));
				tr.setCategoryId(rs.getInt("tr.categoryId"));
				tr.setItemName(rs.getString("itemName"));
				tr.setUnitPrice(rs.getInt("unitprice"));
				tr.setQuantity(rs.getBigDecimal("quantity"));
				tr.setSalesDate(rs.getDate("salesDate"));
				tr.setUpDateTime(rs.getTimestamp("updateTime"));
				
				trd = new TrSalesDetail();
				
				try {
					trd.setCategoryName(rs.getString("ms.category"));
					trd.setCategorySales(rs.getInt("categorySales"));
					trd.setCategoryAmount(rs.getInt("categoryAmount"));
					
					list.add(tr);
					detail.add(trd);
				}catch(Exception e) {
					System.out.println("カテゴリ別検索ではないため中断しました");
					list.add(tr);
				}
				
			}
			trList.setList(list);
			trList.setDetail(detail);
			
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
