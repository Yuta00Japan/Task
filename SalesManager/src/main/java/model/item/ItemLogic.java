package model.item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.MST_Shouhin01Dao;
import dao.TrSalesDao;
import model.util.AntiXss;

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
	
	/**
	 * 商品０１の情報を登録する
	 * @param item 登録情報
	 * @throws Exception 失敗
	 */
	public static void addItem01(Item01 item) throws Exception {
		
		MST_Shouhin01Dao dao = new MST_Shouhin01Dao();
		dao.addItem01(item);
	}
	/**
	 * 商品０１の情報をBEANにセットする
	 * @param request フォーム入力内容
	 * @param parentId 親ID
	 */
	public static void setItem01FromRequest(HttpServletRequest request,String parentId) {
		
		HttpSession session = request.getSession();
		
		Item01 item = (Item01)session.getAttribute("item01");
		
		String rowNo = AntiXss.antiXss(request.getParameter("txtAddNo"));
		String item01Name = AntiXss.antiXss(request.getParameter("txtAddName"));
		
		if(item == null) {
			item = new Item01();
		}
		
		System.out.println("入力値ー＞  "+parentId+" | "+rowNo+" | "+item01Name);
		
		
		if(rowNo != null) {
			if(rowNo.matches("[0-9]+")) {
				item.setRowNo(Integer.parseInt(rowNo));
			}else {
				item.setRowNo(0);
			}
		}else {
			item.setRowNo(0);
		}
		
		item.setParentID(Integer.parseInt(parentId));
		item.setShouhin01Name(item01Name);
		
		session.setAttribute("item01", item);
	}
}
