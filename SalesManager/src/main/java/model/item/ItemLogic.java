package model.item;

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
}
