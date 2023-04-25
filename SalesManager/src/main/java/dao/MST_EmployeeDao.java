package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.connectionPool.Pool;
import model.employee.Employee;
import model.employee.EmployeeDetail;
import model.employee.EmployeeList;

/**
 * 従業員の検索、登録、更新、削除を行う
 * @author yuta
 *
 */
public class MST_EmployeeDao implements Crud{
	
	public String createSql(String name,String branchID,String departmentId,String trueFalse) {
		
		if(trueFalse == null) {
			return String.format(" select emp.empId,emp.branchId,branch.branchName,emp.departmentId,dpm.departmentName,emp.empNo,emp.fullname,emp.kananame,emp.loginID,emp.password,"
					 + "emp.enable,emp.email,emp.userRole,emp.Pwupday,emp.bossId,emp2.fullName,emp2.enable from MST_Employee as emp "
					 + " inner join MST_Branch as branch on emp.branchId = branch.branchId  "
					 + " inner join MST_Department as dpm on emp.departmentId = dpm.departmentID "
					 + " inner join MST_Employee as emp2 on emp.bossId = emp2.EmpId where (emp.fullName like '%%%s%%' or emp.kananame like '%%%s%%') and emp.branchId=%s and emp.departmentId=%s "
					 + " and emp.enable =true",name,name,branchID,departmentId);
		}else {
			return String.format(" select emp.empId,emp.branchId,branch.branchName,emp.departmentId,dpm.departmentName,emp.empNo,emp.fullname,emp.kananame,emp.loginID,emp.password,"
					 + "emp.enable,emp.email,emp.userRole,emp.Pwupday,emp.bossId,emp2.fullName,emp2.enable from MST_Employee as emp "
					 + " inner join MST_Branch as branch on emp.branchId = branch.branchId  "
					 + " inner join MST_Department as dpm on emp.departmentId = dpm.departmentID "
					 + " inner join MST_Employee as emp2 on emp.bossId = emp2.EmpId "
					 + " where (emp.fullName like '%%%s%%' or  emp.kananame like '%%%s%%') and emp.branchId=%s and emp.departmentId=%s",name,name,branchID,departmentId);
		}
	}
	
	/**
	 * ログインIDとパスワードによるログイン認証を行う
	 * @param loginID ログインＩＤ
	 * @param password 暗証番号
	 * @return ユーザー情報
	 * @throws Exception ログイン失敗
	 */
	public Employee login(String loginID,String password) throws Exception{
		
		String sql = String.format("select * from MST_employee where loginID='%s' and password = '%s'",loginID,password);
		System.out.println(sql);
		Employee emp = null;
		
		try(Connection con = Pool.getConnection(); PreparedStatement pps = con.prepareStatement(sql)){
			ResultSet rs = pps.executeQuery();
			if(rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getInt("empId"));
				emp.setBranchId(rs.getInt("departmentId"));
				emp.setDepartmentId(rs.getInt("departmentID"));
				emp.setEmpNo(rs.getInt("empNo"));
				emp.setFullName(rs.getString("fullName"));
				emp.setKanaName(rs.getString("kanaName"));
				emp.setLoginId(rs.getString("loginID"));
				emp.setPassword(rs.getString("password"));
				emp.setEnable(rs.getBoolean("enable"));
				emp.setEmail(rs.getString("email"));
				emp.setUserRole(rs.getString("userRole"));
				emp.setPwupDay(rs.getTimestamp("pwupday"));
				emp.setBossId(rs.getInt("bossID"));
			}
			//大文字、小文字判定を行う
			
			if(emp != null) {
				//大文字、小文字一致
				if(loginID.equalsIgnoreCase(emp.getLoginId())&& password.equalsIgnoreCase(emp.getPassword())) {
					return emp;
				}else {
				//大文字、小文字が不一致
					System.out.println("大文字・小文字判定　不一致");
					return null;
				}
			}
				return null;
			
		}
	}
	
	/**
	 * 従業員情報をすべて取得する
	 * @return 全従業員情報
	 * @throws Exception 取得失敗
	 */
	public EmployeeList loadAll() throws Exception{
		
		String sql ="select emp.empId,emp.branchId,branch.branchName,emp.departmentId,dpm.departmentName,emp.empNo,emp.fullname,emp.kananame,emp.loginID,emp.password,"
				+ "emp.enable,emp.email,emp.userRole,emp.Pwupday,emp.bossId,emp2.fullName,emp2.enable from MST_Employee as emp "
				+ " inner join MST_Branch as branch on emp.branchId = branch.branchId  "
				+ " inner join MST_Department as dpm on emp.departmentId = dpm.departmentID "
				+ " inner join MST_Employee as emp2 on emp.bossId = emp2.EmpId where emp.enable=true;";
		
		Employee emp = null;
		
		EmployeeDetail empDetail = null;
		
		List<Employee> info = new ArrayList<>();
		List<EmployeeDetail>detail = new ArrayList<>();
		
		EmployeeList list = new EmployeeList();
		
		try(Connection con = Pool.getConnection(); PreparedStatement pps = con.prepareStatement(sql)){
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getInt("emp.empId"));
				emp.setBranchId(rs.getInt("emp.branchId"));
				emp.setDepartmentId(rs.getInt("emp.departmentID"));
				emp.setEmpNo(rs.getInt("emp.empNo"));
				emp.setFullName(rs.getString("emp.fullName"));
				emp.setKanaName(rs.getString("emp.kanaName"));
				emp.setLoginId(rs.getString("emp.loginID"));
				emp.setPassword(rs.getString("emp.password"));
				emp.setEnable(rs.getBoolean("emp.enable"));
				emp.setEmail(rs.getString("emp.email"));
				emp.setUserRole(rs.getString("emp.userRole"));
				emp.setPwupDay(rs.getTimestamp("emp.pwupday"));
				emp.setBossId(rs.getInt("emp.bossID"));
				
				info.add(emp);
				
				empDetail = new EmployeeDetail();
				empDetail.setBranchName(rs.getString("branch.branchName"));
				empDetail.setDepartmentName(rs.getString("dpm.departmentName"));
				empDetail.setBossEnable(rs.getBoolean("emp2.enable"));
				empDetail.setBossName(rs.getString("emp2.fullName"));
				
				detail.add(empDetail);
			}
			
			list.setList(info);
			list.setDetail(detail);
			return list;
		}
	}
	/**
	 * 従業員検索を行う
	 * @param name 名前
	 * @param branch 支社ID
	 * @param department 部署ID
	 * @param trueFalse 削除者
	 * @return 従業員検索結果
	 */
	public EmployeeList search(String name,String branch,String department,String trueFalse) throws Exception{
		
		
		String sql = createSql(name,branch,department,trueFalse);
		System.out.println(sql);
		Employee emp = null;
		
		EmployeeDetail empDetail = null;
		
		List<Employee> info = new ArrayList<>();
		List<EmployeeDetail>detail = new ArrayList<>();
		
		EmployeeList list = new EmployeeList();
		
		try(Connection con = Pool.getConnection(); PreparedStatement pps = con.prepareStatement(sql)){
			ResultSet rs = pps.executeQuery();
			while(rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getInt("emp.empId"));
				emp.setBranchId(rs.getInt("emp.branchId"));
				emp.setDepartmentId(rs.getInt("emp.departmentID"));
				emp.setEmpNo(rs.getInt("emp.empNo"));
				emp.setFullName(rs.getString("emp.fullName"));
				emp.setKanaName(rs.getString("emp.kanaName"));
				emp.setLoginId(rs.getString("emp.loginID"));
				emp.setPassword(rs.getString("emp.password"));
				emp.setEnable(rs.getBoolean("emp.enable"));
				emp.setEmail(rs.getString("emp.email"));
				emp.setUserRole(rs.getString("emp.userRole"));
				emp.setPwupDay(rs.getTimestamp("emp.pwupday"));
				emp.setBossId(rs.getInt("emp.bossID"));
				
				info.add(emp);
				
				empDetail = new EmployeeDetail();
				empDetail.setBranchName(rs.getString("branch.branchName"));
				empDetail.setDepartmentName(rs.getString("dpm.departmentName"));
				empDetail.setBossEnable(rs.getBoolean("emp2.enable"));
				empDetail.setBossName(rs.getString("emp2.fullName"));
				
				detail.add(empDetail);
			}
			list.setList(info);
			list.setDetail(detail);
			return list;
		}
	}
	
	/**
	 * 従業員を登録
	 * @param o 従業員情報
	 */
	@Override
	public void add(Object o) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	/**
	 * 既存従業員情報を更新
	 * @param o 更新対象従業員情報
	 */
	@Override
	public void update(Object o) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	/**
	 * 既存の従業員を削除
	 * @param o 削除対象従業員情報
	 */
	@Override
	public void delete(Object o) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	
}
