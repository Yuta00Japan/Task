package model.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * TrSales Entity
 * @author yuta
 *
 */
public class TrSales implements Serializable{

	/**売上ID*/
	private int salesId;
	/**カテゴリID*/
	private int categoryId;
	/**商品名*/
	private String itemName;
	/**単価*/
	private int unitPrice;
	/**個数*/
	private BigDecimal quantity;
	/**販売日*/
	private Date salesDate;
	/**更新日時*/
	private Timestamp upDateTime;
	
	public TrSales() {
		
	}

	public int getSalesId() {
		return salesId;
	}

	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public Timestamp getUpDateTime() {
		return upDateTime;
	}

	public void setUpDateTime(Timestamp upDateTime) {
		this.upDateTime = upDateTime;
	}
	
	
}
