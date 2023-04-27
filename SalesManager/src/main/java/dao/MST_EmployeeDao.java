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
	
	/**
	 * 検索用SQLを作成する
	 * @param name 氏名
	 * @param branchID 支社ID
	 * @param departmentId 部署ID
	 * @param trueFalse 削除者を含めるか？
	 * @return 検索用SQL
	 */
	public String createSql(String name,String branchID,String departmentId,String trueFalse) {
		
		String baseSQL = " select emp.empId,emp.branchId,branch.branchName,emp.departmentId,dpm.departmentName,emp.empNo,emp.fullname,emp.kananame,emp.loginID,emp.password,"
				 + "emp.enable,emp.email,emp.userRole,emp.Pwupday,emp.bossId,emp2.fullName,emp2.enable from MST_Employee as emp "
				 + " inner join MST_Branch as branch on emp.branchId = branch.branchId  "
				 + " inner join MST_Department as dpm on emp.departmentId = dpm.departmentID "
				 + " inner join MST_Employee as emp2 on emp.bossId = emp2.EmpId ";
		
		//何も入力せず検索
		if(name == "" && branchID== "" && departmentId=="" && trueFalse ==null) {
			return baseSQL+" where emp.enable=true";
		}
		//氏名、カナのみで検索
		else if(name!= "" && branchID == ""&& departmentId=="" && trueFalse== null) {
			return baseSQL+String.format(" where (emp.fullName like '%%%s%%' or emp.kananame like '%%%s%%') and emp.enable=true",name,name);
		}
		//支社IDのみで検索
		else if(branchID != "" && name==""  && departmentId=="" && trueFalse== null) {
			return baseSQL +String.format(" where  emp.branchId=%s and emp.enable=true",branchID);
		}
		//部署IDのみで検索
		else if(departmentId != "" &&  name== "" &&  branchID == "" && trueFalse == null ) {
			return baseSQL +String.format(" where emp.departmentId= %s and emp.enable=true", departmentId);
		}
		//削除者のみで検索
		else if(trueFalse != null && name == "" && branchID== "" && departmentId=="" ) {
			return baseSQL+" ";
		}
		//氏名と支社ID
		else if(name != "" && branchID !="" && trueFalse== null && departmentId== "") {
			return baseSQL+ String.format(" where (emp.fullName like '%%%s%%' or emp.kananame like '%%%s%%') and emp.branchId=%s and emp.enable=true",name,name,branchID);
		}
		//氏名と部署ID
		else if(name != "" && departmentId != "" && branchID == "" && trueFalse == null) {
			return baseSQL + String.format(" where (emp.fullName like '%%%s%%' or emp.kananame like '%%%s%%') and emp.departmentId=%s and emp.enable=true", name,name,departmentId);
		}
		//氏名と削除者
		else if(name != "" && trueFalse != null && departmentId=="" && branchID=="") {
			return baseSQL + String.format(" where (emp.fullName like '%%%s%%' or emp.kananame like '%%%s%%') ", name,name,departmentId);
		}
		//支社IDと部署ID
		else if(branchID != "" && departmentId != "" && name == "" && trueFalse == null) {
			return baseSQL + String.format(" where emp.branchID=%s and emp.departmentId=%s and emp.enable=true",branchID,departmentId);
		}
		//支社IDと削除者
		else if(branchID != "" && trueFalse != null && departmentId == "" && name == "") {
			return baseSQL +String.format(" where emp.branchId =%s ",branchID);
		}
		//部署IDと削除者
		else if(departmentId != "" && trueFalse != null && name == "" && branchID == "") {
			return baseSQL + String.format(" where emp.departmentId =%s", departmentId);
		}
		//氏名と部署と所属
		else if(name != "" && departmentId != "" && branchID != "" && trueFalse ==null) {
			return baseSQL+ String.format(" where (emp.fullName like '%%%s%%' or emp.kananame like '%%%s%%') and emp.departmentId=%s and emp.branchId=%s and emp.enable=true",name,name,departmentId,branchID);
		}
		//氏名と部署と削除者
		else if(name != "" && departmentId != "" && trueFalse !=null && branchID =="") {
			return baseSQL+String.format(" where (emp.fullName like '%%%s%%' or emp.kananame like '%%%s%%') and emp.departmentId=%s",name,name,departmentId);
		}
		//氏名と所属と削除者
		else if(name != "" && branchID != "" && trueFalse != null && departmentId == "") {
			return baseSQL+String.format(" where (emp.fullName like '%%%s%%' or emp.kananame like '%%%s%%') and emp.branchId=%s ",name,name,branchID);
		}
		//部署と所属と削除者
		else if(departmentId !="" && branchID != "" && trueFalse != null && name=="") {
			return baseSQL+String.format(" where emp.departmentId=%s  and emp.branchId=%s",departmentId,branchID);
		}
		//すべてで検索
		else{
			return baseSQL+String.format("where (emp.fullName like '%%%s%%' or emp.kananame like '%%%s%%') and emp.branchId=%s and emp.departmentId=%s ",name,name,branchID,departmentId);
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
	 * 従業員番号empNoの最終番号を取得する
	 * @return 従業員最終番号
	 * @throws Exception 取得失敗
	 */
	public int getLastEmpNo() throws Exception{
		try(Connection con = Pool.getConnection(); PreparedStatement pps = con.prepareStatement("select MAX(empNo) as maxEmpNo from MST_Employee")){
			ResultSet rs = pps.executeQuery();
			return rs.getInt("maxEmpNo");
		}
	}
	
	/**
	 * 従業員情報の上司IDから上司情報を取りだします
	 * @param employeeId 上司ID
	 * @return 上司名、ID
	 * @throws Exception 取得失敗
	 */
	public Employee searchBoss(String employeeId) throws Exception{
		
		String sql = "select empId,fullName from MST_Employee where empId =?";
		
		Employee emp = null;
		
		try(Connection con = Pool.getConnection(); PreparedStatement pps = con.prepareStatement(sql)){
			pps.setInt(1, Integer.parseInt(employeeId));
			ResultSet rs = pps.executeQuery();
			if(rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getInt("empId"));
				emp.setFullName(rs.getString("fullName"));
			}
			
			return emp;
		}
	}
	
	/**
	 * 従業員IDをもとに従業員情報をロードします
	 * @param employeeId 従業員ID
	 * @return 従業員情報
	 * @throws Exception ロード失敗
	 */
	public Employee loadSingle(String employeeId) throws Exception{
		
		String sql = "select * from MST_employee where empId=?";
		
		Employee emp = null;
		
		try(Connection con = Pool.getConnection(); PreparedStatement pps = con.prepareStatement(sql)){
			pps.setInt(1, Integer.parseInt(employeeId));
			ResultSet rs = pps.executeQuery();
			if(rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getInt("empId"));
				emp.setBranchId(rs.getInt("branchId"));
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
			return emp;
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
