package model.item;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * MST_Shouhin01のエンティティ
 * @author yuta
 *
 */
public class Item01 implements Serializable{
	
	/**商品０１ID*/
	private int shouhin01ID;
	/**親ID*/
	private int parentID;
	/**商品０１名称*/
	private String shouhin01Name;
	/**行番号*/
	private int rowNo;
	/**更新日時*/
	private Timestamp upd_Dt;
	
	public Item01(){
		
	}

	public int getShouhin01ID() {
		return shouhin01ID;
	}

	public void setShouhin01ID(int shouhin01id) {
		shouhin01ID = shouhin01id;
	}
	
	public String getShouhin01Name() {
		return shouhin01Name;
	}

	public void setShouhin01Name(String shouhin01Name) {
		this.shouhin01Name = shouhin01Name;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public Timestamp getUpd_Dt() {
		return upd_Dt;
	}

	public void setUpd_Dt(Timestamp upd_Dt) {
		this.upd_Dt = upd_Dt;
	}
	
	
}
