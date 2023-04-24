package model.employee;

import dao.MST_EmployeeDao;

/**
 * 従業員関係の処理を担当する
 * @author yuta 
 *
 */
public class EmployeeLogic {

	private EmployeeLogic() {
		
	}
	
	/**
	 * ログイン処理を行いユーザー情報を取得する
	 * @param loginID ログインID
	 * @param password 暗証番号
	 * @return ユーザー情報
	 * @throws Exception 取得失敗
	 */
	public static Employee login(String loginID,String password) throws Exception {
		
		MST_EmployeeDao dao = new MST_EmployeeDao();
		
		return dao.login(loginID, password);
	}
	/**
	 * すべての従業員情報を取得する
	 * @return 全従業員情報
	 */
	public static EmployeeList loadAll() throws Exception{
		
		MST_EmployeeDao dao = new MST_EmployeeDao();
		
		return dao.loadAll();
	}
}
