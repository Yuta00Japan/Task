package model.employee;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * MST_Employee tableの情報を格納します
 * @author yuta
 *
 */
public class Employee implements Serializable{

	/**従業員ID*/
	private int empId;
	/**支店ID*/
	private int branchId;
	/**部署ID*/
	private int departmentId;
	/**従業員No*/
	private int empNo;
	/**従業員名*/
	private String fullName;
	/**従業員かな*/
	private String kanaName;
	/**ログインID*/
	private String loginId;
	/**パスワード*/
	private String password;
	/**有効無効*/
	private boolean enable;
	/**メールアドレス*/
	private String email;
	/**権限*/
	private String userRole;
	/**パスワード更新日時*/
	private Timestamp pwupDay;
	/**上司ID*/
	private int bossId;
	
	public Employee(){
		
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getKanaName() {
		return kanaName;
	}

	public void setKanaName(String kanaName) {
		this.kanaName = kanaName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Timestamp getPwupDay() {
		return pwupDay;
	}

	public void setPwupDay(Timestamp pwupDay) {
		this.pwupDay = pwupDay;
	}

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}
}
