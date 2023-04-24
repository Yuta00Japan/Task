package model.employee;

import java.io.Serializable;

/**
 * 従業員情報の詳細をinner joinし保存する
 * @author yuta
 *
 */
public class EmployeeDetail implements Serializable{
	
	/**支社名*/
	private String branchName;
	/**部署名*/
	private String departmentName;
	/**上司有効*/
	private boolean bossEnable;
	/**上司名*/
	private String bossName;
	
	public EmployeeDetail() {
		
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public boolean isBossEnable() {
		return bossEnable;
	}

	public void setBossEnable(boolean bossEnable) {
		this.bossEnable = bossEnable;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	
}
