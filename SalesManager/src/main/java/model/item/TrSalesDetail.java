package model.item;

import java.io.Serializable;

/**
 * TrSalesのカテゴり名、カテゴリ別の合計金額等を保存します 
 * @author yuta
 *
 */
public class TrSalesDetail implements Serializable{
	
	/**カテゴリ名*/
	private String categoryName;
	/**カテゴリ別売上*/
	private int categorySales;
	/**カテゴリ別販売数*/
	private int categoryAmount;
	
	public TrSalesDetail() {
		
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public int getCategorySales() {
		return categorySales;
	}

	public void setCategorySales(int categorySales) {
		this.categorySales = categorySales;
	}

	public int getCategoryAmount() {
		return categoryAmount;
	}

	public void setCategoryAmount(int categoryAmount) {
		this.categoryAmount = categoryAmount;
	}


	

}
