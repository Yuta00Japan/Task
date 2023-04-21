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
	private int EmpId;
	/**支店ID*/
	private int BranchId;
	/**部署ID*/
	private int departmentId;
	/**従業員No*/
	private int EmpNo;
	/**従業員名*/
	private String fullName;
	/**従業員かな*/
	private String kanaName;
	/**ログインID*/
	private String loginId;
	/**パスワード*/
	private String password;
	/**有効無効*/
	private boolean Enable;
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
		return EmpId;
	}

	public void setEmpId(int empId) {
		EmpId = empId;
	}

	public int getBranchId() {
		return BranchId;
	}

	public void setBranchId(int branchId) {
		BranchId = branchId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getEmpNo() {
		return EmpNo;
	}

	public void setEmpNo(int empNo) {
		EmpNo = empNo;
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
		return Enable;
	}

	public void setEnable(boolean enable) {
		Enable = enable;
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
