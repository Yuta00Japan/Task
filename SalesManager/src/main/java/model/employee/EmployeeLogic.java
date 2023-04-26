package model.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.MST_EmployeeDao;
import model.util.AntiXss;

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
	 * 従業員IDをもとに従業員情報を取得する
	 * @param employeeId 従業員ID
	 * @return 従業員情報
	 * @throws Exception ロード失敗
	 */
	public static Employee loadSingle(String employeeId) throws Exception{
		
		MST_EmployeeDao dao = new MST_EmployeeDao();
		
		return dao.loadSingle(employeeId);
	}
	
	/**
	 * 上司IDをもとに情報を取得する
	 * @param employeeId 上司ID
	 * @return 上司情報
	 */
	public static Employee searchBoss(String employeeId) throws Exception{
		
		MST_EmployeeDao dao = new MST_EmployeeDao();
		
		return dao.searchBoss(employeeId);
	}
	
	
	/**
	 * すべての従業員情報を取得する
	 * @return 全従業員情報
	 */
	public static EmployeeList loadAll() throws Exception{
		
		MST_EmployeeDao dao = new MST_EmployeeDao();
		
		return dao.loadAll();
	}
	
	/**
	 * 従業員検索を行う
	 * @param name 名前
	 * @param branch 支社ID
	 * @param department 部署ID
	 * @param trueFalse 削除者を含めるか？
	 * @return 検索結果
	 * @throws Exception 検索失敗
	 */
	public static EmployeeList search(String name,String branch,String department,String trueFalse) throws Exception{
		
		MST_EmployeeDao dao = new MST_EmployeeDao();
		
		return dao.search(name, branch, department, trueFalse);
	}
	
	/**
	 * 新規登録、編集の情報をsessionに保存する
	 * @param request フォームで入力された内容
	 */
	public static void setEmployeeFromRequest(HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession();
		
		Employee emp = (Employee)session.getAttribute("employee");
		
		String empNo = AntiXss.antiXss(request.getParameter("empNo"));
		String fullName = AntiXss.antiXss(request.getParameter("fullName"));
		String kanaName = AntiXss.antiXss(request.getParameter("kanaName"));
		String loginId= AntiXss.antiXss(request.getParameter("loginId"));
		String mail = AntiXss.antiXss(request.getParameter("email"));
		String password = AntiXss.antiXss(request.getParameter("password"));
		
		String branchId= request.getParameter("branchId");
		String departmentId = request.getParameter("departmentId");
		String bossId= AntiXss.antiXss(request.getParameter("bossId"));
		
		if(empNo == null) {
			empNo = "0";
		}else if(!empNo.matches("^[0-9]+$")) {
			empNo = "0";
		}
		
		if(branchId == "" || branchId == null) {
			branchId ="0";
		}
		
		if(departmentId =="" || departmentId == null) {
			departmentId ="0";
		}
		
		if(bossId == null) {
			bossId="0";
		}//bossIdの値が数字かどうかを確認する
		else if(!bossId.matches("^[0-9]+$")) {
			bossId = "0";
		}
		
		if(emp == null) {
			emp = new Employee();
		}
		emp.setEmpNo(Integer.parseInt(empNo));
		emp.setFullName(fullName);
		emp.setKanaName(kanaName);
		emp.setLoginId(loginId);
		emp.setEmail(mail);
		emp.setBranchId(Integer.parseInt(branchId));
		emp.setDepartmentId(Integer.parseInt(departmentId));
		emp.setBossId(Integer.parseInt(bossId));
		emp.setPassword(password);
		
		session.setAttribute("employee",emp);
	}
}
