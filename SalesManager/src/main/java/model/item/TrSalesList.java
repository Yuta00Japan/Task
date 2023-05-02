package model.item;

import java.util.List;

/**
 * 商品検索結果を保存する
 * @author yuta
 *
 */
public class TrSalesList {

	/**商品検索結果*/
	private List<TrSales> list;
	
	public TrSalesList() {
		
	}

	public List<TrSales> getList() {
		return list;
	}

	public void setList(List<TrSales> list) {
		this.list = list;
	}
	
	
}
