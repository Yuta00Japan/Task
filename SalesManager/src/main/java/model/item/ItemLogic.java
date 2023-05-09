package model.item;

import dao.MST_Shouhin01Dao;
import dao.TrSalesDao;

/**
 * 商品関連の処理を担当する
 * @author yuta
 *
 */
public class ItemLogic {

	private ItemLogic() {
		
	}
	
	/**
	 * 検索処理を行う
	 * @param start 集計開始日付
	 * @param end 集計終了日付
	 * @param category カテゴリ
	 * @param select 検索方法
	 * @return 検索結果
	 * @throws Exception 検索失敗
	 */
	public static TrSalesList search(String start,String end,String category,String select) throws Exception {
		
		TrSalesDao dao = new TrSalesDao();
		
		return dao.search(start, end, category, select);
	}
	
	/**
	 * 大分類の情報を取得する
	 * @return 商品大分類
	 * @exception 大分類取りだし失敗
	 */
	public static Item01List majorItem() throws Exception {
		
		MST_Shouhin01Dao dao = new MST_Shouhin01Dao();
		
		return dao.findAllMejor();
		
	}
}
