package model.item;

import java.io.Serializable;
import java.util.List;

/**
 * 商品検索結果
 * @author yuta
 *
 */
public class Item01List implements Serializable{

	/**カテゴリ検索結果*/
	private List<Item01> list ;

	public List<Item01> getList() {
		return list;
	}

	public void setList(List<Item01> list) {
		this.list = list;
	}
	
}
