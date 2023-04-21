package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.connectionPool.Pool;
import model.employee.Employee;

/**
 * 従業員の検索、登録、更新、削除を行う
 * @author yuta
 *
 */
public class MST_EmployeeDao implements Crud{

	/**
	 * ログインIDとパスワードによるログイン認証を行う
	 * @param loginID ログインＩＤ
	 * @param password 暗証番号
	 * @return ユーザー情報
	 * @throws Exception ログイン失敗
	 */
	public Employee login(String loginID,String password) throws Exception{
		
		String sql = "select * from MST_employee where loginID=? and password = ?";
		
		Employee emp = null;
		
		try(Connection con = Pool.getConnection(); PreparedStatement pps = con.prepareStatement(sql)){
			pps.setString(1,loginID);
			pps.setString(2,password);
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
			return emp;
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
