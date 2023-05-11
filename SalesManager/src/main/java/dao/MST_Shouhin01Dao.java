package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.connectionPool.Pool;
import model.item.Item01;
import model.item.Item01List;

/**
 * 分類に関する処理を行う
 * @author yuta
 *
 */
public class MST_Shouhin01Dao implements Crud{

	
	/**
	 * すべての大分類のカテゴリを取りだす
	 * @return 全大分類
	 * @throws Exception ロード失敗
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
	 * すべての中分類を取りだす
	 * @return 全中分類
	 * @throws Exception ロード失敗
	 */
	public Item01List findAllMinor() throws Exception{
		
		Item01 item = null;
		Item01List list = new Item01List();
		
		List<Item01> result = new ArrayList<>();
		
		String sql =
			"select ms1.shouhin01ID,ms1.parentId,ms1.shouhin01Name,ms1.rowno,ms1.upd_dt, ms2.parentId from MST_Shouhin01 as ms1 "
			+ " inner join MST_Shouhin01 as ms2 on   ms1.parentId=ms2.shouhin01ID "
			+ " where ms1.parentId != 0 and ms2.parentID = 0";
		System.out.println(sql);
		
		try(Connection con = Pool.getConnection();
		PreparedStatement pps = con.prepareStatement(sql)){
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				item = new Item01();
				item.setShouhin01ID(rs.getInt("ms1.shouhin01ID"));
				item.setShouhin01Name(rs.getString("ms1.shouhin01Name"));
				item.setParentID(rs.getInt("ms1.parentId"));
				item.setRowNo(rs.getInt("ms1.rowno"));
				item.setUpd_Dt(rs.getTimestamp("ms1.upd_dt"));
				result.add(item);
			}
			list.setList(result);
			
			return list;
		}
	}
	
	/**
	 * すべての小分類を取得する
	 * @return 全小分類
	 * @throws Exception 小分類取りだし失敗
	 */
	public Item01List findAllDetailed() throws Exception{
		
		Item01 item = null;
		Item01List list = new Item01List();
		
		List<Item01> result = new ArrayList<>();
		
		String sql = "select ms1.shouhin01ID,ms1.parentId,ms1.shouhin01Name,ms1.rowno,ms1.upd_dt, ms2.parentId"
				+ " from MST_Shouhin01 as ms1 inner join MST_Shouhin01 as ms2 on ms1.parentId=ms2.shouhin01ID "
				+ " where ms1.parentId != 0 and ms2.parentID != 0";
		
		try(Connection con = Pool.getConnection();
		PreparedStatement pps = con.prepareStatement(sql)){
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				item = new Item01();
				item.setShouhin01ID(rs.getInt("ms1.shouhin01ID"));
				item.setShouhin01Name(rs.getString("ms1.shouhin01Name"));
				item.setParentID(rs.getInt("ms1.parentId"));
				item.setRowNo(rs.getInt("ms1.rowno"));
				item.setUpd_Dt(rs.getTimestamp("ms1.upd_dt"));
				result.add(item);
			}
			list.setList(result);
		}
		
		return list;
	}
	
	/**
	 * 商品ID０１から商品情報を取得する
	 * @param shouhin01ID 商品０１ID
	 * @return 商品０１情報
	 * @throws Exception ロード失敗
	 */
	public Item01 loadSingle(String shouhin01ID)  throws Exception{
		
		Item01 item = null;
		String sql = String.format("select * from MST_Shouhin01 where shouhin01ID=%s",shouhin01ID);
		System.out.println(sql);
		
		try(Connection con = Pool.getConnection();
		PreparedStatement pps = con.prepareStatement(sql)){
			ResultSet rs = pps.executeQuery();
			if(rs.next()) {
				item = new Item01();
				item.setShouhin01ID(rs.getInt("shouhin01ID"));
				item.setShouhin01Name(rs.getString("shouhin01Name"));
				item.setParentID(rs.getInt("parentId"));
				item.setRowNo(rs.getInt("rowNo"));
				item.setUpd_Dt(rs.getTimestamp("upd_dt"));
			}
			return  item;
		}
	}
	
	/**
	 * 対象IDを親IDとする分類を取りだす
	 * @param shouhin01ID 商品０１ID
	 * @return 商品分類情報
	 * @throws Exception 商品分類取りだし失敗
	 */
	public Item01List detail(String shouhin01ID) throws Exception{
		
		Item01 item = null;
		Item01List list = new Item01List();
		List<Item01> result = new ArrayList<>();
		
		String sql = String.format("select * from MST_shouhin01 where parentId=%s",shouhin01ID);
		System.out.println(sql);
		
		try(Connection con = Pool.getConnection();
		PreparedStatement pps = con.prepareStatement(sql)){
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				item = new Item01();
				item.setShouhin01ID(rs.getInt("shouhin01ID"));
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
	@Override
	public void add(Object o) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		Item01 item = (Item01)o;
		
		String sql =String.format("insert into MST_Shouhin01(parentId,rowNo,shouhin01Name)"
				+ " values(%d,%d,'%s')",item.getParentID(),item.getRowNo(),item.getShouhin01Name());
		System.out.println(sql);
		
		try(Connection con = Pool.getConnection();
		PreparedStatement pps = con.prepareStatement(sql)){
			pps.executeUpdate();
		}
	}
	/**
	 * 既存の商品０１の情報を更新する
	 * @param item 更新情報
	 * @throws Exception 更新失敗
	 */
	@Override
	public void update(Object o) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		Item01 item = (Item01)o;
		
		String sql = 
			String.format
			("update MST_Shouhin01 set rowNo=%d,shouhin01Name='%s' where shouhin01Id=%d",item.getRowNo(),item.getShouhin01Name(),item.getShouhin01ID());
			System.out.println(sql);
			
		try(Connection con = Pool.getConnection();
		PreparedStatement pps = con.prepareStatement(sql)){
			pps.executeUpdate();
		}
	}
	/**
	 * 既存の商品０１を削除する
	 * @param o 削除対象ID
	 */
	@Override
	public void delete(Object o) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		String shouhin01ID = (String)o;
		String sql=
				String.format("DELETE FROM mst_shouhin01 "
					+ " WHERE shouhin01Id = %s "
					+ "   OR parentId = %s"
					+ "   OR parentId IN (SELECT t1.shouhin01Id "
					+ " FROM (SELECT shouhin01Id FROM mst_shouhin01 WHERE parentid = %s OR shouhin01id = %s) AS t1); "
					+ "",shouhin01ID,shouhin01ID,shouhin01ID,shouhin01ID);
		System.out.println(sql);
		
		try(Connection con = Pool.getConnection();
		PreparedStatement pps = con.prepareStatement(sql)){
			pps.executeUpdate();
		}
	}
	
}
