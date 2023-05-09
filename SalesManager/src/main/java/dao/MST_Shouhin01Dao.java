package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.connectionPool.Pool;
import model.item.Item01;
import model.item.Item01List;

public class MST_Shouhin01Dao {

	
	/**
	 * 大分類のカテゴリを取りだす
	 * @return
	 * @throws Exception
	 */
	public Item01List findAllMejor() throws Exception{
		
		Item01 item = null;
		Item01List list = new Item01List();
		
		List<Item01> result = new ArrayList<>();
		
		try(Connection con = Pool.getConnection(); 
		PreparedStatement pps = con.prepareStatement("select * from MST_Shouhin01 where parentId = 0")){
			
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				item = new Item01();
				item.setShouhin01ID(rs.getInt("shouhin01Id"));
				item.setShouhin01Name(rs.getString("shouhin01Name"));
				item.setParentID(rs.getInt("parentId"));
				item.setRowNo(rs.getInt("rowno"));
				item.setUpd_Dt(rs.getTimestamp("upd_dt"));
				result.add(item);
			}
			list.setList(result);
			
			return list;
		}
	}
	
	/**
	 * 商品０１の情報を登録する
	 * @param item 登録したい商品０１情報
	 * @throws Exception 登録失敗
	 */
	public void addItem01(Item01 item) throws Exception{
		
		String sql =String.format("insert into MST_Shouhin01(parentId,rowNo,shouhin01Name)"
				+ " values(%d,%d,'%s')",item.getParentID(),item.getRowNo(),item.getShouhin01Name());
		System.out.println(sql);
		
		try(Connection con = Pool.getConnection();
		PreparedStatement pps = con.prepareStatement(sql)){
			pps.executeUpdate();
		}
	}
}
